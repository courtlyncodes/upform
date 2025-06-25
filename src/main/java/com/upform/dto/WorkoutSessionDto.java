package com.upform.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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
public class WorkoutSessionDto {

    @JsonIgnore
    private Long id;

    private LocalDate date;
    private String difficulty; // easy, moderate, hard

    @Min(1)
    private Integer durationInMinutes;

    @Size(max = 1000, message = "Notes must be under 1000 characters")
    private String notes;

    private List<@Valid ExerciseLogDto> exercises = new ArrayList<>();
}
