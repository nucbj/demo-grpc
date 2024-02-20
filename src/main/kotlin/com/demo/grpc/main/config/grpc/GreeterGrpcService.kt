package com.demo.grpc.main.config.grpc

import com.demo.grpc.main.BaseProtoServiceGrpcKt
import com.demo.grpc.main.BaseRequest
import com.demo.grpc.main.BaseResponse
import com.demo.grpc.main.domain.imageStore.handler.ImageStoreHandler
import com.demo.grpc.main.domain.imageStore.repository.ImageStoreRepository
import io.grpc.Server
import io.grpc.ServerBuilder

class GreeterGrpcService(
	private val port: Int
) {

	private lateinit var imageStoreRepository: ImageStoreRepository

	val server: Server =
		ServerBuilder
			.forPort(port)
			.addService(HelloWorldService())
			.build()

	fun start() {
		server.start()
		println("Server started, listening on $port")
		Runtime.getRuntime().addShutdownHook(
			Thread {
				println("*** shutting down gRPC server since JVM is shutting down")
				this@GreeterGrpcService.stop()
				println("*** server shut down")
			},
		)
	}

	private fun stop() {
		server.shutdown()
	}

	fun blockUntilShutdown() {
		server.awaitTermination()
	}

	internal class HelloWorldService : BaseProtoServiceGrpcKt.BaseProtoServiceCoroutineImplBase() {
		override suspend fun retrieveBaseOnDB(request: BaseRequest): BaseResponse {
			println(request.baseId)
			return BaseResponse.newBuilder()
				.setBaseId("baseId")
				.setBaseName("baseName")
				.setBaseNumber("BaseNumber")
				.build()
		}
	}
}