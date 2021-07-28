package com.viking.services;

import com.viking.dto.ProductsDTO;
import com.viking.dto.UpdateProductDTO;
import com.viking.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IProductsService {

    ProductsDTO createProducts(ProductsDTO product);
    List<Product> findAllProducts();
    Product findProductById(Integer id);
    UpdateProductDTO findProductRestById(Integer id);
    List<Product> findByCategoryId(String cid);
    Product updateProduct(UpdateProductDTO entity);
}
