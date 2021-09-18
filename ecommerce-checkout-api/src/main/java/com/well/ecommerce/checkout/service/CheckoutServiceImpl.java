package com.well.ecommerce.checkout.service;

import com.well.ecommerce.checkout.entity.CheckoutEntity;
import com.hatanaka.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.well.ecommerce.checkout.repository.CheckoutRepository;
import com.well.ecommerce.checkout.resource.checkout.CheckoutRequest;
import com.well.ecommerce.checkout.streaming.CheckoutCreatedSource;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
  private final CheckoutRepository checkoutRepository;
  private final CheckoutCreatedSource checkoutCreatedSource;

  @Override
  public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {
    final CheckoutEntity checkoutEntity = CheckoutEntity.builder()
        .code(UUID.randomUUID().toString())
        .status(CheckoutEntity.Status.CREATED)
        .build();
    final CheckoutEntity entity = checkoutRepository.save(checkoutEntity);
    final CheckoutCreatedEvent checkoutCreatedEvent = CheckoutCreatedEvent.newBuilder()
        .setCheckoutCode(entity.getCode())
        .setStatus(entity.getStatus().name())
        .build();

    checkoutCreatedSource.output().send(MessageBuilder.withPayload(checkoutCreatedEvent).build());

    return Optional.of(entity);
  }
}
