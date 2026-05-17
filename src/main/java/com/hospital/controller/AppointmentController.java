package com.hospital.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.service.AppointmentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> book(@RequestBody BookRequest request) {
        Appointment appointment = Appointment.builder()
                .patient(Patient.builder().id(request.getPatientId()).build())
                .doctor(Doctor.builder().id(request.getDoctorId()).build())
                .appointmentDate(request.getAppointmentDate())
                .appointmentTime(request.getAppointmentTime())
                .reason(request.getReason())
                .build();
        return ResponseEntity.ok(appointmentService.bookAppointment(appointment));
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAll() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDoctor(doctorId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(@PathVariable Long id,
                                                     @RequestParam String status) {
        Appointment.AppointmentStatus s = Appointment.AppointmentStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(appointmentService.updateStatus(id, s));
    }

    @PatchMapping("/{id}/notes")
    public ResponseEntity<Appointment> addNotes(@PathVariable Long id,
                                                 @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(appointmentService.addNotes(id, body.get("notes")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok("Appointment cancelled");
    }

    @Data
    public static class BookRequest {
        private Long patientId;
        private Long doctorId;
        private LocalDate appointmentDate;
        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime appointmentTime;
        private String reason;
    }
}
