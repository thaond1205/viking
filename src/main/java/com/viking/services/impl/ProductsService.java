package com.viking.services.impl;

import com.viking.dto.ProductsDTO;
import com.viking.dto.UpdateProductDTO;
import com.viking.entities.Category;
import com.viking.entities.Product;
import com.viking.repositories.CategoryRepository;
import com.viking.repositories.ProductsRepository;
import com.viking.services.IProductsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductsService implements IProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductsDTO createProducts(ProductsDTO pDTO) {


        Product entity = mapper.map(pDTO, Product.class);
        Category category = categoryRepository.findCategoryById(pDTO.getCategoryId());
        entity.setCategory(category);
        if(pDTO.getCategoryId().equals("1")){
            entity.setAvailable(true);
        }else{
            entity.setAvailable(false);
        }
        Product product = productsRepository.save(entity);
        ProductsDTO productsDTO = mapper.map(product, ProductsDTO.class);
        return productsDTO;
    }

    @Override
    public List<Product> findAllProducts() {
        return productsRepository.findAll();
    }

    @Override
    public Product findProductById(Integer id) {
        return productsRepository.findById(id).get();
    }

    @Override
    public UpdateProductDTO findProductRestById(Integer id) {
        Product product = productsRepository.findById(id).get();
        UpdateProductDTO productsDTO = mapper.map(product, UpdateProductDTO.class);
        productsDTO.setCategoryId(product.getCategory().getId());
        if(product.getAvailable()){
            productsDTO.setAvailable("1");
        }else{
            productsDTO.setAvailable("0");
        }
        return productsDTO;
    }

    @Override
    public List<Product> findByCategoryId(String cid) {
        return categoryRepository.findByCategoryId(cid);
    }

    @Override
    public Product updateProduct(UpdateProductDTO entity) {

        Product product = productsRepository.findById(entity.getId()).get();
        product.setName(entity.getName());
        product.setPrice(entity.getPrice());
        if(entity.getAvailable().equals("1")){
            product.setAvailable(true);
        }else{
            product.setAvailable(false);
        }
        Category category = categoryRepository.findCategoryById(entity.getCategoryId());
        product.setCategory(category);

        return productsRepository.save(product);
    }
}
