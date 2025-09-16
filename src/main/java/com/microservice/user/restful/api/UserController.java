package com.microservice.user.restful.api;

import com.microservice.user.dto.UserDto;
import com.microservice.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/find")
    public @ResponseBody UserDto findUser(@RequestBody UserDto userDto) {
        return userService.getUser(userDto);
    }

    @PostMapping("/save")
    public void saveNewUser(@RequestBody UserDto userDto) {
        userService.saveNewUser(userDto);
    }

    @PutMapping("/update/{id}")
    public void updateExistingUser(@PathVariable Long id,@RequestBody UserDto userDto) {
        userService.updateExistingUser(id, userDto);
    }
}
