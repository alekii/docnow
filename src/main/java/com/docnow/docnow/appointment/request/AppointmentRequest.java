package com.docnow.docnow.appointment.request;
import com.docnow.docnow.appointment.entity.AppointmentStatus;
import lombok.*;

import java.util.Date;

@Getter
@Setter
public class AppointmentRequest {
    private int id;
    private String username;
    private String doctorUsername;
    private String patientUsername;
    private Date appointmentTime;
    private AppointmentStatus status;

    public AppointmentRequest(String username) {
        this.username = username;
    }

    public AppointmentRequest(int id, AppointmentStatus status){
        this.id = id;
        this.status = status;
    }

    public AppointmentRequest(String doctorUsername,String patientUsername,Date appointmentTime) {
        this.doctorUsername = doctorUsername;
        this.patientUsername = patientUsername;
        this.appointmentTime = appointmentTime;
    }
}
