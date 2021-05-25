package br.com.example.fluentvalidatorexamples.validator;

import br.com.example.fluentvalidatorexamples.domain.Address;
import br.com.example.fluentvalidatorexamples.enums.Country;
import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static br.com.example.fluentvalidatorexamples.utils.BillingTemplate.createAddress;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AddressValidatorTest {

  private final AddressValidator validator = new AddressValidator("> ");

  @Test
  void Should_ReturnValidationSuccess_When_AddressIsTotallyValid() {
    final Address address = createAddress();

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }


  @Test
  void Should_ReturnValidationFailed_When_AddressLineOneIsEmpty() {
    final Address address = createAddress();
    address.setAddressLine1("");

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("401"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> addressLine1"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("address line 1 not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }

  @Test
  void Should_ReturnValidationFailed_When_AddressLineOneIsNull() {
    final Address address = createAddress();
    address.setAddressLine1(null);

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("401"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> addressLine1"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("address line 1 not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }


  @Test
  void Should_ReturnValidationSuccess_When_AddressLineTwoIsNull() {
    final Address address = createAddress();
    address.setAddressLine2(null);

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }

  @Test
  void Should_ReturnValidationFailed_When_AddressLineTwoIsEmpty() {
    final Address address = createAddress();
    address.setAddressLine2("");

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("402"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> addressLine2"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("address line 2 not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }


  @Test
  void Should_ReturnValidationFailed_When_CityIsEmpty() {
    final Address address = createAddress();
    address.setCity("");

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("403"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> city"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("city not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }

  @Test
  void Should_ReturnValidationFailed_When_CityIsNull() {
    final Address address = createAddress();
    address.setCity(null);

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("403"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> city"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("city not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }


  @Test
  void Should_ReturnValidationFailed_When_StateIsEmpty() {
    final Address address = createAddress();
    address.setState("");

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("404"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> state"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("state not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }

  @Test
  void Should_ReturnValidationFailed_When_StateIsNull() {
    final Address address = createAddress();
    address.setState(null);

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("404"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> state"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("state not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }


  @Test
  void Should_ReturnValidationFailed_When_CountryIsEmpty() {
    final Address address = createAddress();
    address.setCountry("");

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("405"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> country"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("country not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }

  @Test
  void Should_ReturnValidationFailed_When_CountryIsNull() {
    final Address address = createAddress();
    address.setCountry(null);

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("405"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> country"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("country not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

  @Test
  void Should_ReturnValidationFailed_When_CountryDoesNotExist() {
    final Address address = createAddress();
    address.setCountry("am");

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("406"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> country"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("invalid country. Please, use one of the following: " + Country.getAllCountries()))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo("am"))));
  }

  @Test
  void Should_ReturnValidationFailed_When_CountryIsNotSupported() {
    final Address address = createAddress();
    address.setCountry("br");

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("407"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> country"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("only US, Canada and Mexico countries are allowed for this transaction"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo("br"))));
  }


  @Test
  void Should_ReturnValidationFailed_When_ZipcodeIsEmpty() {
    final Address address = createAddress();
    address.setZipcode("");

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("408"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> zipcode"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("zip code not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }

  @Test
  void Should_ReturnValidationFailed_When_ZipcodeIsNull() {
    final Address address = createAddress();
    address.setZipcode(null);

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("408"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> zipcode"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("zip code not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

  @Test
  void Should_ReturnValidationFailed_When_ZipcodeIsNotNumeric() {
    final Address address = createAddress();
    address.setZipcode("123456abc");

    final ValidationResult validationResult = validator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("409"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("> zipcode"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("incorrect zip code format. Only numbers are accepted"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo("123456abc"))));
  }


  @Test
  void Should_ReturnValidationFailedAndNoPrefix_When_AddressLineOneIsEmpty() {
    final Address address = createAddress();
    address.setAddressLine1("");

    final AddressValidator noPrefixValidator = new AddressValidator();
    final ValidationResult validationResult = noPrefixValidator.validate(address);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("401"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("addressLine1"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("address line 1 not provided"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }

}
