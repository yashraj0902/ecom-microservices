package com.app.ecom.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.app.ecom.entity.User;
import com.app.ecom.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    private List<User> userList = new ArrayList<>();
//    private Long nextId = 1L;



    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> fetchUser(Long id) {
       return userRepository.findById(id);
    }

    public void addUser(User user) {
       userRepository.save(user);
    }

    public boolean updateUser(Long id, User updatedUser)
    {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            userRepository.save(existingUser);
            return  true;
        }).orElse(false);
    }

    public void deleteUser(Long id)
    {
        userRepository.deleteById(id);

    }
}
