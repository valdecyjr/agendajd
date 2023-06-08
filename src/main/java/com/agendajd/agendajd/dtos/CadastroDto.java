package com.agendajd.agendajd.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CadastroDto {
    @NotBlank
    @Size(max = 20, min = 4)
    private String username;

    @NotBlank
    @Size(max = 100, min = 4)
    private String Fullname;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NotBlank
    @Email
    @Size(max = 100)
    private String Email;

    private LocalDate dateBirth;
}
