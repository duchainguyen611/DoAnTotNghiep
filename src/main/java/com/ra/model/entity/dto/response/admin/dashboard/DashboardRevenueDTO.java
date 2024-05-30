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
public class DashboardRevenueDTO implements ExcelExportable {
    @Id
    private Long id;
    private LocalDate createdDate;
    private Double importAmount;
    private Double saleAmount;
    private Double totalAmount;

    @Override
    public String[] getHeaders() {
        return new String[] {"Stt", "Ngày/tháng/năm", "Số tiền nhập hàng","Số tiền bán hàng","Số tiền lỗ/lãi"};
    }

    @Override
    public String[] getData() {
           return new String[] {id.toString(), createdDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                NumberFormat.getInstance(new Locale("vi", "VN")).format(importAmount) + "₫",
                NumberFormat.getInstance(new Locale("vi", "VN")).format(saleAmount) + "₫",
                NumberFormat.getInstance(new Locale("vi", "VN")).format(totalAmount) + "₫"
        };
    }
}
