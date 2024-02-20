package com.demo.grpc.main.domain.imageStore.model

import java.sql.Timestamp
import java.time.ZonedDateTime

data class ImageStoreVo(
	val seq: Long,
	val id: String,
	val imageUrl: String,
)
