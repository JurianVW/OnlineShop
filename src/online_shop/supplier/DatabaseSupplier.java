package online_shop.supplier;

import online_shop.shared.Account;
import online_shop.shared.AccountType;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSupplier {
    List<Product> products;

    public DatabaseSupplier(){
        products = new ArrayList<>();
        products.add(new Product("Blade Runner 2049 Steelbook E1",1, 50.49,309,500));
        products.add(new Product("Blade Runner 2049 Steelbook E2",2, 35.00,463,500));
        products.add(new Product("Blade Runner 2049 Steelbook E3",3, 59.99,235,500));
        products.add(new Product("Thor Ragnarok Steelbook E1",4, 50.00,800,800));
        products.add(new Product("It Steelbook E1",5, 34.50,346,800));
        products.add(new Product("It Steelbook E2",6, 65.49,267,300));
    }

    public Account logIn(String username, String password){
        if(username.equals("Jurian") && password.equals("hoi")){
            return new Account(2, AccountType.SUPPLIEREMPLOYEE, "Jurian van Woerkom", "jurianvanwoerkoM@hotmail.com");
        }
        return null;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void removeProduct(Product product){
        products.remove(product);
    }

    public void productChanged(Product product){
        for (Product p: products) {
            if(p.getId() == product.getId()){
                p = product;
            }
        }
    }

    public List<Product> getProducts(){
        return products;
    }
}
//TODO: Database Supplier