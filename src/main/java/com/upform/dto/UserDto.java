package com.upform.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @JsonIgnore
    private Long id;

    private String name;
    private String email;
    private String experienceLevel;
    private String goal;
    private LocalDate joinedDate;
    private List<@Valid WorkoutSessionDto> workoutSessionList = new ArrayList<>();
}
