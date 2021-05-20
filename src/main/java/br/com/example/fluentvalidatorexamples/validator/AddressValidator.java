package br.com.example.fluentvalidatorexamples.validator;

import br.com.example.fluentvalidatorexamples.domain.Address;
import br.com.example.fluentvalidatorexamples.enums.Country;
import br.com.fluentvalidator.AbstractValidator;

import java.util.HashSet;
import java.util.Set;

import static br.com.fluentvalidator.predicate.LogicalPredicate.isTrue;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.isNumber;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static java.util.function.Predicate.not;

public class AddressValidator extends AbstractValidator<Address> {

  private final String prefix;

  private static final Set<String> ACCEPTED_COUNTRIES;

  static {
    ACCEPTED_COUNTRIES = new HashSet<>();
    ACCEPTED_COUNTRIES.add(Country.UNITED_STATES.getCode());
    ACCEPTED_COUNTRIES.add(Country.CANADA.getCode());
    ACCEPTED_COUNTRIES.add(Country.MEXICO.getCode());
  }

  @Override
  public void rules() {

    /**
     * rules for Address:
     * - address line 1 is mandatory
     * - address line 2 is not mandatory, but whence is not null, it must not the a empty string
     * - city is mandatory
     * - state is mandatory
     * - country is mandatory and must match one of the Country Enum key
     * - only US, Canada and Mexico countries are allowed
     * - zip code is mandatory
     * - zip code must be numbers-only
     */

    ruleFor(prefix + "addressLine1", Address::getAddressLine1)
      /**
       * address line 1 must not be null or empty string
       */
      .must(not(stringEmptyOrNull()))
        .withCode("401")
        .withMessage("address line 1 not provided");

    ruleFor(prefix + "addressLine2", Address::getAddressLine2)
      /**
       * address line 2 must not be empty string whenever it is not null
       */
      .must(not(stringEmptyOrNull()))
      .when(not(nullValue()))
        .withCode("402")
        .withMessage("address line 2 not provided");

    ruleFor(prefix + "city", Address::getCity)
      /**
       * city must not be null or empty string
       */
      .must(not(stringEmptyOrNull()))
        .withCode("403")
        .withMessage("city not provided");

    ruleFor(prefix + "state", Address::getState)
      /**
       * state must not be null or empty string
       */
      .must(not(stringEmptyOrNull()))
        .withCode("404")
        .withMessage("state not provided");

    ruleFor(prefix + "country", Address::getCountry)
      /**
       * country must not be null or empty string
       */
      .must(not(stringEmptyOrNull()))
        .withCode("405")
        .withMessage("country not provided")
        .critical()

      /**
       * country must match any of the Countries in Country Enum
       */
      .must(isTrue(Country::exists))
        .withCode("406")
        .withMessage("invalid country. Please, use one of the following: " + Country.getAllCountries())
        .critical()

      /**
       * only US, Canada and Mexico countries are allowed
       * a list with the accepted countries is provided for this validation
       */
      .must(ACCEPTED_COUNTRIES::contains)
        .withCode("407")
        .withMessage("only US, Canada and Mexico countries are allowed for this transaction");

    ruleFor(prefix + "zipcode", Address::getZipcode)
      /**
       * zipcode must not be null or empty string
       */
      .must(not(stringEmptyOrNull()))
      .withCode("408")
        .withMessage("zip code not provided")
        .critical()

      /**
       * zipcode must be number-only
       */
      .must(isNumber())
        .withCode("409")
        .withMessage("incorrect zip code format. Only numbers are accepted")
        .critical();

  }

  public AddressValidator() {
    prefix = "";
  }

  public AddressValidator(final String prefix) {
    this.prefix = prefix;
  }

}
