package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor( //생성자에 @Autowired를 붙이면 각 프로퍼티에 @Autowired를 생략 가능
    private val userRepository: UserRepository,
    private val userService: UserService,
) {
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
}
