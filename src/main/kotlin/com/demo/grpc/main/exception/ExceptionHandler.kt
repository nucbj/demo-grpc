package com.demo.grpc.main.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import reactor.core.publisher.Mono
import java.sql.Timestamp
import java.time.LocalDateTime.now

@ControllerAdvice
class ExceptionHandler {

	@ExceptionHandler(RuntimeException::class)
	fun onException(exception: RuntimeException): Mono<ResponseEntity<ErrorResponse>> {
//		throw RuntimeException("Not implemented")
		//e.printstacktrace() to kotlin
		exception.printStackTrace()
		return Mono.just(
			ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(
					ErrorResponse(
						HttpStatus.BAD_REQUEST.value(),
						HttpStatus.BAD_REQUEST.name,
						"$exception",
						Timestamp.valueOf(now())
					)
				)
		)
	}
}

data class ErrorResponse(
	val status: Int,
	val error: String,
	val message: String,
	val timestamp: Timestamp
)
