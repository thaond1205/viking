package com.viking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShoppingCartController {
    /*oke nhaaaaaaaaaaaaaaaaaaaaaaaa*/
    /*oke nhaaaaaaaaaaaaaaaaaaaaaaaa*/
    @RequestMapping("/cart/view")
    public String viewCart(){
        return "cart/view";
    }
}
