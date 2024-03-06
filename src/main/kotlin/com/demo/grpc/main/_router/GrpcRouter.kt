package com.demo.grpc.main._router

import com.demo.grpc.main.domain.imageStore.handler.ImageStoreHandler
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class GrpcRouter(
	private val imageStoreHandler: ImageStoreHandler
) {
	fun grpcRoute() = coRouter {
		accept(MediaType.APPLICATION_JSON).nest {
			"grpc".nest {
				POST("", imageStoreHandler::startGrpc)
			}
		}
	}
}
