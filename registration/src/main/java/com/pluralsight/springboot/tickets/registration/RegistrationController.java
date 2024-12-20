package com.pluralsight.springboot.tickets.registration;

import com.pluralsight.springboot.tickets.events.Event;
import com.pluralsight.springboot.tickets.events.EventsClient;
import com.pluralsight.springboot.tickets.events.Product;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/registrations")
public class RegistrationController {
    private final RegistrationRepository registrationRepository;
    private final EventsClient eventsClient;


    public RegistrationController(RegistrationRepository registrationRepository, EventsClient eventsClient) {
        this.registrationRepository = registrationRepository;
        this.eventsClient = eventsClient;
    }

    @PostMapping
    public Registration create(@RequestBody @Valid Registration registration) {
        Product product = eventsClient.getProbuctById(registration.productId());
        Event event = eventsClient.getEventById(product.eventId());

        return registrationRepository.save(new Registration(
                null,
                registration.productId(),
                event.name(),
                product.price(),
                registration.ticketCode(),
                registration.attendeeName()));
    }

    @GetMapping(path = "/{ticketCode}")
    public Registration get(@PathVariable("ticketCode") String ticketCode) {
        return registrationRepository
                .findByTicketCode(ticketCode)
                .orElseThrow(
                        () -> new NoSuchElementException("Registration with ticket code" + ticketCode + " not found")
                );
    }

    @PutMapping
    public Registration update(@RequestBody Registration registration) {
        String ticketCode = registration.ticketCode();
        Registration existing = registrationRepository.findByTicketCode(registration
                .ticketCode())
                .orElseThrow(
                        () -> new NoSuchElementException("Registration with ticket code" + ticketCode + " not found"));
        return registrationRepository.save(
                new Registration(existing.id(),
                        existing.productId(),
                        existing.eventName(),
                        existing.amount(),
                        ticketCode,
                        registration.attendeeName()));
    }

    @DeleteMapping(path = "/{ticketCode}")
    public void delete(@PathVariable("ticketCode") String ticketCode) {
        registrationRepository.deleteByTicketCode(ticketCode);
    }
}
