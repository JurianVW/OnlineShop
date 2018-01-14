package onlineshop.Tests;

import onlineshop.shared.Account;
import onlineshop.shared.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void getId() {
        Integer id = 10;
        Account a = new Account(id, AccountType.CUSTOMER,"Test userrrr", "Tesrrt@user.nl");
        Assertions.assertEquals(id,a.getId());
    }

    @Test
    void getAccountType() {
        Account a1 = new Account(10, AccountType.CUSTOMER,"Test d", "Teddst@user.nl");
        Assertions.assertEquals(AccountType.CUSTOMER,a1.getAccountType());

        Account a2 = new Account(10, AccountType.SHOPEMPLOYEE,"Test usdder", "Taa@user.nl");
        Assertions.assertEquals(AccountType.SHOPEMPLOYEE,a2.getAccountType());

        Account a3 = new Account(10, AccountType.SUPPLIEREMPLOYEE,"Test ffuser", "Test@gg.nl");
        Assertions.assertEquals(AccountType.SUPPLIEREMPLOYEE,a3.getAccountType());
    }

    @Test
    void getName() {
        String name = "nameuserblah";
        Account a = new Account(10, AccountType.CUSTOMER,name, "Test@user.nl");
        Assertions.assertEquals(name,a.getName());
    }

    @Test
    void getEmail() {
        String email = "emailssss";
        Account a = new Account(10, AccountType.CUSTOMER,"test user", email);
        Assertions.assertEquals(email,a.getEmail());
    }
}