package br.com.example.fluentvalidatorexamples.repository;

import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.example.fluentvalidatorexamples.exception.BillingNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class BillingRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(BillingRepository.class);

  private final Set<Billing> DATABASE = new HashSet<>();

  public Billing save(final Billing billing) {
    billing.setId(UUID.randomUUID());

    LOGGER.info("saving billing with parameters: " + billing);

    DATABASE.add(billing);

    LOGGER.info("billing saved successfully");

    return billing;
  }

  public Set<Billing> findAll() {
    LOGGER.info("fetching all billings");

    return DATABASE;
  }

  public Billing findById(final UUID id) throws BillingNotFoundException {
    LOGGER.info("finding billing by id  " + id.toString());

    final Optional<Billing> result = DATABASE
      .stream()
      .filter(billing -> billing.getId().equals(id))
      .findFirst();

    return result.orElseThrow(BillingNotFoundException::new);
  }

  public Billing update(final Billing billing) throws BillingNotFoundException {
    LOGGER.info("updating billing with parameters: " + billing);

    final Billing foundBilling = findById(billing.getId());

    DATABASE.remove(foundBilling);
    DATABASE.add(billing);

    LOGGER.info("billing updated successfully");

    return billing;
  }

  public void delete(final UUID id) throws BillingNotFoundException {
    LOGGER.info("deleting billing by id  " + id.toString());

    final Billing foundBilling = findById(id);

    DATABASE.remove(foundBilling);

    LOGGER.info("billing deleted successfully");
  }

  protected Set<Billing> getDatabase() {
    return DATABASE;
  }

}
