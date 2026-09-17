package com.parksmart.entity;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.persistence.*;

@Entity @Table(name="garage")
public class Garage {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String name; private String address;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal firstHourRate;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal extraHourRate;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal dailyCap;
    @Column(nullable=false) private Instant createdAt;
    public Garage() {} public Garage(String name,String address,BigDecimal first,BigDecimal extra,BigDecimal cap){this.name=name;this.address=address;firstHourRate=first;extraHourRate=extra;dailyCap=cap;}
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getAddress(){return address;} public void setAddress(String v){address=v;}
    public BigDecimal getFirstHourRate(){return firstHourRate;} public void setFirstHourRate(BigDecimal v){firstHourRate=v;} public BigDecimal getExtraHourRate(){return extraHourRate;} public void setExtraHourRate(BigDecimal v){extraHourRate=v;} public BigDecimal getDailyCap(){return dailyCap;} public void setDailyCap(BigDecimal v){dailyCap=v;} public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant v){createdAt=v;}
}