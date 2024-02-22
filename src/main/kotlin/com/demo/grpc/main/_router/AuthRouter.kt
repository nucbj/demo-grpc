package com.demo.grpc.main._router

import com.demo.grpc.main.domain.member.handler.AuthHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class AuthRouter(
	private val authHandler: AuthHandler
) {

	@Bean
	fun authRoute() = coRouter {
		accept(MediaType.APPLICATION_JSON).nest {
			"auth".nest {
				GET("", authHandler::getMember)
			}
		}
	}
}
