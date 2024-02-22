package com.demo.grpc.main._router

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class Router(
	@Value("\${api.prefix}") private val prefix: String,
	private val imageStoreRouter: ImageStoreRouter,
	private val grpcRouter: GrpcRouter,
	private val authRouter: AuthRouter
) {

	@Bean
	fun imageStoreRoute() = coRouter {
		accept(MediaType.APPLICATION_JSON).nest {
			path(prefix).nest {
				add(imageStoreRouter.imageStoreRoute())
				add(grpcRouter.grpcRoute())
				add(authRouter.authRoute())
			}
		}
	}
}
