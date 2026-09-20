package com.app.ecom.controller;
import com.app.ecom.entity.User;
import com.app.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers()
    {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.fetchUser(id)
                .map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.<User>notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user)
    {
       userService.addUser(user);
       return ResponseEntity.ok("User added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser)
    {
        boolean updated = userService.updateUser(id, updatedUser);
        if(updated) return ResponseEntity.ok("User updated successfully!");
        return ResponseEntity.notFound().build();
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id)
//    {
//        boolean deleted = userService.deleteUser(id);
//        if(!deleted)
//        {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.noContent().build();
//
//
//    }


}

