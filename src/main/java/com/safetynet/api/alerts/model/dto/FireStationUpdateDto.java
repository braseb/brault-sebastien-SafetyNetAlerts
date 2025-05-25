package com.safetynet.api.alerts.model.dto;


import jakarta.validation.constraints.Min;


public record FireStationUpdateDto(	@Min(value = 1) int stationNumber) {

}
