package com.ra.model.serviceImp;

import com.ra.model.entity.Product;
import com.ra.model.entity.User;
import com.ra.model.entity.WishList;
import com.ra.model.entity.dto.response.user.WishListResponseDTO;
import com.ra.model.repository.WishListRepository;
import com.ra.model.service.WishListService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class WishListServiceImp implements WishListService {
    private final WishListRepository wishListRepository;
    @Override
    public List<WishList> getAllByUser(User user) {
        return wishListRepository.getAllByUser(user);
    }

    @Override
    public List<WishListResponseDTO> displayWishListByUser(User user) {
        return getAllByUser(user).stream().map(this::UEntityMap).toList();
    }

    @Transactional
    @Override
    public void deleteByUserAndId(User user, Long id) {
        wishListRepository.deleteWishListByUserAndId(user,id);
    }

    @Override
    public WishList save(WishList wishList) {
        return wishListRepository.save(wishList);
    }

    @Override
    public WishList findByProductAndUser(Product product, User user) {
        return wishListRepository.findByProductAndUser(product,user);
    }

    @Override
    public WishListResponseDTO UEntityMap(WishList wishList) {
        return WishListResponseDTO.builder()
                .id(wishList.getId())
                .productId(wishList.getProduct().getId())
                .nameProduct(wishList.getProduct().getProductName())
                .stockQuantity(wishList.getProduct().getStockQuantity())
                .imageProduct(wishList.getProduct().getImage())
                .unitPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(wishList.getProduct().getUnitPrice())+ "₫")
                .build();
    }


}
