package br.com.example.fluentvalidatorexamples.validator;

import br.com.example.fluentvalidatorexamples.domain.Address;
import br.com.example.fluentvalidatorexamples.domain.Receiver;
import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ReceiverValidatorTest {

  private final ReceiverValidator validator = new ReceiverValidator();

  private Receiver receiver;

  @BeforeEach
  void setUp() {
    receiver = new Receiver();

    final Address address = new Address();
    address.setAddressLine1("address");
    address.setCity("city");
    address.setState("state");
    address.setCountry("us");
    address.setZipcode("1234567890");

    receiver.setAddress(address);
    receiver.setEmail("test@test.com");
    receiver.setBirthday(LocalDate.of(1990, Month.OCTOBER, 5));
    receiver.setFirstName("Paulo");
    receiver.setLastName("Junior");
  }


  @Test
  void Should_ReturnValidationSuccess_When_ReceiverIsCompletelyCorrect() {
    final ValidationResult validationResult = validator.validate(receiver);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }


  @Test
  void Should_ReturnValidationFailed_When_FirstNameIsEmpty() {
    receiver.setFirstName("");

    final ValidationResult validationResult = validator.validate(receiver);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("501"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("receiver.firstName"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("receiver's first name not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }

  @Test
  void Should_ReturnValidationFailed_When_FirstNameIsNull() {
    receiver.setFirstName(null);

    final ValidationResult validationResult = validator.validate(receiver);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("501"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("receiver.firstName"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("receiver's first name not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }


  @Test
  void Should_ReturnValidationFailed_When_LastNameIsEmpty() {
    receiver.setLastName("");

    final ValidationResult validationResult = validator.validate(receiver);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("502"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("receiver.lastName"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("receiver's last name not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }

  @Test
  void Should_ReturnValidationFailed_When_LastNameIsNull() {
    receiver.setLastName(null);

    final ValidationResult validationResult = validator.validate(receiver);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("502"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("receiver.lastName"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("receiver's last name not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }


  @Test
  void Should_ReturnValidationFailed_When_BirthdayIsNull() {
    receiver.setBirthday(null);

    final ValidationResult validationResult = validator.validate(receiver);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("503"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("receiver.birthday"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("receiver's birthday not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

  @Test
  void Should_ReturnValidationSuccess_When_ReceiversAgeIsExactlyMinimum() {
    final LocalDate birthday = LocalDate.now().minusYears(21);

    receiver.setBirthday(birthday);

    final ValidationResult validationResult = validator.validate(receiver);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }

  @Test
  void Should_ReturnValidationFailed_When_ReceiversAgeIsBellowMinimum() {
    final LocalDate birthday = LocalDate.now().minusYears(21).plusDays(1);

    receiver.setBirthday(birthday);

    final ValidationResult validationResult = validator.validate(receiver);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("504"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("receiver.birthday"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("receiver's age must be at least 21"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(birthday))));
  }


  @Test
  void Should_ReturnValidationFailed_When_EmailIsNull() {
    receiver.setEmail(null);

    final ValidationResult validationResult = validator.validate(receiver);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("505"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("receiver.email"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("receiver's e-mail must be provided and follow the pattern: 'e-mail@provider.extension'"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

  @Test
  void Should_ReturnValidationFailed_When_EmailIsEmpty() {
    receiver.setEmail("");

    final ValidationResult validationResult = validator.validate(receiver);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("505"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("receiver.email"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("receiver's e-mail must be provided and follow the pattern: 'e-mail@provider.extension'"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }

  @Test
  void Should_ReturnValidationFailed_When_EmailIsInvalid() {
    receiver.setEmail("test@");

    final ValidationResult validationResult = validator.validate(receiver);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("505"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("receiver.email"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("receiver's e-mail must be provided and follow the pattern: 'e-mail@provider.extension'"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo("test@"))));
  }


  @Test
  void Should_ReturnValidationFailed_When_AddressIsNull() {
    receiver.setAddress(null);

    final ValidationResult validationResult = validator.validate(receiver);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("506"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("receiver.billingAddress"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("receiver's billing address not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

}
