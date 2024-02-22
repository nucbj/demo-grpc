package com.demo.grpc.main.config.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class JwtSupport(
	@Value("\${jwt.key}") private val key: ByteArray,
) {
	private val jwtKey = Keys.hmacShaKeyFor(key)
	private val parser = Jwts.parserBuilder().setSigningKey(jwtKey).build()

	fun generate(대칭값: String): BearerToken {
		val token = Jwts.builder()
			.setSubject(대칭값)
			.setIssuedAt(Date.from(Instant.now()))
			.setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
			.signWith(jwtKey)
			.compact()
		return BearerToken(token)
	}

	fun get대칭값(token: BearerToken): String {
		return parser.parseClaimsJws(token.value).body.subject
	}

	fun isValid(token: BearerToken, userDetails: UserDetails): Boolean {
		val claims = parser.parseClaimsJws(token.value).body
		print(claims)
		val unexpired = claims.expiration.after(Date.from(Instant.now()))
		print(unexpired)
		return unexpired && userDetails.username == claims.subject
//		val 대칭값 = get대칭값(token)
//		return userDetails.username == 대칭값
	}
}
