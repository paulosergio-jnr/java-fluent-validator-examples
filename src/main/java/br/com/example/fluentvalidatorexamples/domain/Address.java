package br.com.example.fluentvalidatorexamples.domain;

import br.com.example.fluentvalidatorexamples.enums.Country;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

  private String zipcode;

  private String addressLine1;

  private String addressLine2;

  private String city;

  private String state;

  private Country country;

}
