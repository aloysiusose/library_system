package dev.aloysius.library_system.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aloysius.library_system.models.Users;
import dev.aloysius.library_system.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;

    private Users user1;
    private Users user2;
    private List<Users> usersList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        user1 = new Users();
        user1.setId(1);
        user1.setName("User One");
        user1.setEmail("user1@example.com");
        user2 = new Users();
        user2.setId(2);
        user2.setName("User Two");
        user2.setEmail("user2@example.com");
        usersList = Arrays.asList(user1, user2);
    }

    @Test
    void testAddNewUser() throws Exception {
        doNothing().when(userService).addNewUser(any(Users.class));

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk());

        verify(userService, times(1)).addNewUser(any(Users.class));
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(usersList);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(user1.getId()))
                .andExpect(jsonPath("$[0].name").value(user1.getName()))
                .andExpect(jsonPath("$[0].email").value(user1.getEmail()))
                .andExpect(jsonPath("$[1].id").value(user2.getId()))
                .andExpect(jsonPath("$[1].name").value(user2.getName()))
                .andExpect(jsonPath("$[1].email").value(user2.getEmail()));

        verify(userService, times(1)).findAll();
    }

    @Test
    void testGetUser() throws Exception {
        when(userService.findById(1)).thenReturn(user1);

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.name").value(user1.getName()))
                .andExpect(jsonPath("$.email").value(user1.getEmail()));

        verify(userService, times(1)).findById(1);
    }

    @Test
    void testUpdateUser() throws Exception {
        when(userService.updateUser(eq(1), any(Users.class))).thenReturn(user1);

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.name").value(user1.getName()))
                .andExpect(jsonPath("$.email").value(user1.getEmail()));

        verify(userService, times(1)).updateUser(eq(1), any(Users.class));
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1);

        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(1);
    }

}