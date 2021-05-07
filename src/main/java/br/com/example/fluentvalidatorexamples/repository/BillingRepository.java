package br.com.example.fluentvalidatorexamples.repository;

import br.com.example.fluentvalidatorexamples.domain.Billing;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class BillingRepository {

  private static final Set<Billing> DATABASE = new HashSet<>();

  public Billing save(final Billing billing) {
    billing.setId(UUID.randomUUID());

    DATABASE.add(billing);

    return billing;
  }

  public Billing findById(final UUID id) {
    final Optional<Billing> result = DATABASE
        .stream()
        .filter(billing -> billing.getId().equals(id))
        .findFirst();

    return result.orElse(null);
  }

  public Billing update(final Billing billing) {
    final Billing foundBilling = findById(billing.getId());
    DATABASE.remove(foundBilling);
    DATABASE.add(billing);

    return billing;
  }

  public void delete(final UUID id) {
    final Billing foundBilling = findById(id);
    DATABASE.remove(foundBilling);
  }

}
