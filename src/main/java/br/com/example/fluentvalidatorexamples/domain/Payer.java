package br.com.example.fluentvalidatorexamples.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Payer {

  private String firstName;

  private String lastName;

  private LocalDate birthday;

  private String email;

  private Address billingAddress;

}
