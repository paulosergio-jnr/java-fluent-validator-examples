package br.com.example.fluentvalidatorexamples.validator;

import br.com.example.fluentvalidatorexamples.domain.Payer;
import br.com.fluentvalidator.AbstractValidator;

import static br.com.example.fluentvalidatorexamples.predicate.CustomPredicates.hasMininumAgeOf;
import static br.com.example.fluentvalidatorexamples.predicate.CustomPredicates.hasValidEmail;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static java.util.function.Predicate.not;

public class PayerValidator extends AbstractValidator<Payer> {

  private static final int MINIMUM_PAYER_AGE = 18;

  @Override
  public void rules() {

    /**
     * rules for payer:
     * - payer's first name must be provided
     * - payer's last name must be provided
     * - payer's birthday must be provided
     * - payer must be older than 18 years old
     * - payer's e-mail must be provided
     * - payer's e-mail must follow the pattern: 'e-mail@provider.extension'
     * - payer's billing address must not be null
     * - payer's billing address validations must be set on a dedicated validator
     */

    ruleFor("payer.firstName", Payer::getFirstName)
      /**
       * payer's first name must not be null or string empty
       */
      .must(not(stringEmptyOrNull()))
        .withCode("301")
        .withMessage("payer's first name not provided");

    ruleFor("payer.lastName", Payer::getLastName)
      /**
       * payer's last name must not be null or string empty
       */
      .must(not(stringEmptyOrNull()))
        .withCode("302")
        .withMessage("payer's last name not provided");

    ruleFor("payer.birthday", Payer::getBirthday)
      /**
       * payer's birthday must not be null
       */
      .must(not(nullValue()))
        .withCode("303")
        .withMessage("payer's birthday not provided")
        .critical()

      /**
       * payer's age must be at least 18
       */
      .must(hasMininumAgeOf(MINIMUM_PAYER_AGE))
        .withCode("304")
        .withMessage("payer's age must be at least 18");

    ruleFor("payer.email", Payer::getEmail)
      /**
       * payer's e-mail must be provided and follow the pattern: 'e-mail@provider.extension'
       * hasValidEmail() predicate also checks for null and blank string
       */
      .must(hasValidEmail())
        .withCode("305")
        .withMessage("payer's e-mail must be provided and follow the pattern: 'e-mail@provider.extension'");

    ruleFor("payer.billingAddress", Payer::getBillingAddress)
      /**
       * payer's billing address must not be null
       */
      .must(not(nullValue()))
        .withCode("306")
        .withMessage("payer's billing address not provided")
        .critical()

      /**
       * payer's billing address validations must be set on a dedicated validator
       */
      .whenever(not(nullValue()))
      .withValidator(new AddressValidator("payer."));

  }

}
