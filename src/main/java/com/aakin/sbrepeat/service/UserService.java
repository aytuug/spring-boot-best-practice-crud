package com.aakin.sbrepeat.service;

import com.aakin.sbrepeat.dto.UserDto;
import com.aakin.sbrepeat.dto.convert.UserDtoConverter;
import com.aakin.sbrepeat.dto.request.CreateUserRequest;
import com.aakin.sbrepeat.dto.request.UpdateUserRequest;
import com.aakin.sbrepeat.entity.User;
import com.aakin.sbrepeat.exception.EmailAlreadyExistException;
import com.aakin.sbrepeat.exception.ResourceNotFoundException;
import com.aakin.sbrepeat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDtoConverter userDtoConverter;


    public UserDto createUser(CreateUserRequest createUserRequest){
        User user = new User(
                createUserRequest.getId(),
                createUserRequest.getFirstName(),
                createUserRequest.getLastName(),
                createUserRequest.getEmail()
        );
        Optional<User> optionalUser = userRepository.findByEmail(createUserRequest.getEmail());
        if (optionalUser.isPresent()){
            throw new EmailAlreadyExistException("Email Already exist for user!!");
        }
        userRepository.save(user);
        return userDtoConverter.convertDto(user);
    }

    public UserDto getUserById(Long id){


        return userRepository.findById(id)
                .map(userDtoConverter::convertDto).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
    }

    public List<UserDto> getAllUsers(){
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(userDtoConverter::convertDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(Long id, UpdateUserRequest updateUserRequest){
        Optional<User> userOptional = userRepository.findById(id);

        userOptional.ifPresent(user -> {
            user.setFirstName(updateUserRequest.getFirstName());
            user.setLastName(updateUserRequest.getLastName());
            user.setEmail(updateUserRequest.getEmail());

            userRepository.save(user);
        });

        return userOptional.map(userDtoConverter::convertDto).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        userRepository.deleteById(id);
    }


}
