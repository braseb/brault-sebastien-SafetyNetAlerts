package com.safetynet.api.alerts.model.dto;


import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MedicalRecordUpdateDto(@NotBlank @Pattern(regexp = "^\\d\\d/\\d\\d/\\d{4}$",
														message = "The date must be to this format MM/dd/yyyy") String birthdate, 
									@NotNull List<String> medications,
									@NotNull List<String> allergies) {

}
