package dev.aloysius.library_system.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aloysius.library_system.customExceptions.CustomException;
import dev.aloysius.library_system.models.Users;
import dev.aloysius.library_system.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private Users user1;
    private Users user2;

    @BeforeEach
    void setUp() {


        user1 = new Users();
        user1.setId(1);
        user1.setName("User One");
        user1.setEmail("user1@example.com");
        user2 = new Users();
        user2.setId(2);
        user2.setName("User Two");
        user2.setEmail("user2@example.com");

    }

    @Test
    void testAddNewUser() throws CustomException {
        when(userRepository.findByEmail(user1.getEmail())).thenReturn(Optional.empty());

        userService.addNewUser(user1);

        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void testAddNewUserThrowsException() {
        when(userRepository.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));

        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.addNewUser(user1);
        });

        assertEquals("user1@example.com already exists", exception.getMessage());
        verify(userRepository, never()).save(user1);
    }

    @Test
    void testFindAll() {
        List<Users> usersList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(usersList);

        List<Users> result = userService.findAll();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindById() throws CustomException {
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));

        Users result = userService.findById(1);

        assertNotNull(result);
        assertEquals(user1.getName(), result.getName());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdThrowsException() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.findById(1);
        });

        assertEquals("Invalid Id: %s", exception.getMessage());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateUser() throws CustomException {
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(userRepository.save(any(Users.class))).thenReturn(user1);

        Users updatedUser = new Users();
        updatedUser.setId(1);
        updatedUser.setName("Updated Name");
        updatedUser.setEmail("user1@example.com");
        updatedUser.setMembershipDate(LocalDate.of(2023, 01, 01));

        Users result = userService.updateUser(1, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getName(), result.getName());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void testUpdateUserThrowsException() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Users updatedUser = new Users();
        updatedUser.setId(1);
        updatedUser.setName("Updated Name");
        updatedUser.setEmail("user1@example.com");
        updatedUser.setMembershipDate(LocalDate.of(2023, 01, 01));

        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.updateUser(1, updatedUser);
        });

        assertEquals("Invalid Id: %s", exception.getMessage());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    void testDeleteUser() throws CustomException {
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));

        userService.deleteUser(1);

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).delete(user1);
    }

    @Test
    void testDeleteUserThrowsException() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            userService.deleteUser(1);
        });

        assertEquals("Invalid Id: %s", exception.getMessage());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, never()).delete(any(Users.class));
    }

}