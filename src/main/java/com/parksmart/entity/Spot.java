package com.parksmart.entity;

import jakarta.persistence.*;

@Entity @Table(name="spot", uniqueConstraints=@UniqueConstraint(columnNames={"garage_id","code"}))
public class Spot {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false, fetch=FetchType.LAZY) private Garage garage;
    @Column(nullable=false) private int level; @Column(nullable=false) private String code;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private SpotType type;
    public Spot() {} public Spot(Garage garage,int level,String code,SpotType type){this.garage=garage;this.level=level;this.code=code;this.type=type;}
    public Long getId(){return id;} public Garage getGarage(){return garage;} public void setGarage(Garage v){garage=v;} public int getLevel(){return level;} public void setLevel(int v){level=v;} public String getCode(){return code;} public void setCode(String v){code=v;} public SpotType getType(){return type;} public void setType(SpotType v){type=v;}
}