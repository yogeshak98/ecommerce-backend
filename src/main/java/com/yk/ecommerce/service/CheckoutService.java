package com.yk.ecommerce.service;

import com.yk.ecommerce.dto.Purchase;
import com.yk.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
  PurchaseResponse placeOrder(Purchase purchase);
}
