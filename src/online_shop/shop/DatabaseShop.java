package online_shop.shop;

import online_shop.shared.Account;
import online_shop.shared.AccountType;
import online_shop.supplier.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseShop {
    List<ShopProduct> shopProducts;

    public DatabaseShop(){
        shopProducts = new ArrayList<>();
        shopProducts.add(new ShopProduct(new Product("The Lion King E1",2, 35.00,463,500)));
        shopProducts.add(new ShopProduct(new Product("The Lion King E2",3, 59.99,235,500)));
        shopProducts.add(new ShopProduct(new Product("The Jungle Book",4, 50.00,800,800)));
    }

    public Account logIn(String username, String password){
        if(username.equals("Jurian") && password.equals("hoi")){
            return new Account(2, AccountType.SHOPEMPLOYEE, "Jurian van Woerkom", "jurianvanwoerkoM@hotmail.com");
        }
        return null;
    }

    public void addShopProduct(ShopProduct product){
        shopProducts.add(product);
    }

    public void removeShopProduct(ShopProduct product){
        shopProducts.remove(product);
    }

    public List<ShopProduct> getShopProducts(){
        return shopProducts;
    }


}
