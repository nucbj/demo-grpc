package com.demo.grpc.main.config.filter

import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class RequestLoggingFilter: WebFilter {
	override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
		val request = exchange.request
		println("Request: ${request.method} ${request.uri}")

		return chain.filter(exchange)
			.doFinally {
				val response = exchange.response
				println("Response: ${response.statusCode}")
			}
	}
}