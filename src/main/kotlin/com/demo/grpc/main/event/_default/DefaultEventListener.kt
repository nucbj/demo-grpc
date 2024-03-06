package com.demo.grpc.main.event._default

import org.springframework.context.ApplicationListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class DefaultEventListener : ApplicationListener<DefaultEvent> {

	@Async
	override fun onApplicationEvent(event: DefaultEvent) {
		println("Received spring default event - " + event.message)
	}
}
