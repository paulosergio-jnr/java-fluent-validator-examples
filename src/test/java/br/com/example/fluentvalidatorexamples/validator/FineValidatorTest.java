package br.com.example.fluentvalidatorexamples.validator;

import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class FineValidatorTest {

  private final FineValidator validator = new FineValidator();

  private Billing billing;

  @BeforeEach
  void setUp() {
    billing = new Billing();
    billing.setBalance(BigDecimal.TEN);
    billing.setFineAmount(BigDecimal.ONE);
    billing.setApplyFineForPastPayment(true);
  }


  @Test
  void Should_ReturnValidationSuccess_When_BillingIsCompletelyCorrect() {
    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }


  @Test
  void Should_ReturnValidationFailed_When_FineAmountIsLowerThanTheMinimum() {
    billing.setFineAmount(BigDecimal.valueOf(0.001));

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("201"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("fineAmount"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("the minimum value for fine amount is $0.01"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(BigDecimal.valueOf(0.001)))));
  }

  @Test
  void Should_ReturnValidationFailed_When_FineAmountIsZero() {
    billing.setFineAmount(BigDecimal.ZERO);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("201"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("fineAmount"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("the minimum value for fine amount is $0.01"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(BigDecimal.ZERO))));
  }

  @Test
  void Should_ReturnValidationFailed_When_FineAmountIsNegative() {
    billing.setFineAmount(BigDecimal.valueOf(-0.01));

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("201"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("fineAmount"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("the minimum value for fine amount is $0.01"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(BigDecimal.valueOf(-0.01)))));
  }


  @Test
  void Should_ReturnValidationFailed_When_FineAmountIsEqualToBalance() {
    billing.setFineAmount(BigDecimal.TEN);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("202"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("fineAmount"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("the maximum value for fine can not be greater or equal the billing balance"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(BigDecimal.TEN))));
  }

  @Test
  void Should_ReturnValidationFailed_When_FineAmountIsGreaterThanBalance() {
    billing.setBalance(BigDecimal.ONE);
    billing.setFineAmount(BigDecimal.TEN);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("202"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("fineAmount"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("the maximum value for fine can not be greater or equal the billing balance"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(BigDecimal.TEN))));
  }

}
