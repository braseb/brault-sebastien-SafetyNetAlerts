package com.safetynet.api.alerts.model.dto;


import jakarta.validation.constraints.NotBlank;


public record PersonUpdateDto(	@NotBlank String address,
								@NotBlank  String city,
								@NotBlank  String zip,
								@NotBlank  String phone,
								@NotBlank  String email) {

}
