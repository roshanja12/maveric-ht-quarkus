package org.maveric.quarkus.panache.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.maveric.quarkus.panache.model.SavingsAccount;

import java.util.List;

@ApplicationScoped
public class SavingsAccountRepository implements PanacheRepository<SavingsAccount> {

    public List<SavingsAccount> findBySavingsAccountId(Long savingsAccountId){
        return find("savingsAccountId", savingsAccountId).list();
    }

    public  List<SavingsAccount> findByCustomerId(Long customerId){
        return find("customerId", customerId).list();
    }
}
