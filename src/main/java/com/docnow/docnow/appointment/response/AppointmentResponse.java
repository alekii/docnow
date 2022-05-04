package com.docnow.docnow.appointment.response;

public class AppointmentResponse {
    private String message;

    public AppointmentResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
