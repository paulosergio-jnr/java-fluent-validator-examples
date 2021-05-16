package br.com.example.fluentvalidatorexamples.controller;

import br.com.example.fluentvalidatorexamples.business.BillingBusiness;
import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.example.fluentvalidatorexamples.exception.BillingNotFoundException;
import br.com.example.fluentvalidatorexamples.exception.BillingValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
public class BillingController {

  private static final Logger LOGGER = LoggerFactory.getLogger(BillingController.class);

  @Autowired
  private BillingBusiness billingBusiness;

  @PostMapping("/billing")
  public ResponseEntity<Object> create(@RequestBody final Billing billing) {
    try {
      LOGGER.info("creating billing with data: " + billing.toString());

      final Billing savedBilling = billingBusiness.save(billing);

      LOGGER.info("billing created successfully with data: " + savedBilling.toString());

      return ResponseEntity.ok(savedBilling);

    } catch (final BillingValidationException e) {
      LOGGER.error("billing could not be create due to validation problems");

      return ResponseEntity
        .unprocessableEntity()
        .body(e.getValidationResult().getErrors());
    }
  }

  @GetMapping("/billing")
  public ResponseEntity<Object> findAllBillings() {
    LOGGER.info("searching for all billings");

    final Set<Billing> billings = billingBusiness.findAllBillings();

    LOGGER.info("list of billing fetched with size: " + billings.size());

    return ResponseEntity.ok(billings);
  }

  @GetMapping("/billing/{billingId}")
  public ResponseEntity<Object> findBilling(@PathVariable final String billingId) {
    try {
      LOGGER.info("searching for billing with id '" + billingId + "'");

      final Billing billing = billingBusiness.findBillingById(UUID.fromString(billingId));

      LOGGER.info("billing found");

      return ResponseEntity.ok(billing);

    } catch (final BillingNotFoundException e) {
      LOGGER.error("billing with id '" + billingId + "' not found");

      return ResponseEntity
        .notFound()
        .build();
    }

  }

  @PutMapping("/billing/{billingId}")
  public ResponseEntity<Object> update(@PathVariable final String billingId, @RequestBody final Billing billing) {
    try {
      LOGGER.info("updating billing with id '" + billingId + "'");

      billing.setId(UUID.fromString(billingId));
      final Billing savedBilling = billingBusiness.update(billing);

      LOGGER.info("billing updated successfully with data: " + savedBilling.toString());

      return ResponseEntity.ok(savedBilling);

    } catch (final BillingValidationException e) {
      LOGGER.error("billing could not be create due to validation problems");

      return ResponseEntity
        .unprocessableEntity()
        .body(e.getValidationResult().getErrors());

    } catch (final BillingNotFoundException e) {
      LOGGER.error("billing with id '" + billingId + "' not found");

      return ResponseEntity
        .notFound()
        .build();
    }
  }

  @DeleteMapping("/billing/{billingId}")
  public ResponseEntity<Object> delete(@PathVariable final String billingId) {
    try {
      LOGGER.info("deleting billing with id '" + billingId + "'");

      billingBusiness.delete(UUID.fromString(billingId));

      LOGGER.info("billing found");

      return ResponseEntity.ok().build();

    } catch (final BillingNotFoundException e) {
      LOGGER.error("billing with id '" + billingId + "' not found");

      return ResponseEntity
        .notFound()
        .build();
    }

  }

}
