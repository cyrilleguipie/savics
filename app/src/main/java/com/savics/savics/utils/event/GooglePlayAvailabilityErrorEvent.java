package com.savics.savics.utils.event;

public class GooglePlayAvailabilityErrorEvent {
    private int resultCode;

    public GooglePlayAvailabilityErrorEvent(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}