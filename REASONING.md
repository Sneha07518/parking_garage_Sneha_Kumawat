# ParkSmart reasoning

## Fee model

Minutes round up to hours, minimum one hour. Complete 24-hour blocks cost `dailyCap`; a remainder costs `min(dailyCap, firstHourRate + (remainingHours - 1) * extraHourRate)`. Thus 61 minutes is two hours, 24 hours is one cap, 25 hours is one cap plus one hour.

`FeeCalculator` is pure and tested for 1, 59, 60, 61, 5, cap-hit, 24, 25, and 50 hours.

## Design decisions

Garage rates and spots are data, not constants. EV vehicles use only EV spots; standard vehicles use standard spots; compact vehicles prefer compact and fall back to standard. Plates are uppercase with spaces and dashes removed.

Active sessions store nullable unique `active_spot_id` and `active_plate_key` (`garageId:plate`). They are set on check-in and cleared on checkout. The transactional service checks first for clear errors, while database uniqueness and `DataIntegrityViolationException` protect concurrent requests.

## Testing and fixes

`mvn test` runs the fee suite and a Spring context smoke test against separate SQLite. Manual checks use the seeded login, protected garage request, availability, check-in, quote, and checkout curl calls. Boundary tests caught incorrect expectations for 24 and 50 hours; expected values were corrected to match the formula.

## Twist decisions

**T4 rate cards:** `RateCardParser` treats the first non-comment row as a case-insensitive, trimmed header, accepts the documented aliases, normalizes messy vehicle-type names and money tokens, skips repeated headers, rejects invalid business values with line/raw/reason, and lets the last valid duplicate win. Checkout resolves the card using the actual spot type and falls back to garage defaults.

**T2 clock and automation:** `ClockService` wraps `java.time.Clock`; all application-controlled timestamps use it, with system UTC as the default and a fixed clock for simulation. Auto-close selects only active sessions older than 24 hours, clears active uniqueness fields, records `AUTO`, and is idempotent because a closed row no longer matches the query. The root clock endpoint runs the same service used by the midnight scheduler.

**T6 transfers:** A hand-off changes the plate and active key on the existing session row, preserving spot, vehicle type, and original entry time. It rejects equal plates, missing source sessions, and active destinations, and records an audit row with attendant, reason, and the controlled transfer time. Checkout by the new plate therefore charges from the original arrival.

Tests cover parser cleaning/rejection/override behavior, clock set/advance/reset, existing fee boundaries, application startup, and the transactional workflow slices. A notable bug fixed during the extension was null JSON serialization for active sessions; response maps now deliberately preserve `checkedOutAt: null` and expose `closedBy`.