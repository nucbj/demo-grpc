package com.demo.grpc.main.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter

@Configuration
@EnableWebFluxSecurity
class FilterChain {

	@Bean
	fun passwordEncoder() = BCryptPasswordEncoder()

	@Bean
	fun securityFilterChain(
		http: ServerHttpSecurity,
		authManager: ReactiveAuthenticationManager,
		converter: ServerAuthenticationConverter
	): SecurityWebFilterChain {
		val filter = AuthenticationWebFilter(authManager)
		filter.setServerAuthenticationConverter(converter)

		return http
			.csrf { it.disable() }
			.formLogin { it.disable() }
			.httpBasic { it.disable() }
			.logout { it.disable() }
			.authorizeExchange {
				it
//					.pathMatchers("/api/**").permitAll()
//					.pathMatchers("/api/**").authenticated()
//					.pathMatchers("/api/auth").permitAll()
					.anyExchange().permitAll()

			}
			.addFilterAfter(filter, SecurityWebFiltersOrder.AUTHENTICATION).build()
	}
}
