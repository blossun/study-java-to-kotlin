package com.group.libraryapp.domain.book

import javax.persistence.*

@Entity
class Book(
        val name: String,

        @Enumerated(EnumType.STRING) // 주의 - 문자열로 저장되도록 지정해줘야 한다.
        val type: BookType,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private val id: Long? = null,
) {

    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다")
        }
    }

    companion object {
        fun fixture(
                name: String = "책 이름",
                type: BookType = BookType.COMPUTER,
                id: Long? = null,
        ): Book {
            return Book(
                    name = name,
                    type = type,
                    id = id,
            )
        }
    }
}
