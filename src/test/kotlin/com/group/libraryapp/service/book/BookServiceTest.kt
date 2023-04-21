package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @AfterEach
    fun clean() {
        bookRepository.deleteAll()
        userRepository.deleteAll() // User(1) - UserLoanHistory(N) OneToMany 구조이기 때문에 deleteAll로 삭제하면 자식테이블인 userLoanHistory도 같이 삭제된다. (따라서 UserLoanHistory는 따로 deleteAll 하지 않아도 된다.)
    }

    @DisplayName("책 등록이 정상 동작한다.")
    @Test
    fun saveBookTest() {
        // given
        val bookName = "이상한 나라의 엘리스"
        val request = BookRequest(bookName)

        // when
        bookService.saveBook(request)

        // then
        val books = bookRepository.findAll()
        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo(bookName)
    }

    @DisplayName("책 대출이 정상 동작한다.")
    @Test
    fun loanBookTest() {
        // given
        val bookName = "클린 코드"
        bookRepository.save(Book(bookName))
        val savedUser = userRepository.save(User("Solar", null))
        val request = BookLoanRequest("Solar", bookName)

        // when
        bookService.loanBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo(bookName)
        assertThat(results[0].user.id).isEqualTo(savedUser.id)
        assertThat(results[0].isReturn).isFalse()
    }

    @DisplayName("책이 이미 대출되어 있다면, 신규 대출이 실패한다.")
    @Test
    fun loanBookFailTest() {
        // given
        val bookName = "클린 코드"
        bookRepository.save(Book(bookName))
        val savedUser = userRepository.save(User("Solar", null))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, bookName, false))
        val request = BookLoanRequest("Solar", bookName)

        // when & then
        assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }.apply {
            assertThat(message).isEqualTo("진작 대출되어 있는 책입니다")
        }
    }

    @DisplayName("책 반납이 정상 동작한다.")
    @Test
    fun returnBookTest() {
        // given
        val bookName = "클린 코드"
        bookRepository.save(Book(bookName))
        val savedUser = userRepository.save(User("Solar", null))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, bookName, false))
        val request = BookReturnRequest("Solar", bookName)

        // when
        bookService.returnBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].isReturn).isTrue()
    }
}
