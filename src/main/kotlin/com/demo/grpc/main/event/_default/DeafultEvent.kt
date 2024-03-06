package com.demo.grpc.main.event._default

import org.springframework.context.ApplicationEvent

class DefaultEvent(source: Any?, val message: String) : ApplicationEvent(source!!)
