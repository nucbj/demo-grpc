package com.demo.grpc.main.domain.member.grpc

import UserServiceGrpcKt
import UserServiceRequest
import UserServiceResponse
import com.demo.grpc.main.domain.member.repository.MemberRepository
import com.demo.grpc.main.utils.FormattingUtils
import kotlinx.coroutines.reactive.awaitFirst
import net.devh.boot.grpc.server.service.GrpcService
import java.time.format.DateTimeFormatter

@GrpcService
class UserGrpcService(
	private val memberRepository: MemberRepository
) : UserServiceGrpcKt.UserServiceCoroutineImplBase() {

	override suspend fun findUser(request: UserServiceRequest): UserServiceResponse {
		val userEntity = memberRepository.findByName(request.name).awaitFirst()
		return UserServiceResponse.newBuilder()
			.setSeq(userEntity.seq)
			.setName(FormattingUtils.maskingName(userEntity.name!!))
			.setType(userEntity.type)
			.setGender(userEntity.gender!!.toInt())
			.setStatus(userEntity.status)
			.setCreatedAt(userEntity.createdAt!!.format(DateTimeFormatter.ISO_DATE_TIME)).build()
	}
}
