package com.yk.ecommerce.service;

import com.yk.ecommerce.dao.CustomerRepository;
import com.yk.ecommerce.dto.Purchase;
import com.yk.ecommerce.dto.PurchaseResponse;
import com.yk.ecommerce.entity.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;


@Service
public class CheckoutServiceImpl implements CheckoutService {

  private final CustomerRepository customerRepository;

  @Autowired
  public CheckoutServiceImpl(CustomerRepository customerRepository){
    this.customerRepository = customerRepository;
  }

  @Override
  @Transactional
  public PurchaseResponse placeOrder(Purchase purchase) {
    // retrieve order info from dto
    var order = purchase.getOrder();

    // generate tracking number
    var orderTrackingNumber = generateOrderTrackingNumber();
    order.setOrderTrackingNumber(orderTrackingNumber);

    // populate order with order items
    Set<OrderItem> orderItems = purchase.getOrderItems();
    orderItems.forEach(order::addOrderItem);

    // populate order with billing address and shipping address
    order.setBillingAddress(purchase.getBillingAddress());
    order.setShippingAddress(purchase.getShippingAddress());

    // populate customer with order
    var customer = purchase.getCustomer();
    customer.addOrder(order);

    // save to database
    customerRepository.save(customer);

    // return response
    return new PurchaseResponse(orderTrackingNumber);
  }

  private String generateOrderTrackingNumber() {
    return UUID.randomUUID().toString();
  }
}
