package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User

data class UserResponse(
    val id: Long,
    val name: String,
    val age: Int?,
) {
    // 정적 팩토리 메서드 사용하기
    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                id = user.id!!,
                name = user.name,
                age = user.age,
            )
        }
    }

    // 부생성자 사용하기
    constructor(user: User) : this(
        id = user.id!!,
        name = user.name,
        age = user.age,
    )
}
