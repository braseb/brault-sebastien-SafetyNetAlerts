package com.safetynet.api.alerts.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonUpdateDto(	@NotBlank @NotNull String address,
								@NotBlank @NotNull String city,
								@NotBlank @NotNull String zip,
								@NotBlank @NotNull String phone,
								@NotBlank @NotNull String email) {

}
