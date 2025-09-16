package com.microservice.user.service;


import com.microservice.user.dto.UserDto;
import com.microservice.user.mapper.UserMapper;
import com.microservice.user.model.User;
import com.microservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveNewUser(UserDto userDto) {
        userRepository.save(UserMapper.parseDtoToUser(userDto));
    }

    public void updateExistingUser(Long id, UserDto userDto) {

        Optional<User> userModelOptional = userRepository.findById(id);

        if (userModelOptional.isPresent()) {
            User userModel = userModelOptional.get();
            userModel.setName(userDto.username());
            userModel.setPassword(userDto.password());
            userRepository.save(userModel);
        }
    }

    public UserDto getUser(UserDto userDto) {
        Optional<User> userModelOptional = userRepository.findByNameAndPassword(userDto.username(), userDto.password());

        return userModelOptional.map(UserMapper::parseUserToDto).orElseGet(() -> new UserDto("", "", null));

    }
}
