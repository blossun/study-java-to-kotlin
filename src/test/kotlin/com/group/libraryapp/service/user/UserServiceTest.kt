package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor( //생성자에 @Autowired를 붙이면 각 프로퍼티에 @Autowired를 생략 가능
    private val userRepository: UserRepository,
    private val userService: UserService,
) {

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }

    @DisplayName("유저 저장이 정상 동작한다.")
    @Test
    fun saveUserTest() {
        // given
        val request = UserCreateRequest("Solar", null)

        // when
        userService.saveUser(request)

        // then
        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("Solar")
        assertThat(results[0].age).isNull()
    }

    @DisplayName("유저 조회가 정상 동작한다.")
    @Test
    fun getUsersTest() {
        // given
        userRepository.saveAll(listOf(
            User("Solar", 20),
            User("Holar", 30),
        ))

        // when
        val results = userService.getUsers()

        // then
        assertThat(results).hasSize(2) // [UserResponse(), UserResponse()]
        assertThat(results).extracting("name").containsExactlyInAnyOrder("Solar", "Holar") // ["Solar", "Holar"]
    }

    @DisplayName("유저 수정이 정상 동작한다.")
    @Test
    fun updateUserNameTest() {
        // given
        val savedUser = userRepository.save(User("Solar", null))
        val request = UserUpdateRequest(savedUser.id!!, "Holar")

        // when
        userService.updateUserName(request)

        // then
        val result = userRepository.findAll()[0]
        assertThat(request.name).isEqualTo("Holar")
    }

    @DisplayName("유저 삭제가 정상 동작한다.")
    @Test
    fun deleteUserTest() {
        // given
        userRepository.save(User("Solar", null))

        // when
        userService.deleteUser("Solar")

        // then
        assertThat(userRepository.findAll()).isEmpty()
    }
}
