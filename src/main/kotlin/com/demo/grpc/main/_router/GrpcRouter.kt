package com.demo.grpc.main._router

import com.demo.grpc.main.domain.imageStore.handler.ImageStoreHandler
import com.demo.grpc.main.config.filter.RequestLoggingFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class GrpcRouter (
	@Value("\${api.prefix}") private val prefix: String,
	private val loggingFilter: RequestLoggingFilter,
	private val imageStoreHandler: ImageStoreHandler
) {

	@Bean
	fun grpcRoute() = coRouter {
		accept(MediaType.APPLICATION_JSON).nest {
			"grpc".nest {
				POST("", imageStoreHandler::startGrpc)
			}
		}
	}
}