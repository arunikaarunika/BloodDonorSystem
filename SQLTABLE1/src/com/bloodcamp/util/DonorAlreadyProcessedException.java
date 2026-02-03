package com.bloodcamp.util;

public class DonorAlreadyProcessedException extends Exception {
    public DonorAlreadyProcessedException() {
        super();
    }
    public DonorAlreadyProcessedException(String message) {
        super(message);
    }
}
