package br.com.example.fluentvalidatorexamples.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public enum Country {

  UNITED_STATES("us"),
  MEXICO("mx"),
  CANADA("ca"),
  BRAZIL("br"),
  IRELAND("ir"),
  ENGLAND("en");

  private static final Map<String, Country> ENTRIES = new HashMap<>();

  static {
    for (final Country country : Country.values()) {
      ENTRIES.put(country.code, country);
    }
  }

  private final String code;

  Country(final String code) {
    this.code = code;
  }

  public static Country entryOf(final String country) {
    return Objects.nonNull(country) ? ENTRIES.get(country.toLowerCase()) : null;
  }

  public static boolean exists(final String country) {
    return Objects.nonNull(country) && ENTRIES.containsKey(country);
  }

  public static String getAllCountries() {
    return ENTRIES.keySet().stream().reduce((s, s2) -> s = s + "," + s2).orElse("");
  }

}
