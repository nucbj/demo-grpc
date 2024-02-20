package com.demo.grpc.main.domain.imageStore.repository

import com.demo.grpc.main.domain.imageStore.model.ImageStoreEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ImageStoreRepository: ReactiveCrudRepository<ImageStoreEntity, Long>{
}