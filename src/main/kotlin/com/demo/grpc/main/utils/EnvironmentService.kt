package com.demo.grpc.main.utils

import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class EnvironmentService(
	private val environment: Environment,
) {

	/**
	 * DEV or LOCAL
	 */
	fun isLocalOrDev(): Boolean {
		for (activeProfile in environment.activeProfiles) {
			if (activeProfile.startsWith("local") || activeProfile.startsWith("dev")) {
				return true
			}
		}
		return false
	}

	/**
	 * LOCAL
	 */
	fun isLocal(): Boolean {
		for (activeProfile in environment.activeProfiles) {
			if (activeProfile.startsWith("local")) {
				return true
			}
		}
		return false
	}

	/**
	 * LIVE
	 */
	fun isLive(): Boolean {
		for (activeProfile in environment.activeProfiles) {
			if (activeProfile.startsWith("live")) {

				return true
			}
		}
		return false
	}

	fun getProfile(): String {
		for (activeProfile in environment.activeProfiles) {
			if (activeProfile.startsWith("live") || activeProfile.startsWith("dev") || activeProfile.startsWith("local")) {
				return activeProfile
			}
		}
		return "Unknown"
	}
}
