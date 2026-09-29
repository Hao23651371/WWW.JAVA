package vn.fit.jakartaee_baitap2_tuan3.repository;

import vn.fit.jakartaee_baitap2_tuan3.model.ShoppingCart;
import java.util.List;

public interface ShoppingCartRepository {
    List<ShoppingCart> findAll();
    ShoppingCart findById(int id);
    List<ShoppingCart> findByCustomerName(String customerName);
    boolean save(ShoppingCart cart);
    boolean update(ShoppingCart cart);
    boolean delete(int id);
    boolean deleteByCustomerName(String customerName);
}