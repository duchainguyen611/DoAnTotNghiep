package com.ra.controller.payment;

import com.ra.model.entity.ENUM.PaymentMethods;
import com.ra.model.entity.dto.response.user.CheckOutInforDTO;
import com.ra.model.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class PaymentController {
    private final ShoppingCartService shoppingCartService;
    private CheckOutInforDTO checkOutInforStatic;

    @GetMapping("/user/payment-callback")
    public RedirectView  paymentCallback(@RequestParam Map<String, String> queryParams, RedirectAttributes redirAttrs) throws IOException {
        String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
        if ("00".equals(vnp_ResponseCode)) {
            checkOutInforStatic.setPaymentMethods(PaymentMethods.ONLINE);
            shoppingCartService.checkOut(checkOutInforStatic);
            redirAttrs.addFlashAttribute("success", "Thanh toán thành công!");
            return new RedirectView("/user/profile/invoice");
        } else {
            redirAttrs.addFlashAttribute("error", "Thanh toán không thành công!");
            return new RedirectView("/user/cart");
        }
    }

    @PostMapping("/user/payment")
    public RedirectView getPay(@RequestParam("typePay") String typePay, @ModelAttribute("checkOutInfor") CheckOutInforDTO checkOutInfor,
                               @RequestParam("totalPriceAll") double totalPriceAll) throws UnsupportedEncodingException {
        checkOutInforStatic = checkOutInfor;
        System.out.println(checkOutInforStatic.getReceiveName());

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = (long) (totalPriceAll * 100); // Amount in smallest currency unit
        String bankCode = typePay;
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1"; // Replace with the actual IP address of the client
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Add creation date and expiry date
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Build data for hash and query string
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        // Calculate secure hash
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        query.append("&vnp_SecureHashType=SHA512&vnp_SecureHash=").append(URLEncoder.encode(vnp_SecureHash, StandardCharsets.US_ASCII.toString()));

        // Construct payment URL
        String paymentUrl = Config.vnp_PayUrl + "?" + query.toString();
        return new RedirectView(paymentUrl);
    }


}
