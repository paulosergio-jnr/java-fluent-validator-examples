package br.com.example.fluentvalidatorexamples.validator;

import br.com.example.fluentvalidatorexamples.domain.Receiver;
import br.com.fluentvalidator.AbstractValidator;

import static br.com.example.fluentvalidatorexamples.predicate.CustomPredicates.hasMininumAgeOf;
import static br.com.example.fluentvalidatorexamples.predicate.CustomPredicates.hasValidEmail;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static java.util.function.Predicate.not;

public class ReceiverValidator extends AbstractValidator<Receiver> {

  private static final int MINIMUM_PAYER_AGE = 21;

  @Override
  public void rules() {

    /**
     * rules for receiver:
     * - receiver's first name must be provided
     * - receiver's last name must be provided
     * - receiver's birthday must be provided
     * - receiver must be older than 21 years old
     * - receiver's e-mail must be provided
     * - receiver's e-mail must follow the pattern: 'e-mail@provider.extension'
     * - receiver's billing address must not be null
     * - receiver's billing address validations must be set on a dedicated validator
     */

    ruleFor("receiver.firstName", Receiver::getFirstName)
      /**
       * receiver's first name must not be null or string empty
       */
      .must(not(stringEmptyOrNull()))
        .withCode("501")
        .withMessage("receiver's first name not provided");

    ruleFor("receiver.lastName", Receiver::getLastName)
      /**
       * receiver's last name must not be null or string empty
       */
      .must(not(stringEmptyOrNull()))
        .withCode("502")
        .withMessage("receiver's last name not provided");

    ruleFor("receiver.birthday", Receiver::getBirthday)
      /**
       * receiver's birthday must not be null
       */
      .must(not(nullValue()))
        .withCode("503")
        .withMessage("receiver's birthday not provided")
        .critical()

      /**
       * receiver's age must be at least 21
       */
      .must(hasMininumAgeOf(MINIMUM_PAYER_AGE))
        .withCode("504")
        .withMessage("receiver's age must be at least 21");

    ruleFor("receiver.email", Receiver::getEmail)
      /**
       * receiver's e-mail must be provided and follow the pattern: 'e-mail@provider.extension'
       * hasValidEmail() predicate also checks for null and blank string
       */
      .must(hasValidEmail())
        .withCode("505")
        .withMessage("receiver's e-mail must be provided and follow the pattern: 'e-mail@provider.extension'");

    ruleFor("receiver.billingAddress", Receiver::getAddress)
      /**
       * receiver's billing address must not be null
       */
      .must(not(nullValue()))
        .withCode("506")
        .withMessage("receiver's billing address not provided")
        .critical()

      /**
       * receiver's billing address validations must be set on a dedicated validator
       */
      .whenever(not(nullValue()))
      .withValidator(new AddressValidator("receiver."));

  }

}
