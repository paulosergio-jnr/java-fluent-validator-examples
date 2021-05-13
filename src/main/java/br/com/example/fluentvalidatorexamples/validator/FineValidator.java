package br.com.example.fluentvalidatorexamples.validator;

import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.fluentvalidator.AbstractValidator;

import java.math.BigDecimal;

import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThanOrEqual;
import static br.com.fluentvalidator.predicate.ComparablePredicate.lessThan;
import static br.com.fluentvalidator.predicate.LogicalPredicate.isTrue;

class FineValidator extends AbstractValidator<Billing> {

  @Override
  public void rules() {
    /**
     * rules for fine appliance:
     * - it must be greater or equal $0.01 if fine is applicable
     * - it must be lesser than the balance if fine is applicable
     */
    ruleFor(billing -> billing)
      /**
       * the minimum value for fine amount is $0.01
       */
      .must(greaterThanOrEqual(Billing::getFineAmount, new BigDecimal("0.01")))
      .when(isTrue(Billing::getApplyFineForPastPayment))
        .withCode("")
        .withFieldName("")
        .withMessage("")
        .withAttempedValue(Billing::getFineAmount);

      /**
       * the maximum value for fine can not be greather or equal the billing balance
       */
//      .must(lessThan(Billing::getFineAmount, Billing::getBalance))
//      .when(isTrue(Billing::getApplyFineForPastPayment))
//        .withCode("")
//        .withFieldName("")
//        .withMessage("")
//        .withAttempedValue(Billing::getFineAmount);

  }

}
