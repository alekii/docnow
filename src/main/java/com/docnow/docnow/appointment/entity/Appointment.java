package com.docnow.docnow.appointment.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import lombok .*;
import net.bytebuddy.implementation.bind.annotation.Default;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name="appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int Id;
    @NotNull
    @Column(name="doctor_username")
    private String doctorUsername;

    @Column(name="patient_username")
    private String patientUsername;

    @Column(name="date")
    private Date appointmentTime;

    @Column(name="status", columnDefinition="String default PENDING")
    private AppointmentStatus appointmentStatus;

    public Appointment(String doctorUsername,String patientUsername,Date appointmentTime) {
        this.doctorUsername = doctorUsername;
        this.patientUsername = patientUsername;
        this.appointmentTime = appointmentTime;
    }
}
