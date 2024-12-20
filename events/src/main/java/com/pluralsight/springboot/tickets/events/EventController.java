package com.pluralsight.springboot.tickets.events;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class EventController {
    private final OrganizerRepository organizerRepository;
    private final EventRepository eventRepository;
    private final ProductRepository productRepository;

    public EventController(OrganizerRepository organizerRepository,
                           EventRepository eventRepository,
                           ProductRepository productRepository) {
        this.organizerRepository = organizerRepository;
        this.eventRepository = eventRepository;
        this.productRepository = productRepository;
    }

    @GetMapping(path = "/organizer")
    public List<Organizer> getOrganizers() {
        return organizerRepository.findAll();
    }

    @GetMapping("/events")
    public List<Event> getEventsByOrganizer(@RequestParam("organizerId") int  organizerId) {
        return eventRepository.findByOrganizerId(organizerId);
    }

    @GetMapping("/events/{id}")
    public Event getEventsById(@PathVariable("id") int eventId) {
        return eventRepository
                .findById(eventId)
                .orElseThrow(
                        () -> new NoSuchElementException("Event with id " + eventId + " not found")
                );
    }

    @GetMapping(path = "/products")
    public List<Product> getProductsByEvent(@RequestParam("eventId") int eventId) {
        return productRepository.findByEventId(eventId);
    }

    @GetMapping(path = "/products/{id}")
    public Optional<Product> getProductById(@PathVariable("id") Long productId) {
        return productRepository.findById(productId);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse notFound(NoSuchElementException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }
}
