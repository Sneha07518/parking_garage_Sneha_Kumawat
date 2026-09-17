# ParkSmart

Configurable parking garage attendant system built with Spring Boot 3.5, SQLite, and plain HTML/CSS/JavaScript.

## Run in Codespaces

Requires JDK 21+ (JDK 25 works) and Maven 3.9+.

```bash
mvn test
mvn spring-boot:run
```

Open `http://localhost:8080`. The first run seeds City Centre Garage and `admin@parksmart.com` / `admin123`. Reset with `rm -f parksmart.db`.

## API

Except `/api/auth/**` and static pages, endpoints require `Authorization: Bearer <token>`.

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/register` | Create account |
| POST | `/api/auth/login` | Return JWT |
| GET | `/api/auth/me` | Current user |
| GET/POST | `/api/garages` | List/create garages |
| GET/PUT | `/api/garages/{id}` | Read/update rates |
| GET/POST | `/api/garages/{id}/spots` | List/bulk-create spots |
| GET | `/api/garages/{id}/availability?type=EV` | Free counts and spots |
| POST | `/api/garages/{id}/checkin` | Start session |
| POST | `/api/garages/{id}/checkout` | Charge and close session |
| GET | `/api/garages/{id}/quote?plate=ABC123` | Current fee quote |
| GET | `/api/garages/{id}/sessions/active/{plate}` | Active vehicle |
| GET | `/api/garages/{id}/sessions` | Filtered pageable log |
| GET | `/api/garages/{id}/rates` | Cleaned rate cards |
| POST | `/api/garages/{id}/rates/import` | Import CSV or JSON rate cards |
| POST/GET | `/clock` | Set/advance or read simulated time |
| POST | `/clock/reset` | Return to system time |
| POST | `/api/garages/{id}/sessions/transfer` | Transfer an active valet session |
| GET | `/api/garages/{id}/sessions/{sessionId}/transfers` | Transfer audit history |

Example:

```bash
TOKEN=$(curl -s -X POST localhost:8080/api/auth/login -H 'Content-Type: application/json' -d '{"email":"admin@parksmart.com","password":"admin123"}' | jq -r .token)
curl -H "Authorization: Bearer $TOKEN" localhost:8080/api/garages
curl -X POST localhost:8080/api/garages/1/checkin -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' -d '{"plate":"ab-123","vehicleType":"COMPACT"}'
curl -X POST localhost:8080/api/garages/1/rates/import -H "Authorization: Bearer $TOKEN" -H 'Content-Type: text/plain' --data-binary $'type,first,extra,cap\nEV,100,40,500\ncompact,$50,$20,$300'
curl -X POST localhost:8080/clock -H 'Content-Type: application/json' -d '{"advanceHours":25}'
curl -X POST localhost:8080/api/garages/1/sessions/transfer -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' -d '{"fromPlate":"AB123","toPlate":"ZX999","reason":"Valet hand-off"}'
```

## MySQL and debugging

Uncomment the MySQL properties in `application.properties`, add the MySQL JDBC driver, and set credentials. Tests use `parksmart-test.db`, a separate SQLite file. Check the Spring Boot console and run `mvn test` when debugging.
