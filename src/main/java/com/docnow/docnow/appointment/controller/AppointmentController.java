package com.docnow.docnow.appointment.controller;

import com.docnow.docnow.appointment.entity.Appointment;
import com.docnow.docnow.appointment.entity.AppointmentStatus;
import com.docnow.docnow.appointment.repository.AppointmentRepository;
import com.docnow.docnow.appointment.request.AppointmentRequest;
import com.docnow.docnow.appointment.response.AppointmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<?> findAppointment(@RequestBody AppointmentRequest request){
        Optional<Appointment> appointment = appointmentRepository.findByDoctorUsername(request.getUsername());
        if(appointment.isPresent()){
            return ResponseEntity.ok()
                    .body(appointment);
        } else{
            return ResponseEntity.badRequest().body(new AppointmentResponse("No appointment found"));
        }
    }

    @PostMapping("book")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequest request){
        Appointment appointment = new Appointment(request.getDoctorUsername(),request.getPatientUsername(),request.getAppointmentTime());
        appointmentRepository.save(appointment);

        return ResponseEntity.ok().body(new AppointmentResponse("Appointment Booked Successfully"));
    }

    @PutMapping("update")
    @PreAuthorize("hasAuthority('PATIENT') or hasAuthority('DOCTOR')")
    public ResponseEntity<?> updateAppointmentStatus(@RequestBody AppointmentRequest request){
        Appointment appointment = appointmentRepository.findById(request.getId()).orElseThrow(()->new ResourceNotFoundException("No Appointment exists with given ID"));
         String appointmentStatus = request.getStatus().name();
         switch(appointmentStatus){
             case "confirmed":
                 appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
                 break;
             case "pending":
                 appointment.setAppointmentStatus(AppointmentStatus.PENDING);
                 break;
             case "cancelled":
                 appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
                 break;
         }
         appointmentRepository.save(appointment);
         return ResponseEntity.ok().body(new AppointmentResponse(String.format("Appointment updated to %s",request.getStatus())));
    }
}
