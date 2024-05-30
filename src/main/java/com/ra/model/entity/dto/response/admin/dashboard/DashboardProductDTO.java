package com.ra.model.entity.dto.response.admin.dashboard;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DashboardProductDTO implements ExcelExportable {
    @Id
    private Long id;
    private String nameProduct;
    private String nameCategory;
    private String nameBrand;
    private Integer inputQuantity;
    private Integer outputQuantity;
    private Integer stockQuantity;
    private LocalDate createdDate;

    @Override
    public String[] getHeaders() {
        return new String[] {"Stt", "Tên sản phẩm","Tên danh mục","Tên thương hiệu","Số lượng nhập hàng","Số lượng bán hàng","Số lượng trong kho","Ngày/tháng/năm"};
    }

    @Override
    public String[] getData() {
        return new String[] {id.toString(),nameProduct, nameCategory,nameBrand,inputQuantity.toString(),outputQuantity.toString(),stockQuantity.toString()
                , createdDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))};
    }
}
