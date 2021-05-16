package br.com.example.fluentvalidatorexamples.validator;

import br.com.fluentvalidator.AbstractValidator;

import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static java.util.function.Predicate.not;

public class AdditionalInfoValidator extends AbstractValidator<String> {

  @Override
  public void rules() {

    /**
     * rules for additionalInfo:
     * - an item can not be null or string empty
     */
    ruleFor("additionalInfo", additionalInfo -> additionalInfo)
      .must(not(stringEmptyOrNull()))
        .withCode("601")
        .withMessage("additional info cannot be empty or null");

  }

}
