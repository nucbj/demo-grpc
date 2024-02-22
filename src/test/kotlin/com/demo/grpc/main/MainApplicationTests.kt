package com.demo.grpc.main

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*

@SpringBootTest
class MainApplicationTests {


	@Test
	fun contextLoads() {
		genKey("test")
	}

	fun genKey(input: String): String {
		val random = SecureRandom()
		val key = ByteArray(32)
		random.nextBytes(key)
		val encodedKey = Base64.getEncoder().encodeToString(key)
		val hashedInput = hashAndEncode(input)
		return "$encodedKey$hashedInput"
	}

	fun hashAndEncode(input: String): String {
		val bytes = input.toByteArray()
		val md = MessageDigest.getInstance("SHA-256")
		val digest = md.digest(bytes)
		val encoded = Base64.getEncoder().encodeToString(digest)
		return encoded
	}
}
