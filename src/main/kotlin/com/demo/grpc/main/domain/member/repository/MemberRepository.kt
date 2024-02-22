package com.demo.grpc.main.domain.member.repository

import com.demo.grpc.main.domain.member.model.MemberEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface MemberRepository : ReactiveCrudRepository<MemberEntity, Long> {

	fun findByName(name: String): Mono<MemberEntity>
}
