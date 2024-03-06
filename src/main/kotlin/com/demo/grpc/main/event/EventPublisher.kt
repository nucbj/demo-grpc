package com.demo.grpc.main.event

import com.demo.grpc.main.event._default.DefaultEvent
import com.demo.grpc.main.event.custom.CustomEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventPublisher {

	@Autowired
	private val applicationEventPublisher: ApplicationEventPublisher? = null

	suspend fun publishDefaultEvent(message: String) {
		val defaultEvent: DefaultEvent = DefaultEvent(this, message)
		System.out.printf("Publishing default event. %s%n", message)
		applicationEventPublisher!!.publishEvent(defaultEvent)
	}

	suspend fun publishCustomEvent(chkFlag: Boolean) {
		val customEvent: CustomEvent<Object> = CustomEvent(Object(), chkFlag)
		System.out.printf("Publishing custom event. %s%n", chkFlag)
		applicationEventPublisher!!.publishEvent(customEvent)
	}
}
