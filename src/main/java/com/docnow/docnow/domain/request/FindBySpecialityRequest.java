package com.docnow.docnow.domain.request;

public class FindBySpecialityRequest {
    private String illness;

    public FindBySpecialityRequest(String illness) {
        this.illness = illness;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }
}
