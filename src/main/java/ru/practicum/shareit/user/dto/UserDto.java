package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDto {
    @NotBlank
    private String name; // имя или логин пользователя;
    @NotBlank @Email @EqualsAndHashCode.Include
    private String email; // адрес электронной почты (два пользователя не могут иметь одинаковый адрес электронной почты).
}