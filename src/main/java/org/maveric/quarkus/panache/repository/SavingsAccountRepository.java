package org.maveric.quarkus.panache.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.maveric.quarkus.panache.model.SavingsAccount;

@ApplicationScoped
public class SavingsAccountRepository implements PanacheRepository<SavingsAccount> {

    public SavingsAccount findBySavingsAccountId(Long savingsAccountId){
        return find("savingsAccountId", savingsAccountId).firstResult();
    }

    public SavingsAccount findByCustomerId(Long customerId){
        return find("customerId", customerId).firstResult();
    }
}
