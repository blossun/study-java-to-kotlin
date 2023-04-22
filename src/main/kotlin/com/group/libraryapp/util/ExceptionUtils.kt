package com.group.libraryapp.util

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun fail(): Nothing {
    throw IllegalArgumentException()
}

// 참고 - CrudRepositoryExtensions.kt
fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T { // null일 경우 Exception을 던질 것이기 때문에 nullable한 리턴값을 쓰지 않는다.
    return this.findByIdOrNull(id) ?: fail()
}
