package com.ra.model.repository;

import com.ra.model.entity.Product;
import com.ra.model.entity.User;
import com.ra.model.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WishListRepository extends JpaRepository<WishList,Long> {
    List<WishList> getAllByUser(User user);
    void deleteWishListByUserAndId(User user, Long id);
    WishList findByProductAndUser(Product product, User user);
}
