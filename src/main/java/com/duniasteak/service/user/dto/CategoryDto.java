package com.duniasteak.service.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private Integer id;
    private String name;
    private List<ProductDto> product;
    private Boolean isSelected;
}
