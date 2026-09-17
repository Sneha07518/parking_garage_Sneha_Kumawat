package com.parksmart.service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;

@Service
public class ClockService {
    private volatile Clock clock = Clock.systemUTC();
    private volatile boolean simulated;

    public Instant now() { return clock.instant(); }
    public synchronized Instant setNow(Instant now) { clock = Clock.fixed(now, ZoneOffset.UTC); simulated = true; return now(); }
    public synchronized Instant advance(Duration duration) { return setNow(now().plus(duration)); }
    public synchronized Instant reset() { clock = Clock.systemUTC(); simulated = false; return now(); }
    public boolean isSimulated() { return simulated; }
}