package com.demo.grpc.main.config.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils

@FunctionalInterface
class BearerToken(
	val value: String
) : AbstractAuthenticationToken(AuthorityUtils.NO_AUTHORITIES) {
	override fun getCredentials() = value
	override fun getPrincipal() = value
}
