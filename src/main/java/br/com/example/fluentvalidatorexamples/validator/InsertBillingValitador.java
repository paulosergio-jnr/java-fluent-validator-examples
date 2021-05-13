package br.com.example.fluentvalidatorexamples.validator;

import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.fluentvalidator.AbstractValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

import static br.com.fluentvalidator.predicate.CollectionPredicate.empty;
import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThanOrEqual;
import static br.com.fluentvalidator.predicate.ComparablePredicate.lessThanOrEqual;
import static br.com.fluentvalidator.predicate.LocalDatePredicate.*;
import static br.com.fluentvalidator.predicate.LogicalPredicate.*;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;


@Component
public class InsertBillingValitador extends AbstractValidator<Billing> {

  private static final BigDecimal MAX_BALANCE = new BigDecimal("999999.99");
  private static final BigDecimal MIN_BALANCE = new BigDecimal("1.00");

  @Override
  public void rules() {

    /**
     * rules for balance field:
     * - it must not be null
     * - it must be greater or equal $1
     * - it must be lesser or equal $999,999.99
     */
    ruleFor(Billing::getBalance)
      /**
       * this field is mandatory, and must not be null
       */
      .must(not(nullValue()))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getBalance)

      /**
       * the minimum value for a billing is $1
       * this conditional will be validated if the field is not null
       */
      .must(greaterThanOrEqual(MIN_BALANCE))
      .when(not(nullValue()))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getBalance)

      /**
       * the maximum value for a billing is $999,999.99
       * this conditional will be validated if the field is not null
       */
      .must(lessThanOrEqual(MAX_BALANCE))
      .when(not(nullValue()))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getBalance);

    /**
     * rules for due date:
     * - it must not be null
     * - it can not be a past date
     * - it can not be more than 1 year further
     */
    ruleFor(Billing::getDueDate)
      /**
       * this field is mandatory, and must not be null
       * if this fails, it might not continue with the validation for this field
       * for that, we will simplify using critical() parameter
       */
      .must(not(nullValue()))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getDueDate)
      .critical()

      /**
       * due date must be equal of after today
       */
      .must(localDateAfterOrEqualToday())
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getDueDate)

      /**
       * due date must not the set to more than one year further the current date
       */
      .must(localDateBeforeOrEqual(LocalDate.now().plusYears(1)))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getDueDate);

    /**
     * rules for past payments:
     * - if past payment is acceptable, expiration date must be defined
     * - if past payment is not acceptable, expiration date must be null
     * - expiration date must not be before due date
     * - expiration date has a limit of 6 months further due date
     */
    ruleFor(billing -> billing)
      /**
       * this field is mandatory, and must not be null
       */
      .must(not(nullValue(Billing::getAcceptPastPayment)))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getAcceptPastPayment)
      .critical()

      /**
       * expiration date must be provided whenever past payment is accepted
       */
      .must(not(nullValue(Billing::getExpirationDate)))
      .when(isTrue(Billing::getAcceptPastPayment))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getExpirationDate)
      .critical()

      /**
       * expiration date must not be provided whenever past payment is not accepted
       */
      .must(nullValue(Billing::getExpirationDate))
      .when(isFalse(Billing::getAcceptPastPayment))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getExpirationDate)
      .critical()

      /**
       * expiration date must not be provided whenever past payment is not accepted
       */
      .must(nullValue(Billing::getExpirationDate))
      .when(isFalse(Billing::getAcceptPastPayment))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getExpirationDate)
      .critical()

      /**
       * expiration date must be after due date
       * whenever past payment is accepted
       */
      .must(localDateAfter(Billing::getExpirationDate, Billing::getDueDate))
      .when(isTrue(Billing::getAcceptPastPayment))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getExpirationDate)

      /**
       * expiration date must be set to more than 6 months past due date
       * whenever past payment is accepted
       */
      .must(localDateBeforeOrEqual(Billing::getExpirationDate, plusMonths(Billing::getDueDate, 6)))
      .when(isTrue(Billing::getAcceptPastPayment))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getExpirationDate);

    /**
     * whenever past payment is accepted, fine rules can be applied
     * for this purpose, a exclusive validator for fine rules is created
     */
    ruleFor(billing -> billing)
      .whenever(isTrue(Billing::getAcceptPastPayment))
      .withValidator(new FineValidator());

    /**
     * rules for payer:
     * - it must not be null
     * - use a exclusive validator with only payer rules
     */
    ruleFor(Billing::getPayer)
      /**
       * payer must not be null
       */
      .must(not(nullValue()))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getPayer)

      /**
       * validate payer with proper rules
       */
      .whenever(not(nullValue()))
      .withValidator(new PayerValidator());

    /**
     * rules for receiver:
     * - it must not be null
     * - use a exclusive validator with only payer rules
     */
    ruleFor(Billing::getReceiver)
      /**
       * receiver must not be null
       */
      .must(not(nullValue()))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getPayer)

      /**
       * validate receiver with proper rules
       */
      .whenever(not(nullValue()))
      .withValidator(new ReceiverValidator());

    /**
     * rules for additionalInfo:
     * - it can be null
     * - if additional info is provided, the list must not be empty
     * - for each item in the list, use and exclusive validator
     */
    ruleFor(Billing::getAdditionalInfo)
      /**
       * if additional info list is provided, it must not be empty
       */
      .must(not(empty()))
      .when(not(nullValue()))
      .withCode("")
      .withFieldName("")
      .withMessage("")
      .withAttempedValue(Billing::getAdditionalInfo);

    /**
     * for each item on additional info list, apply a proper validator
     */
    ruleForEach(Billing::getAdditionalInfo)
      .whenever(not(nullValue()))
      .withValidator(new AdditionalInfoValidator());

  }

  /**
   * Function create to dynamically add months to a field
   *
   * @param localDateFunction
   * @return
   */
  private Function<Billing, LocalDate> plusMonths(final Function<Billing, LocalDate> localDateFunction, final int months) {
    return billing -> localDateFunction.apply(billing).plusMonths(months);
  }

}
