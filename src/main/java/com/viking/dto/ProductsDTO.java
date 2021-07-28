package com.viking.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductsDTO {
    private String name;

    private String image;

    private Double price;

    private String available;

    private String categoryId;


}
