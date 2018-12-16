package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.model.repository.Repository;
import ru.itmo.webmail.model.repository.impl.RepositoryImpl;

public class EventService {

    private Repository<Event> eventRepository = new RepositoryImpl<>(Event.class);

    public void publishLogEvent(long userId, Event.EventType type) {
        Event event = new Event();
        event.setUserId(userId);
        event.setType(type);
        eventRepository.save(event);
    }

}
