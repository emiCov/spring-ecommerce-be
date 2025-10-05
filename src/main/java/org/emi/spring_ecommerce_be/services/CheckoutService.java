package org.emi.spring_ecommerce_be.services;

import org.emi.spring_ecommerce_be.db.entities.OrderEntity;
import org.emi.spring_ecommerce_be.db.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CheckoutService {

  private final OrderService orderService;
  private final OrderRepository orderRepository;
  private final PaymentService paymentService;

  public CheckoutService(
      OrderService orderService, OrderRepository orderRepository, PaymentService paymentService) {
    this.orderService = orderService;
    this.orderRepository = orderRepository;
    this.paymentService = paymentService;
  }

  @Transactional(readOnly = false)
  public String completeOrder(long orderId) {
    OrderEntity order = orderRepository.findById(orderId).orElseThrow();

    boolean paymentSuccess = paymentService.executePayment(order.getTotal());

    if (paymentSuccess) {
      orderService.confirmOrder(order);
      return "Payment was successful. Order is completed.";
    }

    orderService.cancelOrder(order);
    return "Payment failed. Please try again.";
  }
}
