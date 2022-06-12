package com.yk.ecommerce.dto;

import com.yk.ecommerce.entity.Address;
import com.yk.ecommerce.entity.Customer;
import com.yk.ecommerce.entity.Order;
import com.yk.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
  private Customer customer;
  private Address shippingAddress;
  private Address billingAddress;
  private Order order;
  private Set<OrderItem> orderItems;
}
