package br.com.example.fluentvalidatorexamples.repository;

import br.com.example.fluentvalidatorexamples.domain.Billing;
import br.com.example.fluentvalidatorexamples.exception.BillingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static br.com.example.fluentvalidatorexamples.utils.BillingTemplate.createBilling;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


class BillingRepositoryTest {

  private BillingRepository billingRepository;

  @BeforeEach
  void setUp() {
    billingRepository = new BillingRepository();
  }

  @Test
  void Should_SaveBilling_When_BillingIsRight() {
    final Billing savedBilling = billingRepository.save(createBilling());

    final Set<Billing> database = billingRepository.getDatabase();

    assertThat(savedBilling, not(nullValue()));
    assertThat(savedBilling.getId(), not(nullValue()));
    assertThat(database, hasItem(savedBilling));
  }

  @Test
  void Should_ReturnEmptyListOfBillings_When_FindingForAllBillingsInAnEmptyList() {
    final Set<Billing> billings = billingRepository.findAll();

    assertThat(billings, not(nullValue()));
    assertThat(billings, is(empty()));
  }

  @Test
  void Should_ReturnNonEmptyListOfBillings_When_FindingForAllBillingsInANonEmptyList() {
    final Billing billing = createBilling();

    final Set<Billing> database = billingRepository.getDatabase();
    database.add(billing);

    final Set<Billing> billings = billingRepository.findAll();

    assertThat(billings, not(nullValue()));
    assertThat(billings, hasSize(1));
    assertThat(billings, hasItem(billing));
  }

  @Test
  void Should_ReturnBilling_When_FindingForExistingBillingById() throws BillingNotFoundException {
    final UUID id = UUID.randomUUID();

    final Billing billing = createBilling();
    billing.setId(id);

    final Set<Billing> database = billingRepository.getDatabase();
    database.add(billing);

    final Billing foundBilling = billingRepository.findById(id);

    assertThat(foundBilling, not(nullValue()));
    assertThat(foundBilling, equalTo(billing));
  }

  @Test
  void Should_ThrowException_When_FindingForNonExistingBillingById() {
    final UUID id = UUID.randomUUID();

    final BillingNotFoundException exception = catchThrowableOfType(() -> billingRepository.findById(id), BillingNotFoundException.class);

    assertThat(exception, not(nullValue()));
    assertThat(exception, instanceOf(BillingNotFoundException.class));
  }


  @Test
  void Should_ReturnUpdatedBilling_When_UpdatingAnExistingBilling() throws BillingNotFoundException {
    final UUID id = UUID.randomUUID();

    final Billing billing = createBilling();
    billing.setId(id);

    final Set<Billing> database = billingRepository.getDatabase();
    database.add(billing);

    final Billing newBilling = createBilling();
    newBilling.setId(id);

    final Billing updatedBilling = billingRepository.update(newBilling);

    assertThat(updatedBilling, not(equalTo(billing)));
    assertThat(updatedBilling, equalTo(newBilling));
    assertThat(database, hasSize(1));
    assertThat(database, hasItem(newBilling));
  }

  @Test
  void Should_ThrowException_When_UpdatingAnNonExistingBilling() {
    final UUID id = UUID.randomUUID();

    final Billing newBilling = createBilling();
    newBilling.setId(id);

    final BillingNotFoundException exception = catchThrowableOfType(() -> billingRepository.update(newBilling), BillingNotFoundException.class);

    assertThat(exception, not(nullValue()));
    assertThat(exception, instanceOf(BillingNotFoundException.class));
  }

  @Test
  void Should_DeleteBilling_When_DeletingAnExistingBilling() throws BillingNotFoundException {
    final UUID id = UUID.randomUUID();

    final Billing billing = createBilling();
    billing.setId(id);

    final Set<Billing> database = billingRepository.getDatabase();
    database.add(billing);

    billingRepository.delete(id);

    assertThat(database, is(empty()));
  }

  @Test
  void Should_ThrowException_When_DeletingAnNonExistingBilling() {
    final UUID id = UUID.randomUUID();

    final BillingNotFoundException exception = catchThrowableOfType(() -> billingRepository.delete(id), BillingNotFoundException.class);

    assertThat(exception, not(nullValue()));
    assertThat(exception, instanceOf(BillingNotFoundException.class));
  }


}
