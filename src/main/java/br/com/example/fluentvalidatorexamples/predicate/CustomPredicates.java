package br.com.example.fluentvalidatorexamples.predicate;

import br.com.fluentvalidator.predicate.PredicateBuilder;

import java.time.LocalDate;
import java.util.function.Predicate;

import static br.com.fluentvalidator.predicate.LocalDatePredicate.localDateBeforeOrEqual;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static br.com.fluentvalidator.predicate.StringPredicate.stringMatches;
import static java.util.function.Predicate.not;

public class CustomPredicates {

  public static Predicate<LocalDate> hasMininumAgeOf(final int age) {
    return PredicateBuilder.<LocalDate>from(not(nullValue()))
      .and(localDateBeforeOrEqual(LocalDate.now().minusYears(age)));
  }

  public static Predicate<String> hasValidEmail() {
    return PredicateBuilder.from(not(stringEmptyOrNull()))
      .and(stringMatches("^.*\\@.*\\.\\w+$"));
  }

}
