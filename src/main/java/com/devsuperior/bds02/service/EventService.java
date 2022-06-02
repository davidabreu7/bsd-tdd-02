package com.devsuperior.bds02.service;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.exceptions.ResourceNotFoundException;
import com.devsuperior.bds02.repository.CityRepository;
import com.devsuperior.bds02.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    final EventRepository eventRepository;
    final CityRepository cityRepository;

    public EventService(EventRepository repository, CityRepository cityRepository) {
        this.eventRepository = repository;
        this.cityRepository = cityRepository;
    }

    public EventDTO update(Long id, EventDTO dto) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource id: %d not found".formatted(id)));
        event.setDate(dto.getDate());
        event.setCity(cityRepository.getOne(dto.getCityId()));
        event.setName(dto.getName());
        event.setUrl(dto.getUrl());
        return new EventDTO(event);

    }
}
