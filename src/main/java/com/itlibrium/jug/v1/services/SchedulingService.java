package com.itlibrium.jug.v1.services;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SchedulingService {

    public void scheduleMeeting(Instant start, Instant end, String meetingName) {
        if(start.isAfter(end)) {
            throw new IllegalArgumentException("Start after end!");
        }
    }
}
