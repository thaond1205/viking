package com.viking.controller;

import com.viking.entities.Product;
import com.viking.services.impl.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @RequestMapping("/product/list")
    public String getAllProducts(Model model, @RequestParam("cid") Optional<String> cid) {
        if(cid.isPresent()) {
            List<Product> productsList = productsService.findByCategoryId(cid.get());

            model.addAttribute("items", productsList);
        }
        else {
            List<Product> productsList = productsService.findAllProducts();

            model.addAttribute("items", productsList);
        }
        return "product/list";
    }


    @RequestMapping("/product/detail/{id}")
    public String productDetail(Model model, @PathVariable("id") Integer id){
        Product item = productsService.findProductById(id);

        model.addAttribute("item", item);
        return "product/detail";
    }
}
