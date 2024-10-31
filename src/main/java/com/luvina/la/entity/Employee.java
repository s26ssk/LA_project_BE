/**
 * Copyright(C) 2024  Luvina
 * Employee.java, 04/10/2024 KhanhNV
 */
package com.luvina.la.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

/**
 * Lớp đại diện cho thực thể Employee (Nhân viên) trong cơ sở dữ liệu.
 * Sử dụng @Entity để chỉ định rằng lớp này là một thực thể JPA.
 * Sử dụng @Table để xác định bảng trong cơ sở dữ liệu mà lớp này tương ứng.
 * Sử dụng @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor từ Lombok để tự động sinh các phương thức getter, setter,
 * cũng như các constructor không tham số và có tham số.
 */
@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    private static final long serialVersionUID = 2117609759622903107L; // Serial version UID cho tính tương thích

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", unique = true, nullable = false) // Cột ID của nhân viên
    private Long employeeId;

    @ManyToOne // Mối quan hệ nhiều-1 với Department
    @JoinColumn(name = "department_id", nullable = false) // Cột ID phòng ban của nhân viên
    @JsonBackReference // Tham chiếu ngược trong mối quan hệ
    private Department department; // Phòng ban mà nhân viên thuộc về

    @Column(name = "employee_name", nullable = false) // Tên của nhân viên
    private String employeeName;

    @Column(name = "employee_name_kana") // Tên Kana của nhân viên
    private String employeeNameKana;

    @Column(name = "employee_birth_date") // Ngày sinh của nhân viên
    private Date employeeBirthDate;

    @Column(name = "employee_email", nullable = false) // Địa chỉ email của nhân viên
    private String employeeEmail;

    @Column(name = "employee_telephone", length = 50) // Số điện thoại của nhân viên
    private String employeeTelephone;

    @Column(name = "employee_login_id", nullable = false, length = 50) // ID đăng nhập của nhân viên
    private String employeeLoginId;

    @Column(name = "employee_login_password", length = 100) // Mật khẩu đăng nhập của nhân viên
    private String employeeLoginPassword;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore // Bỏ qua việc tuần tự hóa tập hợp này
    @JsonManagedReference // Tham chiếu quản lý trong mối quan hệ
    private Set<EmployeesCertification> employeesCertifications; // Tập hợp các chứng chỉ của nhân viên

}
