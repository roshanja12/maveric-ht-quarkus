package org.maveric.quarkus.panache.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.maveric.quarkus.panache.model.SavingAccount;

@ApplicationScoped
public class SavingAccountRepository implements PanacheRepository<SavingAccount> {

    public SavingAccount findBySavingsAccountId(Long savingsAccountId){
        return find("savingsAccountId", savingsAccountId).firstResult();
    }

    public SavingAccount findByCustomerId(Long customerId){
        return find("customerId", customerId).firstResult();
    }
}
