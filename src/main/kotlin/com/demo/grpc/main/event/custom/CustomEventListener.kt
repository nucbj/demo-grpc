package com.demo.grpc.main.event.custom

import org.springframework.context.event.EventListener
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

/**
 * REF BY
 * https://github.com/suhongkim98/spring-eventlistener-example
 * */
@Component
class CustomEventListener {

	@Async
	@EventListener
	suspend fun defaultEventListener(event: CustomEvent<*>) {
		println("Default Event : $event")
	}

	@Async
	@EventListener
	suspend fun testEvent(event: CustomEvent<*>) {
		println("Some domain Test Event. : $event")
	}

	/**
	 * Order 로 이벤트 리스너 간 호출 순서 지정 가능, 숫자가 작을 수록 우선된다.
	 * 지정하지 않을 때 디폴트는 Ordered.LOWEST_PRECEDENCE
	 */
	@Order(value = Ordered.HIGHEST_PRECEDENCE)
	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	fun transactionalEventListenerBeforeCommitOrdered(event: CustomEvent<*>) {
		println(
			"before commit ---> $event"
		)
	}

	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	fun transactionalEventListenerBeforeCommit(event: CustomEvent<*>) {
		println(
			"before commit ---> $event"
		)
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	fun transactionalEventListenerAfterCommit(event: CustomEvent<*>) {
		println(
			"after commit ---> $event"
		)
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
	fun transactionalEventListenerAfterRollback(event: CustomEvent<*>) {
		println(
			"after rollback ---> $event"
		)
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
	fun transactionalEventListenerAfterCompletion(event: CustomEvent<*>) {
		println(
			"after completion ---> $event"
		)
	}

	/**
	 * 트랜잭션이 성공한 이후 이벤트 리스너에서 DB에 쓰기작업 등을
	 * 추가로 해야하는 경우 REQUIRES_NEW를 주어 새로운 트랜잭션에서 이벤트 리스너의 메서드가 실행되게 한다.
	 */
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	fun transactionalEventListenerAfterCommitRequiredNew(event: CustomEvent<*>) {
		println(
			"after commit requires new ---> $event"
		)
	}

	/**
	 * 트랜잭션이 성공한 이후 이벤트 리스너에서 DB에 쓰기작업 등을
	 * 추가로 해야하는 경우 혹은 @Async 로 별도의 스레드에서 처리하도록 함
	 */
	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	fun transactionalEventListenerAfterCommitAsync(event: CustomEvent<*>) {
		println(
			"after commit with async ---> $event"
		)
	}
}
