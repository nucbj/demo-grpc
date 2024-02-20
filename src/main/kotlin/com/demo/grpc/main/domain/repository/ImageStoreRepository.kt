package com.demo.grpc.main.domain.repository

import com.demo.grpc.main.domain.model.ImageStoreEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ImageStoreRepository: ReactiveCrudRepository<ImageStoreEntity, Long>{
}