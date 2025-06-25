package com.upform.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExerciseLogDto {

    @JsonIgnore
    private Long id;

    @NotBlank
    private String exerciseName;

    @Min(1)
    private Integer sets;

    @Min(1)
    private Integer reps;

    @Min(1)
    private Double weight;

    @Min(1)
    @Max(10)
    private Integer exertion;
}
