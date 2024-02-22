package com.demo.grpc.main.config.security

import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ReactiveAuthenticationManager(
	private val jwtSupport: JwtSupport,
	private val user: UserDetailCustomService
) : ReactiveAuthenticationManager {

	override fun authenticate(authentication: Authentication?): Mono<Authentication> {
		return Mono.justOrEmpty(authentication)
			.filter { auth -> auth is BearerToken }
			.cast(BearerToken::class.java)
			.flatMap { jwt -> mono { validateToken(jwt) } }
			.onErrorMap { error -> throw RuntimeException(error.message) }
	}

	suspend fun validateToken(token: BearerToken): Authentication {
		val 대칭값 = jwtSupport.get대칭값(token)
		val userDetails = user.findByUsername(대칭값).awaitFirstOrNull()

		if (jwtSupport.isValid(token, userDetails!!)) {
			return UsernamePasswordAuthenticationToken(
				userDetails.username,
				userDetails.password,
				userDetails.authorities
			)
		}
		throw RuntimeException("Invalid Token")
	}
}
