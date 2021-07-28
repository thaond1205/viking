package com.viking.dto;

import lombok.Data;

@Data
public class UpdateProductDTO {
    private Integer id;

    private String name;

    private String image;

    private Double price;

    private String available;

    private String categoryId;
}
