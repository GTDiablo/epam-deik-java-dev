package com.epam.training.ticketservice.core.user.impl;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.entity.User;
import com.epam.training.ticketservice.core.user.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private UserService underTest = new UserServiceImpl(userRepository);

    @Test
    public void testSignInShouldThrowNullPointerExceptionWhenUsernameIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.signIn(null, "pass"));
    }

    @Test
    public void testSignInShouldThrowNullPointerExceptionWhenPasswordIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.signIn("user", null));
    }

    @Test
    public void testSignInShouldSetDescribeAccountWhenUsernameAndPasswordAreCorrect() {
        // Given
        User user = new User("user", "user", User.Role.USER);
        Optional<User> expected = Optional.of(user);
        when(userRepository.findByUsernameAndPassword("user", "user")).thenReturn(Optional.of(user));

        // When
        Optional<UserDto> actual = underTest.signIn("user", "user");

        // Then
        assertEquals(expected.get().getUsername(), actual.get().getUsername());
        assertEquals(expected.get().getRole(), actual.get().getRole());
        verify(userRepository).findByUsernameAndPassword("user", "user");
    }

    @Test
    public void testSignInShouldReturnOptionalEmptyWhenUsernameOrPasswordAreNotCorrect() {
        // Given
        Optional<UserDto> expected = Optional.empty();
        when(userRepository.findByUsernameAndPassword("dummy", "dummy")).thenReturn(Optional.empty());

        // When
        Optional<UserDto> actual = underTest.signIn("dummy", "dummy");

        // Then
        assertEquals(expected, actual);
        verify(userRepository).findByUsernameAndPassword("dummy", "dummy");
    }
    @Test
    public void testSignOutShouldReturnOptionalEmptyWhenThereIsNoOneLoggedIn() {
        // Given
        Optional<UserDto> expected = Optional.empty();

        // When
        Optional<UserDto> actual = underTest.signOut();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testSignOutShouldReturnThePreviouslyLoggedInUserWhenThereIsALoggedInUser() {
        // Given
        UserDto user = new UserDto("user", User.Role.USER);
        Optional<UserDto> expected = Optional.of(user);
        underTest = new UserServiceImpl(user, userRepository);

        // When
        Optional<UserDto> actual = underTest.signOut();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testDescribeAccountShouldReturnTheLoggedInUserWhenThereIsALoggedInUser() {
        // Given
        UserDto user = new UserDto("user", User.Role.USER);
        Optional<UserDto> expected = Optional.of(user);
        underTest = new UserServiceImpl(user, userRepository);

        // When
        Optional<UserDto> actual = underTest.describeAccount();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetDescribeAccountShouldReturnOptionalEmptyWhenThereIsNoOneLoggedIn() {
        // Given
        Optional<UserDto> expected = Optional.empty();

        // When
        Optional<UserDto> actual = underTest.describeAccount();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testSignupShouldThrowNullPointerExceptionWhenUsernameIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.signUp(null, "pass"));
    }

    @Test
    public void testSignUpShouldThrowNullPointerExceptionWhenPasswordIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.signUp("user", null));
    }

    @Test
    public void testSignUpShouldCallUserRepositoryWhenTheInputIsValid() {
        // Given - When
        underTest.signUp("user", "pass");

        // Then
        verify(userRepository).save(new User("user", "pass", User.Role.USER));
    }

    @Test
    void singInPrivilegedShouldSetLoggedInUserWhenUsernameAndPasswordAreCorrect() {
        // Given
        User admin = new User("admin", "admin", User.Role.ADMIN);
        Optional<User> expected = Optional.of(admin);
        when(userRepository.findByUsernameAndPassword("admin", "admin")).thenReturn(Optional.of(admin));

        // When
        Optional<UserDto> actual = underTest.singInPrivileged("admin", "admin");

        // Then
        assertEquals(expected.get().getUsername(), actual.get().getUsername());
        assertEquals(expected.get().getRole(), actual.get().getRole());
        verify(userRepository).findByUsernameAndPassword("admin", "admin");

    }

    @Test
    public void testSignInPrivilegedShouldReturnOptionalEmptyWhenUsernameOrPasswordAreNotCorrect() {
        // Given
        Optional<UserDto> expected = Optional.empty();
        when(userRepository.findByUsernameAndPassword("admin", "admin")).thenReturn(Optional.empty());

        // When
        Optional<UserDto> actual = underTest.singInPrivileged("admin", "admin");

        // Then
        assertEquals(expected, actual);
        verify(userRepository).findByUsernameAndPassword("admin", "admin");
    }

    @Test
    void singInPrivilegedShouldThrowNullPointerExceptionWhenUsernameIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.singInPrivileged(null, "admin"));

    }

    @Test
    void singInPrivilegedShouldThrowNullPointerExceptionWhenPasswordIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.singInPrivileged("admin", null));

    }

    @Test
    void getRoleShouldReturnRoleOfUser() {
        User user = new User("user", "user", User.Role.USER);
        assertEquals(User.Role.USER, user.getRole());

    }
}