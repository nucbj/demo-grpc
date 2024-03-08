package com.demo.grpc.main.domain.member.grpc

import UserServiceGrpcKt
import UserServiceRequest
import UserServiceResponse
import com.demo.grpc.main.domain.member.repository.MemberRepository
import com.demo.grpc.main.utils.FormattingUtils
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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

	// 클라이언트에게 사용자 데이터를 스트림으로 전송하는 메소드
	override fun toStreamUser(request: UserServiceRequest): Flow<UserServiceResponse> {
		return flow {
			val userEntity = memberRepository.findByName(request.name).awaitFirst()
			// emit 함수는 코루틴의 Flow에서 데이터를 방출하는 역할을 합니다.
			emit(
				UserServiceResponse.newBuilder()
					.setSeq(userEntity.seq)
					.setName(FormattingUtils.maskingName(userEntity.name!!))
					.setType(userEntity.type)
					.setGender(userEntity.gender!!.toInt())
					.setStatus(userEntity.status)
					.setCreatedAt(userEntity.createdAt!!.format(DateTimeFormatter.ISO_DATE_TIME)).build()
			)
		}
//		val userEntity = memberRepository.findByName(request.name)
	}

	override suspend fun fromStreamUser(requests: Flow<UserServiceRequest>): UserServiceResponse {
		// Flow에서 첫 번째 요청을 가져옵니다.
		// 이는 클라이언트로부터 스트림을 받아 처리하는 메소드에서 첫 번째 요청만을 처리하고자 할 때 사용됩니다.
		val request = requests.first()
		val userEntity = memberRepository.findByName(request.name).awaitFirst()
		return UserServiceResponse.newBuilder()
			.setSeq(userEntity.seq)
			.setName(FormattingUtils.maskingName(userEntity.name!!))
			.setType(userEntity.type)
			.setGender(userEntity.gender!!.toInt())
			.setStatus(userEntity.status)
			.setCreatedAt(userEntity.createdAt!!.format(DateTimeFormatter.ISO_DATE_TIME)).build()
	}

	// 클라이언트로부터 사용자 데이터를 스트림으로 받고, 처리 결과를 스트림으로 전송하는 메소드
	override fun allStreamUser(requests: Flow<UserServiceRequest>): Flow<UserServiceResponse> {
		return flow {
			requests.collect { request ->
				val userEntity = memberRepository.findByName(request.name).awaitFirst()
				emit(
					UserServiceResponse.newBuilder()
						.setSeq(userEntity.seq)
						.setName(FormattingUtils.maskingName(userEntity.name!!))
						.setType(userEntity.type)
						.setGender(userEntity.gender!!.toInt())
						.setStatus(userEntity.status)
						.setCreatedAt(userEntity.createdAt!!.format(DateTimeFormatter.ISO_DATE_TIME)).build()
				)
			}
		}
	}
}
