package com.demo.grpc.main.domain.member.handler

import com.demo.grpc.main.config.security.JwtSupport
import com.demo.grpc.main.domain.member.repository.MemberRepository
import com.demo.grpc.main.event.EventPublisher
import io.micrometer.common.util.StringUtils
import kotlinx.coroutines.reactive.awaitFirstOrElse
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Service
class AuthHandler(
	private val jwtSupport: JwtSupport,
	private val eventPublisher: EventPublisher,
	private val memberRepository: MemberRepository
) {

	suspend fun getToken(request: ServerRequest): ServerResponse {
		val name = request.queryParam("name")
		val member = memberRepository.findByName(name.get())
		val email = member.filter { it.name == name.get() }
			.awaitFirstOrElse { throw IllegalArgumentException("name is not found") }
			.email
		val token = jwtSupport.generate(email.toString())

		// e.g. Event triggered after registration
		eventPublisher.publishCustomEvent(true)
		eventPublisher.publishDefaultEvent("default event")
		if (StringUtils.isEmpty(name.orElse(null))) {
			throw IllegalArgumentException("name is required")
		} else {
			return ServerResponse.ok().bodyValueAndAwait(token)
		}
	}

	suspend fun checkToken(request: ServerRequest): ServerResponse {
		val name = request.queryParam("name")
		if (StringUtils.isEmpty(name.orElse(null))) {
			throw IllegalArgumentException("name is required")
		} else {
			return ServerResponse.ok().bodyValueAndAwait(memberRepository.findByName(name.get()).awaitSingle())
		}
	}

	suspend fun refreshToken(request: ServerRequest): ServerResponse {
		val name = request.queryParam("name")
		if (StringUtils.isEmpty(name.orElse(null))) {
			throw IllegalArgumentException("name is required")
		} else {
			return ServerResponse.ok().bodyValueAndAwait(memberRepository.findByName(name.get()).awaitSingle())
		}
	}
}
