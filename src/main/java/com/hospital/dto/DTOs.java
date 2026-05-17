package com.hospital.dto;

import com.hospital.model.Appointment;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

// ──────────────────────────────────────────────────────────
// Auth DTOs
// ──────────────────────────────────────────────────────────

@Data @NoArgsConstructor @AllArgsConstructor
class RegisterRequest {
    @NotBlank private String username;
    @NotBlank private String password;
    @Email @NotBlank private String email;
    @NotBlank private String role; // ROLE_ADMIN / ROLE_DOCTOR / ROLE_PATIENT
}

@Data @NoArgsConstructor @AllArgsConstructor
class LoginRequest {
    @NotBlank private String username;
    @NotBlank private String password;
}

@Data @NoArgsConstructor @AllArgsConstructor
class AuthResponse {
    private String token;
    private String username;
    private String role;
}

// ──────────────────────────────────────────────────────────
// Patient DTOs
// ──────────────────────────────────────────────────────────

@Data @NoArgsConstructor @AllArgsConstructor @Builder
class PatientRequest {
    @NotBlank(message = "Name is required")     private String name;
    @Email @NotBlank                             private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String address;
    private String bloodGroup;
}

@Data @NoArgsConstructor @AllArgsConstructor @Builder
class PatientResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String bloodGroup;
}

// ──────────────────────────────────────────────────────────
// Doctor DTOs
// ──────────────────────────────────────────────────────────

@Data @NoArgsConstructor @AllArgsConstructor @Builder
class DoctorRequest {
    @NotBlank private String name;
    @NotBlank private String specialization;
    @Email @NotBlank private String email;
    private String phone;
    private Integer experience;
}

@Data @NoArgsConstructor @AllArgsConstructor @Builder
class DoctorResponse {
    private Long id;
    private String name;
    private String specialization;
    private String email;
    private String phone;
    private Integer experience;
    private Boolean available;
}

// ──────────────────────────────────────────────────────────
// Appointment DTOs
// ──────────────────────────────────────────────────────────

@Data @NoArgsConstructor @AllArgsConstructor @Builder
class AppointmentRequest {
    @NotNull private Long patientId;
    @NotNull private Long doctorId;
    @NotNull private LocalDate appointmentDate;
    @NotNull private LocalTime appointmentTime;
    private String reason;
}

@Data @NoArgsConstructor @AllArgsConstructor @Builder
class AppointmentResponse {
    private Long id;
    private String patientName;
    private String doctorName;
    private String doctorSpecialization;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private Appointment.AppointmentStatus status;
    private String reason;
    private String notes;
}

// ──────────────────────────────────────────────────────────
// Generic API Response wrapper
// ──────────────────────────────────────────────────────────

@Data @NoArgsConstructor @AllArgsConstructor @Builder
class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder().success(true).message(message).data(data).build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder().success(false).message(message).build();
    }
}
