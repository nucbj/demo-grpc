package com.demo.grpc.main._router

import com.demo.grpc.main.domain.imageStore.handler.ImageStoreHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ImageStoreRouter(
	private val imageStoreHandler: ImageStoreHandler
) {

	@Bean
	fun imageStoreRoute() = coRouter {
		accept(MediaType.APPLICATION_JSON).nest {
			"image".nest {
				GET("", imageStoreHandler::findImages)
				GET("/{id}", imageStoreHandler::findImage)
			}
		}
	}
}
