package com.kazyon.orderapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(example = "user")
    @NotBlank
    private String username;

    @NotBlank   @Schema(example = "user")

    private String password;
}
