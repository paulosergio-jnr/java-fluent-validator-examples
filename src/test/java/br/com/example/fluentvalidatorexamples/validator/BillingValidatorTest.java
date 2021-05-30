package br.com.example.fluentvalidatorexamples.validator;

import br.com.example.fluentvalidatorexamples.domain.Address;
import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.example.fluentvalidatorexamples.domain.Payer;
import br.com.example.fluentvalidatorexamples.domain.Receiver;
import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class BillingValidatorTest {

  private final BillingValidator validator = new BillingValidator();

  private Billing billing;

  @BeforeEach
  void setUp() {
    billing = new Billing();

    final Address address = new Address();
    address.setAddressLine1("address");
    address.setCity("city");
    address.setState("state");
    address.setCountry("us");
    address.setZipcode("1234567890");

    final Payer payer = new Payer();
    payer.setBillingAddress(address);
    payer.setEmail("test@test.com");
    payer.setBirthday(LocalDate.of(1990, Month.OCTOBER, 5));
    payer.setFirstName("Paulo");
    payer.setLastName("Junior");

    final Receiver receiver = new Receiver();
    receiver.setAddress(address);
    receiver.setEmail("test@test.com");
    receiver.setBirthday(LocalDate.of(1990, Month.OCTOBER, 5));
    receiver.setFirstName("Paulo");
    receiver.setLastName("Junior");

    final ArrayList<String> additionalInfo = new ArrayList<>();
    additionalInfo.add("Info 01");
    additionalInfo.add("Info 02");

    billing.setPayer(payer);
    billing.setReceiver(receiver);
    billing.setBalance(BigDecimal.TEN);
    billing.setDueDate(LocalDate.now().plusDays(10));
    billing.setAcceptPastPayment(true);
    billing.setExpirationDate(LocalDate.now().plusDays(11));
    billing.setApplyFineForPastPayment(true);
    billing.setFineAmount(BigDecimal.ONE);
    billing.setAdditionalInfo(additionalInfo);
  }

  @Test
  void Should_ReturnValidationSuccess_When_BillingIsCompletelyCorrect() {
    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }


  @Test
  void Should_ReturnValidationFailed_When_BalanceIsNull() {
    billing.setBalance(null);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("101"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("balance"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("balance must not be null"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

  @Test
  void Should_ReturnValidationFailed_When_BalanceIsZero() {
    billing.setBalance(BigDecimal.ZERO);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("102"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("balance"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("the minimum value for a billing is $1"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(BigDecimal.ZERO))));
  }

  @Test
  void Should_ReturnValidationFailed_When_BalanceIsNegative() {
    billing.setBalance(BigDecimal.valueOf(-1));

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("102"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("balance"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("the minimum value for a billing is $1"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(BigDecimal.valueOf(-1)))));
  }

  @Test
  void Should_ReturnValidationFailed_When_BalanceIsGreaterThanAllowed() {
    billing.setBalance(BigDecimal.valueOf(1_000_000));

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("103"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("balance"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("the maximum value for a billing is $999,999.99"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(BigDecimal.valueOf(1_000_000)))));
  }

  @Test
  void Should_ReturnValidationSuccess_When_BalanceIsEqualToMaximumAllowed() {
    billing.setBalance(BigDecimal.valueOf(999999.99));

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }


  @Test
  void Should_ReturnValidationFailed_When_DueDateIsNull() {
    billing.setDueDate(null);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("104"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("dueDate"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("due date not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

  @Test
  void Should_ReturnValidationFailed_When_DueDateIsPastDate() {
    billing.setDueDate(LocalDate.now().minusDays(1));

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("105"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("dueDate"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("due date must be equal of after today"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(billing.getDueDate()))));
  }

  @Test
  void Should_ReturnValidationSuccess_When_DueDateIsToday() {
    billing.setDueDate(LocalDate.now());

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }

  @Test
  void Should_ReturnValidationFailed_When_DueDateIsAfterOneYear() {
    billing.setDueDate(LocalDate.now().plusYears(1).plusDays(1));

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("106"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("dueDate"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("due date must not the set to more than one year further the current date"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(billing.getDueDate()))));
  }

  @Test
  void Should_ReturnValidationSuccess_When_DueDateIsEqualOneYearAhead() {
    billing.setDueDate(LocalDate.now().plusYears(1));
    billing.setAcceptPastPayment(false);
    billing.setExpirationDate(null);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }


  @Test
  void Should_ReturnValidationFailed_When_AcceptPastPaymentIsNull() {
    billing.setAcceptPastPayment(null);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("107"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("acceptPastPayment"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("accept past payment not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

  @Test
  void Should_ReturnValidationFailed_When_AcceptPastPaymentIsTrueAndExpirationDateIsNull() {
    billing.setAcceptPastPayment(true);
    billing.setExpirationDate(null);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("108"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("expirationDate"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("expiration date must be provided whenever past payment is accepted"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

  @Test
  void Should_ReturnValidationFailed_When_AcceptPastPaymentIsFalseAndExpirationDateIsProvided() {
    billing.setAcceptPastPayment(false);
    billing.setExpirationDate(LocalDate.now().plusDays(11));

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("109"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("expirationDate"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("expiration date must not be provided whenever past payment is not accepted"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(billing.getExpirationDate()))));
  }

  @Test
  void Should_ReturnValidationFailed_When_AcceptPastPaymentIsTrueAndExpirationDateIsBeforeDueDate() {
    billing.setAcceptPastPayment(true);
    billing.setExpirationDate(billing.getDueDate().minusDays(1));

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("110"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("expirationDate"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("expiration date must be further due date"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(billing.getExpirationDate()))));
  }

  @Test
  void Should_ReturnValidationFailed_When_AcceptPastPaymentIsTrueAndExpirationDateIsEqualDueDate() {
    billing.setAcceptPastPayment(true);
    billing.setExpirationDate(billing.getDueDate());

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("110"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("expirationDate"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("expiration date must be further due date"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(billing.getExpirationDate()))));
  }

  @Test
  void Should_ReturnValidationSuccess_When_AcceptPastPaymentIsTrueAndExpirationDateIsSixMonthsAfterDueDate() {
    billing.setAcceptPastPayment(true);
    billing.setExpirationDate(billing.getDueDate().plusMonths(6));

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }

  @Test
  void Should_ReturnValidationFailed_When_AcceptPastPaymentIsTrueAndExpirationDateIsAfterSixMonthsFurtherDueDate() {
    billing.setAcceptPastPayment(true);
    billing.setExpirationDate(billing.getDueDate().plusMonths(6).plusDays(1));

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("111"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("expirationDate"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("expiration date must be set to more than 6 months past due date"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(billing.getExpirationDate()))));
  }


  @Test
  void Should_ReturnValidationFailed_When_ApplyFineForPastPaymentIsNull() {
    billing.setApplyFineForPastPayment(null);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("112"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("applyFineForPastPayment"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("flag 'apply fine for past payment' not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

  @Test
  void Should_ReturnValidationSuccess_When_ApplyFineForPastPaymentIsFalse() {
    billing.setApplyFineForPastPayment(false);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }


  @Test
  void Should_ReturnValidationFailed_When_PayerIsNull() {
    billing.setPayer(null);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("113"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("payer"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("payer not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }


  @Test
  void Should_ReturnValidationFailed_When_ReceiverIsNull() {
    billing.setReceiver(null);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("114"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("receiver"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("receiver not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }


  @Test
  void Should_ReturnValidationSuccess_When_AdditionalInfoIsNull() {
    billing.setAdditionalInfo(null);

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }

  @Test
  void Should_ReturnValidationFailed_When_AdditionalInfoIsEmpty() {
    billing.setAdditionalInfo(new ArrayList<>());

    final ValidationResult validationResult = validator.validate(billing);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasItem(hasProperty("code", equalTo("115"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("additionalInfo"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("additional info list must not be empty"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(billing.getAdditionalInfo()))));
  }

}
