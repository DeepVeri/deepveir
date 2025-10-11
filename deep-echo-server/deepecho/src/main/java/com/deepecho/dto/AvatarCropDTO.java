package com.deepecho.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;

@Data
public class AvatarCropDTO {
    @Min(0)
    private Integer x = 0;
    
    @Min(0)
    private Integer y = 0;
    
    @Min(1)
    private Integer width;
    
    @Min(1)
    private Integer height;
    
    private Integer rotate = 0;
} 