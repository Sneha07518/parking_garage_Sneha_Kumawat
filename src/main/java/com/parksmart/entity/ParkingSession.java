package com.parksmart.entity;

import java.math.BigDecimal; import java.time.Instant;
import jakarta.persistence.*;

@Entity @Table(name="parking_session", indexes={@Index(name="idx_session_plate",columnList="plate"),@Index(name="idx_session_checked_in",columnList="checked_in_at")}, uniqueConstraints={@UniqueConstraint(columnNames="active_spot_id"),@UniqueConstraint(columnNames="active_plate_key")})
public class ParkingSession {
    public enum ClosedBy { ATTENDANT, AUTO }
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) private Garage garage;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) private Spot spot;
    @Column(nullable=false) private String plate; @Enumerated(EnumType.STRING) @Column(nullable=false) private VehicleType vehicleType;
    @Column(nullable=false) private Instant checkedInAt; private Instant checkedOutAt; private Long hoursBilled; @Column(precision=12,scale=2) private BigDecimal fee;
    @Enumerated(EnumType.STRING) private ClosedBy closedBy;
    @ManyToOne(fetch=FetchType.LAZY) private User attendant;
    @Column(name="active_spot_id", unique=true) private Long activeSpotId; @Column(name="active_plate_key", unique=true) private String activePlateKey;
    public ParkingSession() {}
    public Long getId(){return id;} public Garage getGarage(){return garage;} public void setGarage(Garage v){garage=v;} public Spot getSpot(){return spot;} public void setSpot(Spot v){spot=v;} public String getPlate(){return plate;} public void setPlate(String v){plate=v;} public VehicleType getVehicleType(){return vehicleType;} public void setVehicleType(VehicleType v){vehicleType=v;} public Instant getCheckedInAt(){return checkedInAt;} public void setCheckedInAt(Instant v){checkedInAt=v;} public Instant getCheckedOutAt(){return checkedOutAt;} public void setCheckedOutAt(Instant v){checkedOutAt=v;} public Long getHoursBilled(){return hoursBilled;} public void setHoursBilled(Long v){hoursBilled=v;} public BigDecimal getFee(){return fee;} public void setFee(BigDecimal v){fee=v;} public ClosedBy getClosedBy(){return closedBy;} public void setClosedBy(ClosedBy v){closedBy=v;} public User getAttendant(){return attendant;} public void setAttendant(User v){attendant=v;} public Long getActiveSpotId(){return activeSpotId;} public void setActiveSpotId(Long v){activeSpotId=v;} public String getActivePlateKey(){return activePlateKey;} public void setActivePlateKey(String v){activePlateKey=v;}
}