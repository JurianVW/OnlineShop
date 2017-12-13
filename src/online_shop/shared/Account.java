package online_shop.shared;

public class Account {
    Integer id;
    AccountType accountType;
    String name;
    String email;

    private Address address;

    public Account(Integer id, AccountType accountType, String name, String email) {

    }

    public Account(Integer id, AccountType accountType, String name, String email, String streetName, String houseNumber, String postalCode, String place) {

    }

    public Integer getId() {
        throw new UnsupportedOperationException();
    }

    public AccountType getAccountType() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public String getEmail() {
        throw new UnsupportedOperationException();
    }
}
