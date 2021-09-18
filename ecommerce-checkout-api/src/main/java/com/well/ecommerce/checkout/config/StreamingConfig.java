package com.well.ecommerce.checkout.config;

import com.well.ecommerce.checkout.streaming.CheckoutCreatedSource;
import com.well.ecommerce.checkout.streaming.PaymentPaidSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(value = {
    CheckoutCreatedSource.class,
    PaymentPaidSink.class
})
public class StreamingConfig {
}
