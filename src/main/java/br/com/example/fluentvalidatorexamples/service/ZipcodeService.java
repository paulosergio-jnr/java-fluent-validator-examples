package br.com.example.fluentvalidatorexamples.service;

import org.springframework.stereotype.Service;

@Service
public class ZipcodeService {

  public Boolean isZipcodeValid(final String zipcode) {
    // This is a mocked service to validate zipcode
    return true;
  }

}
