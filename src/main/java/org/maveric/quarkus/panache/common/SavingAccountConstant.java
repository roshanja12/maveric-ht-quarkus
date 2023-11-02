package org.maveric.quarkus.panache.common;

public class SavingAccountConstant {
    public static final String QUERY=" WHERE customerName = ?1 OR customerId = ?2 OR customerEmail = ?1 OR customerPhone = ?2  ORDER BY savingsAccountId DESC ";
    public final static String GET_ACCOUNTS_PATH="/api/v1/accounts/saving";
    public final static String GET_SUCCESS_RESPONSE_MSG="Data rendered successfully";
    public final static String SUCCESS_MSG="Success";
    public final static String UPDATED_SUCCESS_RESPONSE_MSG="Updated successfully";

    public final static String UPDATE_ACCOUNTS_PATH="/api/v1/accounts/saving";
}
