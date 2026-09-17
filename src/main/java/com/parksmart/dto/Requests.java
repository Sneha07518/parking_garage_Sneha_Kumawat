package com.parksmart.dto;
import java.math.BigDecimal; import java.util.List; import com.parksmart.entity.*; import jakarta.validation.constraints.*;
public class Requests {
    public static class GarageRequest { @NotBlank public String name; public String address; @NotNull @PositiveOrZero public BigDecimal firstHourRate; @NotNull @PositiveOrZero public BigDecimal extraHourRate; @NotNull @Positive public BigDecimal dailyCap; }
    public static class CheckInRequest { @NotBlank public String plate; @NotNull public VehicleType vehicleType; public Long spotId; }
    public static class SpotItem { @Min(1) public int level; @NotBlank public String code; @NotNull public SpotType type; }
    public static class SpotsRequest { @NotEmpty public List<SpotItem> spots; }
}