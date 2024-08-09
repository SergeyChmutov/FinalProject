package ru.skypro.homework.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CreateOrUpdateAd")
public class CreateOrUpdateAdDTO {

    @Size(min = 4, max = 32)
    private String title;
    @Min(0)
    @Max(10_000_000)
    private Integer price;
    @Size(min = 8, max = 64)
    private String description;
}
