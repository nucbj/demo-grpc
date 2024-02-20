package com.demo.grpc.main.domain.handler

import com.demo.grpc.main.domain.model.ImageStoreEntity
import com.demo.grpc.main.domain.model.ImageStoreDto
import com.demo.grpc.main.domain.model.ImageStoreVo
import com.demo.grpc.main.domain.repository.ImageStoreRepository
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Service
class ImageStoreHandler(
	private val imageStoreRepository: ImageStoreRepository
) {

	suspend fun findImages(request: ServerRequest): ServerResponse {
		val count = imageStoreRepository.count().awaitSingle()
		val images = imageStoreRepository.findAll().collectList().awaitSingle()
		val imageVos = images.map { it.toImageStoreVo()}
		return ok().bodyValueAndAwait(ImageStoreDto(count, imageVos))
	}

	suspend fun findImage(request: ServerRequest): ServerResponse {
		var id = request.pathVariable("id");
		if(id.isNullOrEmpty()) {
			throw IllegalArgumentException("id is required")
		}
		return ok().bodyValueAndAwait(imageStoreRepository.findById(id.toLong()).awaitSingle().toVO())
	}

	suspend fun insertImage(request: ServerRequest): ServerResponse {
//		request.body().map { it.toImageStoreEntity() }
//			.flatMap { imageStoreRepository.save(it) }
//			.awaitSingle(
		return ok().bodyValueAndAwait(imageStoreRepository.findAll().collectList().awaitSingle())
	}

	fun ImageStoreEntity.toVO(): ImageStoreVo {
		return ImageStoreVo(
			seq = this.seq,
			id = this.id,
			imageUrl = this.imageUrl
		)
	}
}
