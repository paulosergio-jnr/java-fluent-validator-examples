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
    ruleFor(additionalInfo -> additionalInfo)
      /**
       * an item can not be null or string empty
       */
      .must(not(stringEmptyOrNull()))
        .withCode("601")
        .withFieldName(fn -> "additionalInfo[" + getCounter() + "]")
        .withMessage("additional info cannot be empty or null");

  }

}
