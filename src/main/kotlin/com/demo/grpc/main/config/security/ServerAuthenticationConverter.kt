package com.demo.grpc.main.config.security

import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class ServerAuthenticationConverter : ServerAuthenticationConverter {

	override fun convert(exchange: ServerWebExchange?): Mono<Authentication> {
		return Mono.justOrEmpty(exchange?.request?.headers?.getFirst("Authorization"))
			.filter { auth -> auth.startsWith("Bearer ") }
			.map { auth -> BearerToken(auth.substring(7)) }
			.cast(Authentication::class.java)
	}
}
