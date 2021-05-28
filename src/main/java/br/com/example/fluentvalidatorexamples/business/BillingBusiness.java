package br.com.example.fluentvalidatorexamples.business;

import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.example.fluentvalidatorexamples.exception.BillingNotFoundException;
import br.com.example.fluentvalidatorexamples.exception.BillingValidationException;
import br.com.example.fluentvalidatorexamples.repository.BillingRepository;
import br.com.example.fluentvalidatorexamples.validator.BillingValidator;
import br.com.fluentvalidator.context.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
public class BillingBusiness {

  private static final Logger LOGGER = LoggerFactory.getLogger(BillingBusiness.class);

  @Autowired
  private BillingValidator billingValidator;

  @Autowired
  private BillingRepository billingRepository;

  public Billing save(final Billing billing) throws BillingValidationException {
    LOGGER.info("starting billing validations");

    final ValidationResult validationResult = billingValidator.validate(billing);

    if (!validationResult.isValid()) {
      // once the billing parameters does not pass on validation, stop the save process
      LOGGER.error("there were failure(s) on validation process");

      throw new BillingValidationException(validationResult);
    }

    LOGGER.info("billing validated successfully");

    return billingRepository.save(billing);
  }

  public Set<Billing> findAllBillings() {
    return billingRepository.findAll();
  }

  public Billing findBillingById(final UUID id) throws BillingNotFoundException {
    return billingRepository.findById(id);
  }

  public Billing update(final Billing billing) throws BillingValidationException, BillingNotFoundException {
    LOGGER.info("starting billing validations");

    final ValidationResult validationResult = billingValidator.validate(billing);

    if (!validationResult.isValid()) {
      // once the billing parameters does not pass on validation, stop the save process
      LOGGER.error("there were failure(s) on validation process");

      throw new BillingValidationException(validationResult);
    }

    LOGGER.info("billing validated successfully");

    return billingRepository.update(billing);
  }

  public void delete(final UUID id) throws BillingNotFoundException {
    LOGGER.info("deleting billing with id " + id);

    billingRepository.delete(id);

    LOGGER.info("billing deleted successfully");
  }

}
