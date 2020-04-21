package com.cycsystems.heymebackend.models.service;

public interface ICaptchaService {

    public abstract boolean verify(String response);
}
