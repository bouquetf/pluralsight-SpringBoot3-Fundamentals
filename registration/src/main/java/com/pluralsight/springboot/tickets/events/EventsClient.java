package com.pluralsight.springboot.tickets.events;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface EventsClient {
    @GetExchange("/products/{id}")
    Product getProbuctById(@PathVariable("id") int productId);

    @GetExchange("/events/{id}")
    Event getEventById(@PathVariable("id") int eventId);
}
