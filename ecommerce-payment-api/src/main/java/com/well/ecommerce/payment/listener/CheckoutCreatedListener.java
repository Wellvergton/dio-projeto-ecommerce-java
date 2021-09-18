package com.well.ecommerce.payment.listener;

import com.well.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.well.ecommerce.payment.event.PaymentCreatedEvent;
import com.well.ecommerce.payment.streaming.CheckoutProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CheckoutCreatedListener {
  private final CheckoutProcessor checkoutProcessor;

  @StreamListener(CheckoutProcessor.INPUT)
  public void handler(CheckoutCreatedEvent event) {
    final PaymentCreatedEvent paymentCreatedEvent = PaymentCreatedEvent.newBuilder()
        .setCheckoutCode(event.getCheckoutCode())
        .setCheckoutStatus(event.getStatus())
        .setPaymentCode(UUID.randomUUID().toString())
        .build();

    checkoutProcessor.output().send(MessageBuilder.withPayload(paymentCreatedEvent).build());
  }
}
