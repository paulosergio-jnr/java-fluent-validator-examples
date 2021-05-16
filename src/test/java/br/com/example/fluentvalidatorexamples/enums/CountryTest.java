package br.com.example.fluentvalidatorexamples.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class CountryTest {

  @Test
  void testEntryOf() {
    assertThat(Country.entryOf("us"), equalTo(Country.UNITED_STATES));
    assertThat(Country.entryOf("mx"), equalTo(Country.MEXICO));
    assertThat(Country.entryOf("ca"), equalTo(Country.CANADA));
    assertThat(Country.entryOf("br"), equalTo(Country.BRAZIL));
    assertThat(Country.entryOf("ir"), equalTo(Country.IRELAND));
    assertThat(Country.entryOf("en"), equalTo(Country.ENGLAND));
    assertThat(Country.entryOf(""), equalTo(null));
    assertThat(Country.entryOf(null), equalTo(null));
  }

  @Test
  void testExists() {
    assertThat(Country.exists(Country.UNITED_STATES.getCode()), equalTo(true));
    assertThat(Country.exists(Country.MEXICO.getCode()), equalTo(true));
    assertThat(Country.exists(Country.CANADA.getCode()), equalTo(true));
    assertThat(Country.exists(Country.BRAZIL.getCode()), equalTo(true));
    assertThat(Country.exists(Country.IRELAND.getCode()), equalTo(true));
    assertThat(Country.exists(Country.ENGLAND.getCode()), equalTo(true));
    assertThat(Country.exists("non_present"), equalTo(false));
    assertThat(Country.exists(""), equalTo(false));
    assertThat(Country.exists(null), equalTo(false));

  }

  @Test
  void testGetAllCountries() {
    final String result = Country.getAllCountries();
    Assertions.assertEquals("br,ir,en,mx,us,ca", result);
  }

}
