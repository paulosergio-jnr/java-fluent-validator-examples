package br.com.example.fluentvalidatorexamples.predicate;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static br.com.example.fluentvalidatorexamples.predicate.CustomPredicates.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomPredicatesTest {

  @Test
  void testHasMininumAgeOf() {
    assertTrue(hasMininumAgeOf(18).test(LocalDate.now().minusYears(18)));
    assertTrue(hasMininumAgeOf(18).test(LocalDate.now().minusYears(19)));
    assertFalse(hasMininumAgeOf(18).test(LocalDate.now().minusYears(17)));

    assertFalse(hasMininumAgeOf(18).test(LocalDate.now().plusDays(1).minusYears(18)));
    assertTrue(hasMininumAgeOf(18).test(LocalDate.now().minusDays(1).minusYears(18)));

    assertFalse(hasMininumAgeOf(18).test(null));
  }

  @Test
  void testHasValidEmail() {
    assertTrue(hasValidEmail().test("teste@teste.com"));
    assertTrue(hasValidEmail().test("teste@teste.com.br"));
    assertTrue(hasValidEmail().test("teste123@teste.com"));
    assertTrue(hasValidEmail().test("teste@teste.com.123"));
    assertTrue(hasValidEmail().test("123@123.com"));
    assertFalse(hasValidEmail().test("a@a.info"));
    assertFalse(hasValidEmail().test("teste@teste"));
    assertFalse(hasValidEmail().test("teste@teste."));
    assertFalse(hasValidEmail().test("teste@teste.com."));
    assertFalse(hasValidEmail().test(""));
    assertFalse(hasValidEmail().test(null));
  }

}
