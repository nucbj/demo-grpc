package com.demo.grpc.main.config.security

import com.demo.grpc.main.domain.member.repository.MemberRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Component
class UserDetailCustomService(
	private val memberRepository: MemberRepository
) : ReactiveUserDetailsService {

	override fun findByUsername(username: String?): Mono<UserDetails> {
		if (username == null) {
			throw RuntimeException("username is null")
		}

		var member = memberRepository.findByName(username)

		if (member != null) {
			throw RuntimeException("member is null")
		}
		return Mono.just(member)
	}
}
