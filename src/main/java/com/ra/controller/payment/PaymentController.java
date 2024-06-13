package com.ra.controller.payment;

import com.ra.model.entity.ENUM.PaymentMethods;
import com.ra.model.entity.dto.response.user.CheckOutInforDTO;
import com.ra.model.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    public RedirectView getPay(@RequestParam("typePay") String typePay,@Valid @ModelAttribute("checkOutInfor") CheckOutInforDTO checkOutInfor
            , @RequestParam("totalPriceAll") double totalPriceAll, HttpServletRequest request,RedirectAttributes redirAttrs) throws UnsupportedEncodingException {
        if (checkOutInfor.getReceiveName().isEmpty()){
            redirAttrs.addFlashAttribute("error", "Chọn đỉa chỉ nhận hàng trước khi thanh toán!");
            return new RedirectView("/user/cart/checkOut");
        }
        checkOutInforStatic = checkOutInfor;
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = (long) (totalPriceAll * 100);
        String bankCode = typePay;
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = request.getRemoteAddr();
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

        TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar cld = Calendar.getInstance(timeZone);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(timeZone);
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        return new RedirectView(paymentUrl);
    }


}
