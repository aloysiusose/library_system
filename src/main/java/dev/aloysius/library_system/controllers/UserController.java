package dev.aloysius.library_system.controllers;

import dev.aloysius.library_system.customExceptions.CustomException;
import dev.aloysius.library_system.models.Users;
import dev.aloysius.library_system.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void addNewUser(@RequestBody Users users) throws CustomException {
        userService.addNewUser(users);
    }
    @GetMapping
    public List<Users> getAllUsers(){
        return userService.findAll();
    }
    @GetMapping("/{id}")
    public Users getUser(@PathVariable int id) throws CustomException {
        return userService.findById(id);
    }
    @PutMapping("/{id}")
    public Users updateUser(@PathVariable int id, @RequestBody Users users) throws CustomException {
        return userService.updateUser(id, users);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) throws CustomException {
        userService.deleteUser(id);
    }
}
