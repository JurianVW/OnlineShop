package onlineshop.shared;

import java.io.Serializable;

public class Account implements Serializable {
    Integer id;
    AccountType accountType;
    String name;
    String email;

    public Account(Integer id, AccountType accountType, String name, String email) {
        this.id = id;
        this.accountType = accountType;
        this.name = name;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
