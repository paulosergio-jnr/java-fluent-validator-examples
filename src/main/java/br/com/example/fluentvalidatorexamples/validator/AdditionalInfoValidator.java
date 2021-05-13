package br.com.example.fluentvalidatorexamples.validator;

import br.com.fluentvalidator.AbstractValidator;

public class AdditionalInfoValidator extends AbstractValidator<String> {

  @Override
  public void rules() {

    /**
     * rules for additionalInfo:
     * - it can be null
     * - if additional info is provided, the list must not be empty
     * - an item can not be null or string empty
     */

  }

}
