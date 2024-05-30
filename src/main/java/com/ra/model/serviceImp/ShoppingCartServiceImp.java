package com.ra.model.serviceImp;


import com.ra.model.entity.ENUM.InvoiceStatus;
import com.ra.model.entity.*;
import com.ra.model.entity.dto.response.user.CartResponseDTO;
import com.ra.model.entity.dto.response.user.CheckOutInforDTO;
import com.ra.model.repository.InvoiceDetailRepository;
import com.ra.model.repository.InvoiceRepository;
import com.ra.model.repository.ShoppingCartRepository;
import com.ra.model.service.ProductService;
import com.ra.model.service.ShoppingCartService;
import com.ra.security.UserDetail.UserLogin;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImp implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserLogin userLogin;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceDetailRepository invoiceDetailRepository;
    private final ProductService productService;
    @Override
    public List<ShoppingCart> getAllByUser(User user) {
        return shoppingCartRepository.getAllByUser(user);
    }

    @Override
    public List<CartResponseDTO> displayCartByUser(User user) {
        return getAllByUser(user).stream().map(this::AEntityMap).toList();
    }
    @Transactional
    @Override
    public void deleteByUserAndId(User user, Long id) {
        shoppingCartRepository.deleteShoppingCartByIdAndUser(id,user);
    }

    @Override
    public ShoppingCart save(ShoppingCart cart) {
        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart findById(Long id) {
        return shoppingCartRepository.findById(id).orElse(null);
    }

    @Override
    public void updateOrderQuantity(Long id, Integer quantity) {
        ShoppingCart cart = findById(id);
        cart.setCartQuantity(quantity);
        save(cart);
    }

    @Override
    public void deleteByUser(User user) {
        shoppingCartRepository.deleteByUser(user);
    }
    @Transactional
    @Override
    public void checkOut(CheckOutInforDTO checkOutInfor) {
        User user = userLogin.userLogin();
        Invoice invoice = new Invoice();
        invoice.setUser(user);
        invoice.setReceiveName(checkOutInfor.getReceiveName());
        invoice.setReceivePhone(checkOutInfor.getReceivePhone());
        invoice.setReceiveAddress(checkOutInfor.getReceiveAddress());
        invoice.setPaymentMethods(checkOutInfor.getPaymentMethods());
        invoice.setNote(checkOutInfor.getNote());
        invoice.setSerialNumber(UUID.randomUUID().toString());
        invoice.setInvoiceStatus(InvoiceStatus.WAITING);
        double totalPriceMoney = 0;
        List<ShoppingCart> carts = shoppingCartRepository.getAllByUser(user);
        for (ShoppingCart cart:carts){
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setProduct(cart.getProduct());
            invoiceDetail.setInvoiceQuantity(cart.getCartQuantity());
            invoiceDetail.setInvoice(invoice);
            invoiceDetail.setProduct(productService.findById(cart.getProduct().getId()));
            totalPriceMoney+=(cart.getCartQuantity()*cart.getProduct().getUnitPrice());
            invoiceDetailRepository.save(invoiceDetail);
        }
        invoice.setTotalPrice(totalPriceMoney+40000);
        invoiceRepository.save(invoice);
        deleteByUser(user);
    }

    @Override
    public ShoppingCart findByProductAndUser(Product product, User user) {
        return shoppingCartRepository.findByProductAndUser(product,user);
    }

    @Override
    public CartResponseDTO AEntityMap(ShoppingCart cart) {
        return CartResponseDTO.builder()
                .id(cart.getId())
                .productId(cart.getProduct().getId())
                .imageProduct(cart.getProduct().getImage())
                .nameProduct(cart.getProduct().getProductName())
                .cartQuantity(cart.getCartQuantity())
                .unitPrice(cart.getProduct().getUnitPrice())
                .build();
    }
}
