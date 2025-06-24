package com.upform.service;

import com.upform.dto.UserDto;
import com.upform.model.User;
import com.upform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

// TODO: Add negative tests
@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    private UserDto userDto;


    @BeforeEach
    public void setup() {
        user = User.builder()
                .id(1L)
                .authId("auth0")
                .name("Court")
                .email("court1@gmail.com")
                .experienceLevel("Intermediate")
                .goal("weight loss")
                .joinedDate(LocalDate.of(2025, 6, 24))
                .workoutSessionList(new ArrayList<>())
                .build();

        userDto = UserDto.builder()
                .id(1L)
                .name("Court")
                .email("court1@gmail.com")
                .experienceLevel("Intermediate")
                .goal("weight loss")
                .joinedDate(LocalDate.of(2025, 6, 24))
                .workoutSessionList(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("Should create a new user")
    void createUserTest() {
        given(userRepository.save(any(User.class))).willReturn(user);

        UserDto result = userService.createUser(userDto);

        assertThat(result.getName()).isEqualTo("Court");
    }

    @Test
    @DisplayName("Should return a user when an ID is provided")
    void getUserByIdTest() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        UserDto result = userService.getUserById(user.getId());

        assertThat(result.getName()).isEqualTo("Court");
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should return all users")
    void getAllUsersTest() {
        User user1 = User.builder()
                .id(2L)
                .authId("auth0abc")
                .name("Sheila")
                .email("sheila@gmail.com")
                .experienceLevel("Beginner")
                .goal("increase strength")
                .joinedDate(LocalDate.of(2024, 9, 18))
                .workoutSessionList(new ArrayList<>())
                .build();

        given(userRepository.findAll()).willReturn(List.of(user, user1));

        List<UserDto> result = userService.getAllUsers();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(1).getName()).isEqualTo("Sheila");
    }

    @Test
    @DisplayName("Should update user's detail when ID is provided")
    void updateUserTest() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));
        given(userRepository.save(any(User.class))).willReturn(user);

        UserDto updatedDto = UserDto.builder()
                .name("Coco")
                .email("court1@gmail.com")
                .experienceLevel("Intermediate")
                .goal("increase strength")
                .joinedDate(LocalDate.of(2025, 6, 24))
                .workoutSessionList(new ArrayList<>())
                .build();

        UserDto result = userService.updateUserById(1L, updatedDto);

        assertThat(result.getName()).isEqualTo("Coco");
        assertThat(result.getGoal()).isEqualTo("increase strength");
    }

    @Test
    @DisplayName("Should delete a user when ID is provided")
    void deleteUserTest() {
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(user);
    }
}
