package com.viking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {

    @RequestMapping("/order/checkout")
    public String viewCheckout(){
        return "order/checkout";
    }

    @RequestMapping("/order/list")
    public String viewOrderList(){
        return "order/list";
    }

    @RequestMapping("/order/detail/{id}")
    public String viewOrderDetail(){
        return "order/detail";
    }
}
