package com.safetynet.api.alerts.model.dto;


import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicalRecordUpdateDto(@NotBlank String birthdate, 
									@NotNull List<String> medications,
									@NotNull List<String> allergies) {

}
