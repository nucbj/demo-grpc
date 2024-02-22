package com.demo.grpc.main.domain.member.handler

import com.demo.grpc.main.domain.imageStore.model.ImageStoreDto
import com.demo.grpc.main.domain.imageStore.repository.ImageStoreRepository
import com.demo.grpc.main.domain.member.repository.MemberRepository
import io.micrometer.common.util.StringUtils
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import reactor.kotlin.core.publisher.toMono

@Service
class AuthHandler(
	private val memberRepository: MemberRepository
) {

	suspend fun getMember(request: ServerRequest): ServerResponse {
		var name = request.queryParam("name")
		if (StringUtils.isEmpty(name.orElse(null))) {
			throw IllegalArgumentException("name is required")
		} else {
//			return ServerResponse.ok().bodyValueAndAwait(memberRepository.findAll()
//				.filter { it.name.equals(name) }
//				.toMono().awaitSingle())
			return ServerResponse.ok().bodyValueAndAwait(memberRepository.findByName(name.get()).awaitSingle())
		}
	}
}
