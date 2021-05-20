package br.com.example.fluentvalidatorexamples.validator;

import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class AdditionalInfoValidatorTest {

  private final AdditionalInfoValidator validator = new AdditionalInfoValidator();

  @Test
  void Should_ReturnValidationOk_When_AdditionalInfoIsRight() {
    final String additionalInfo = "This is an additional info";

    final ValidationResult validationResult = validator.validate(additionalInfo);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(true));
  }

  @Test
  void Should_ReturnValidationFailed_When_AdditionalInfoIsEmpty() {
    final String additionalInfo = "";

    final ValidationResult validationResult = validator.validate(additionalInfo);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("601"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("additionalInfo[0]"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("additional info cannot be empty or null"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(""))));
  }

  @Test
  void Should_ReturnValidationFailed_When_AdditionalInfoIsNull() {
    final String additionalInfo = null;

    final ValidationResult validationResult = validator.validate(additionalInfo);

    assertThat(validationResult, not(nullValue()));
    assertThat(validationResult.isValid(), equalTo(false));

    final Collection<Error> errors = validationResult.getErrors();

    assertThat(errors, hasSize(1));
    assertThat(errors, hasItem(hasProperty("code", equalTo("601"))));
    assertThat(errors, hasItem(hasProperty("field", equalTo("additionalInfo[0]"))));
    assertThat(errors, hasItem(hasProperty("message", equalTo("additional info cannot be empty or null"))));
    assertThat(errors, hasItem(hasProperty("attemptedValue", equalTo(null))));
  }

}
