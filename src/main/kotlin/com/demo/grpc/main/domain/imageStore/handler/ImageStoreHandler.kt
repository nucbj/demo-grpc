package com.demo.grpc.main.domain.imageStore.handler

import com.demo.grpc.main.config.grpc.GreeterGrpcService
import com.demo.grpc.main.domain.imageStore.model.ImageStoreEntity
import com.demo.grpc.main.domain.imageStore.model.ImageStoreDto
import com.demo.grpc.main.domain.imageStore.model.ImageStoreVo
import com.demo.grpc.main.domain.imageStore.repository.ImageStoreRepository
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

	suspend fun startGrpc(request: ServerRequest): ServerResponse {
		GreeterGrpcService(9999, imageStoreRepository).start()
//		request.body().map { it.toImageStoreEntity() }
//			.flatMap { imageStoreRepository.save(it) }
//			.awaitSingle(
		return ok().bodyValueAndAwait("grpc server started")
	}

	fun ImageStoreEntity.toVO(): ImageStoreVo {
		return ImageStoreVo(
			seq = this.seq,
			id = this.id,
			imageUrl = this.imageUrl
		)
	}
}
