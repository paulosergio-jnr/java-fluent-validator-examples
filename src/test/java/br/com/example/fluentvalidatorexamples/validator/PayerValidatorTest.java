package br.com.example.fluentvalidatorexamples.validator;

import br.com.example.fluentvalidatorexamples.domain.Address;
import br.com.example.fluentvalidatorexamples.domain.Payer;
import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PayerValidatorTest {

  private final PayerValidator validator = new PayerValidator();

  private Payer payer;

  @BeforeEach
  void setUp() {
    payer = new Payer();

    final Address address = new Address();
    address.setAddressLine1("address");
    address.setCity("city");
    address.setState("state");
    address.setCountry("us");
    address.setZipcode("1234567890");

    payer.setBillingAddress(address);
    payer.setEmail("test@test.com");
    payer.setBirthday(LocalDate.of(1990, Month.OCTOBER, 5));
    payer.setFirstName("Paulo");
    payer.setLastName("Junior");
  }

  @Test
  void Should_ReturnValidationSuccess_When_PayerIsCompletelyCorrect() {
    final ValidationResult validationResult = validator.validate(payer);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }


  @Test
  void Should_ReturnValidationFailed_When_FirstNameIsEmpty() {
    payer.setFirstName("");

    final ValidationResult validationResult = validator.validate(payer);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("301"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("payer.firstName"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("payer's first name not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }

  @Test
  void Should_ReturnValidationFailed_When_FirstNameIsNull() {
    payer.setFirstName(null);

    final ValidationResult validationResult = validator.validate(payer);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("301"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("payer.firstName"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("payer's first name not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }


  @Test
  void Should_ReturnValidationFailed_When_LastNameIsEmpty() {
    payer.setLastName("");

    final ValidationResult validationResult = validator.validate(payer);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("302"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("payer.lastName"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("payer's last name not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }

  @Test
  void Should_ReturnValidationFailed_When_LastNameIsNull() {
    payer.setLastName(null);

    final ValidationResult validationResult = validator.validate(payer);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("302"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("payer.lastName"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("payer's last name not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }


  @Test
  void Should_ReturnValidationFailed_When_BirthdayIsNull() {
    payer.setBirthday(null);

    final ValidationResult validationResult = validator.validate(payer);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("303"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("payer.birthday"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("payer's birthday not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

  @Test
  void Should_ReturnValidationSuccess_When_PayersAgeIsExactlyMinimum() {
    final LocalDate birthday = LocalDate.now().minusYears(18);

    payer.setBirthday(birthday);

    final ValidationResult validationResult = validator.validate(payer);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }

  @Test
  void Should_ReturnValidationFailed_When_PayersAgeIsBellowMinimum() {
    final LocalDate birthday = LocalDate.now().minusYears(18).plusDays(1);

    payer.setBirthday(birthday);

    final ValidationResult validationResult = validator.validate(payer);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("304"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("payer.birthday"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("payer's age must be at least 18"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(birthday))));
  }


  @Test
  void Should_ReturnValidationFailed_When_EmailIsNull() {
    payer.setEmail(null);

    final ValidationResult validationResult = validator.validate(payer);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("305"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("payer.email"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("payer's e-mail must be provided and follow the pattern: 'e-mail@provider.extension'"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

  @Test
  void Should_ReturnValidationFailed_When_EmailIsEmpty() {
    payer.setEmail("");

    final ValidationResult validationResult = validator.validate(payer);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("305"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("payer.email"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("payer's e-mail must be provided and follow the pattern: 'e-mail@provider.extension'"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }

  @Test
  void Should_ReturnValidationFailed_When_EmailIsInvalid() {
    payer.setEmail("test@");

    final ValidationResult validationResult = validator.validate(payer);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("305"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("payer.email"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("payer's e-mail must be provided and follow the pattern: 'e-mail@provider.extension'"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo("test@"))));
  }


  @Test
  void Should_ReturnValidationFailed_When_BillingAddressIsNull() {
    payer.setBillingAddress(null);

    final ValidationResult validationResult = validator.validate(payer);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("306"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("payer.billingAddress"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("payer's billing address not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

}
