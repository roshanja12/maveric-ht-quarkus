package org.maveric.quarkus.panache.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.maveric.quarkus.panache.model.Transaction;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transaction> {
}
