package com.app.ecom.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.app.ecom.dto.AddressDTO;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    private List<User> userList = new ArrayList<>();
//    private Long nextId = 1L;



    public List<UserResponse> fetchAllUsers() {
        return userRepository.findAll().stream().map(this::maptoUserResponse).collect(Collectors.toList());
    }

    public Optional<UserResponse> fetchUser(Long id) {
       return userRepository.findById(id).map(this::maptoUserResponse);
    }


    public void addUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }



    public boolean updateUser(Long id, UserRequest updatedUserRequest)
    {
        return userRepository.findById(id).map(existingUser -> {
           updateUserFromRequest(existingUser, updatedUserRequest);
            userRepository.save(existingUser);
            return  true;
        }).orElse(false);
    }

    public void deleteUser(Long id)
    {
        userRepository.deleteById(id);

    }

    private UserResponse maptoUserResponse(User user)
    {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());

        if(user.getAddress() != null)
        {

            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            addressDTO.setStreet(user.getAddress().getStreet());
            response.setAddress(addressDTO);

        }
        return response;
    }

    private void updateUserFromRequest(User user, UserRequest userRequest)
    {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if(userRequest.getAddress() != null)
        {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipcode(userRequest.getAddress().getZipcode());
        }


    }



}
