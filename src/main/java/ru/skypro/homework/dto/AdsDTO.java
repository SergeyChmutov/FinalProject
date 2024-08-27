package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@Builder
@Schema(name = "Ads")
public class AdsDTO {

    private Integer count;
    private List<AdDTO> results;
}
