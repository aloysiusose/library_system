package dev.aloysius.library_system.services;

import dev.aloysius.library_system.customExceptions.CustomException;
import dev.aloysius.library_system.models.Users;
import dev.aloysius.library_system.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addNewUser(Users users) throws CustomException {
        if (userRepository.findByEmail(users.getEmail()).isPresent()) {
            throw new CustomException("%s already exists".formatted(users.getEmail()));
        }
        else {
            userRepository.save(users);
        }
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Users findById(int id) throws CustomException {
        return userRepository.findById(id).orElseThrow(()-> new CustomException("Invalid Id: %s"));
    }

    public Users updateUser(int id, Users user) throws CustomException {
        Users users = userRepository.findById(id).orElseThrow(() -> new CustomException("Invalid Id: %s"));
        users.setName(user.getName());
        users.setMembershipDate(user.getMembershipDate());
        return userRepository.save(users);
    }

    public void deleteUser(int id) throws CustomException {
        Users users = userRepository.findById(id).orElseThrow(() -> new CustomException("Invalid Id: %s"));
        userRepository.delete(users);
    }
}
