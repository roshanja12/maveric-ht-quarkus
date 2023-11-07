package org.maveric.quarkus.panache.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.maveric.quarkus.panache.model.Transaction;

import java.util.List;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transaction> {
    public List<Transaction> findBySavingAccount(Long savingsAccountId,int pageNumber, int pageSize){
       PanacheQuery<Transaction> query= find("from Transaction where savingAccount.savingsAccountId= ?1 order by date desc",savingsAccountId);
       return query.page(pageNumber,pageSize).list();
    }

}
