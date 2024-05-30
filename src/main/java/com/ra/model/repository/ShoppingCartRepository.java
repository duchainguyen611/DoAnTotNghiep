package com.ra.model.repository;

import com.ra.model.entity.Product;
import com.ra.model.entity.ShoppingCart;
import com.ra.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    List<ShoppingCart> getAllByUser(User user);
    void deleteShoppingCartByIdAndUser(Long id, User user);
    void deleteByUser(User user);
    ShoppingCart findByProductAndUser(Product product, User user);

}
