package com.demo.grpc.main.exception

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono
import java.lang.RuntimeException
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.LocalDateTime.now

@ControllerAdvice
class ExceptionHandler {

	@ExceptionHandler(RuntimeException::class)
	fun onException(exception: RuntimeException): Mono<ResponseEntity<ErrorResponse>> {
//		throw RuntimeException("Not implemented")
		return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.name,
				"$exception",
				Timestamp.valueOf(now())
			))
		)
	}
}
data class ErrorResponse(
	val status: Int,
	val error: String,
	val message: String,
	val timestamp: Timestamp
)