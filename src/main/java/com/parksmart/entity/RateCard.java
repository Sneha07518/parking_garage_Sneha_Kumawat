package com.parksmart.entity;

import java.math.BigDecimal; import java.time.Instant; import jakarta.persistence.*;

@Entity @Table(name="rate_card", uniqueConstraints=@UniqueConstraint(columnNames={"garage_id","spot_type"}))
public class RateCard {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) private Garage garage;
    @Enumerated(EnumType.STRING) @Column(name="spot_type",nullable=false) private SpotType spotType;
    @Column(nullable=false,precision=12,scale=2) private BigDecimal firstHourRate;
    @Column(nullable=false,precision=12,scale=2) private BigDecimal extraHourRate;
    @Column(nullable=false,precision=12,scale=2) private BigDecimal dailyCap;
    @Column(nullable=false) private Instant updatedAt;
    public RateCard() {}
    public RateCard(Garage garage,SpotType spotType,BigDecimal first,BigDecimal extra,BigDecimal cap,Instant updated){this.garage=garage;this.spotType=spotType;firstHourRate=first;extraHourRate=extra;dailyCap=cap;updatedAt=updated;}
    public Long getId(){return id;} public Garage getGarage(){return garage;} public void setGarage(Garage v){garage=v;} public SpotType getSpotType(){return spotType;} public void setSpotType(SpotType v){spotType=v;} public BigDecimal getFirstHourRate(){return firstHourRate;} public void setFirstHourRate(BigDecimal v){firstHourRate=v;} public BigDecimal getExtraHourRate(){return extraHourRate;} public void setExtraHourRate(BigDecimal v){extraHourRate=v;} public BigDecimal getDailyCap(){return dailyCap;} public void setDailyCap(BigDecimal v){dailyCap=v;} public Instant getUpdatedAt(){return updatedAt;} public void setUpdatedAt(Instant v){updatedAt=v;}
}