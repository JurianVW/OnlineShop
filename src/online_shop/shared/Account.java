package online_shop.shared;

public class Account {
    Integer id;
    AccountType accountType;
    String name;
    String email;

    private Address address;

    public Account(Integer id, AccountType accountType, String name, String email) {
        this.id = id;
        this.accountType = accountType;
        this.name = name;
        this.email = email;
    }

    public Account(Integer id, AccountType accountType, String name, String email, String streetName, String houseNumber, String postalCode, String place) {

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
