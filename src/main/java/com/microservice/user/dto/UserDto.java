package com.microservice.user.dto;

import java.time.LocalDate;

public record UserDto(String username, String password, LocalDate dateCreated) {
}
