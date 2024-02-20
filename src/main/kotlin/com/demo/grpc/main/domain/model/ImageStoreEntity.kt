package com.demo.grpc.main.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.ZonedDateTime

@Table(name= "image_store")
data class ImageStoreEntity(

	@Id
	val seq: Long,
	val id: String,
	val type: String,
	val imageUrl: String,
	val createdAt: ZonedDateTime,
) {
	fun toImageStoreVo(): ImageStoreVo {
		return ImageStoreVo(
			seq = this.seq,
			id = this.id,
			imageUrl = this.imageUrl
		)
	}
}