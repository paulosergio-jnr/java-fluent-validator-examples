package br.com.example.fluentvalidatorexamples.validator;

import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.fluentvalidator.AbstractValidator;

import java.math.BigDecimal;
import java.util.function.Function;

import static br.com.fluentvalidator.predicate.ComparablePredicate.greaterThanOrEqual;
import static br.com.fluentvalidator.predicate.ComparablePredicate.lessThan;
import static br.com.fluentvalidator.predicate.LogicalPredicate.isTrue;

class FineValidator extends AbstractValidator<Billing> {

  private static final BigDecimal MINIMUM_FINE_AMOUNT = new BigDecimal("0.01");

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
      .must(greaterThanOrEqual(Billing::getFineAmount, MINIMUM_FINE_AMOUNT))
      .when(isTrue(Billing::getApplyFineForPastPayment))
        .withCode("201")
        .withFieldName("fineAmount")
        .withMessage("the minimum value for fine amount is $0.01")
        .withAttempedValue(Billing::getFineAmount)

      /**
       * the maximum value for fine can not be greater or equal the billing balance
       */
      .must(lessThan(Billing::getFineAmount, (Function<Billing, BigDecimal>) Billing::getBalance))
      .when(isTrue(Billing::getApplyFineForPastPayment))
        .withCode("202")
        .withFieldName("fineAmount")
        .withMessage("the maximum value for fine can not be greater or equal the billing balance")
        .withAttempedValue(Billing::getFineAmount);

  }

}
