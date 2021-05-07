package br.com.example.fluentvalidatorexamples.controller;

import br.com.example.fluentvalidatorexamples.domain.Billing;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingController {

  @PostMapping("/billing")
  public ResponseEntity<Object> save(@RequestBody final Billing billing) {

    return ResponseEntity.ok("");
  }

}
