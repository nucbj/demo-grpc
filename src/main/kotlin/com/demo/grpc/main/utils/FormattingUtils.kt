package com.demo.grpc.main.utils

class FormattingUtils {
	companion object {
		fun maskingName(str: String): String {
			var maskedName = str
			var firstName = ""
			var masked = ""
			var lastName = ""
			if (str.length == 2) {
				maskedName = str[0].toString() + "*"
			} else if (str.length > 2) {
				firstName = str.substring(0, 1)
				lastName = str.substring(str.length - 1)
				for (i in 0 until (str.length - 2)) {
					masked += "*"
				}
				maskedName = firstName + masked + lastName
			}
			return maskedName
		}
	}
}
