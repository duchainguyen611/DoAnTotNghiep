package com.ra.model.entity.dto.response.admin.dashboard;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DashboardSellGoodsDTO implements ExcelExportable {
    @Id
    private Long id;
    private String productName;
    private String categoryName;
    private String brandName;
    private Integer outputQuantity;
    private Double totalAmount;
    private LocalDate createdDate;

    @Override
    public String[] getHeaders() {
        return new String[] {"Stt", "Tên sản phẩm","Tên danh mục","Tên thương hiệu","Số lượng bán hàng","Tổng tiền","Ngày/tháng/năm"};
    }

    @Override
    public String[] getData() {
        return new String[] {id.toString(),productName,categoryName,brandName,outputQuantity.toString()
                , NumberFormat.getInstance(new Locale("vi", "VN")).format(totalAmount)+ "₫"
                ,createdDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))};
    }
}
