package com.docnow.docnow.appointment.repository;

import com.docnow.docnow.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
              Optional<Appointment> findByDoctorUsername(String username);
}
