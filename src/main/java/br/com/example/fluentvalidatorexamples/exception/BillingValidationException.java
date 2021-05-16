package br.com.example.fluentvalidatorexamples.exception;

import br.com.fluentvalidator.context.ValidationResult;
import br.com.fluentvalidator.exception.ValidationException;

public class BillingValidationException extends ValidationException {

  private static final long serialVersionUID = -2208785053763548883L;

  public BillingValidationException(ValidationResult validationResult) {
    super(validationResult);
  }

}
