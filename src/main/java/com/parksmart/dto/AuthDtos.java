package com.parksmart.dto;
import jakarta.validation.constraints.*;
public class AuthDtos { public static class Register { @NotBlank public String name; @Email @NotBlank public String email; @Size(min=6) public String password; } public static class Login { @Email @NotBlank public String email; @NotBlank public String password; } }