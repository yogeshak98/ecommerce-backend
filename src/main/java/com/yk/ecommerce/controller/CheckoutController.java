package com.yk.ecommerce.controller;

import com.yk.ecommerce.dto.Purchase;
import com.yk.ecommerce.dto.PurchaseResponse;
import com.yk.ecommerce.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
  private final CheckoutService checkoutService;

  @Autowired
  public CheckoutController(CheckoutService checkoutService){
    this.checkoutService = checkoutService;
  }

  @PostMapping("/purchase")
  public PurchaseResponse placeOrder(@RequestBody Purchase purchase){
    return checkoutService.placeOrder(purchase);
  }
}
