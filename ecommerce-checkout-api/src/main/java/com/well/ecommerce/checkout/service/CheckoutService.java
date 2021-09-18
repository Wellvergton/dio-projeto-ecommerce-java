package com.well.ecommerce.checkout.service;

import com.well.ecommerce.checkout.entity.CheckoutEntity;
import com.well.ecommerce.checkout.resource.checkout.CheckoutRequest;

import java.util.Optional;

public interface CheckoutService {
  Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);
}
