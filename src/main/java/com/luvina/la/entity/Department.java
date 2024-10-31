/**
 * Copyright(C) 2024  Luvina
 * Certification.java, 04/10/2024 KhanhNV
 */
package com.luvina.la.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

/**
 * Lớp đại diện cho thực thể Department (Phòng ban) trong cơ sở dữ liệu.
 * Sử dụng @Entity để chỉ định rằng lớp này là một thực thể JPA.
 * Sử dụng @Table để xác định bảng trong cơ sở dữ liệu mà lớp này tương ứng.
 * Sử dụng @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor từ Lombok để tự động sinh các phương thức getter, setter,
 * cũng như các constructor không tham số và có tham số.
 */
@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department implements Serializable {

    private static final long serialVersionUID = 5250546799844056946L; // Serial version UID cho tính tương thích

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", unique = true, nullable = false) // Cột ID của phòng ban
    private Long departmentId;

    @Column(name = "department_name", nullable = false, length = 50) // Tên của phòng ban
    private String departmentName;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonManagedReference // Tham chiếu quản lý trong mối quan hệ
    private Set<Employee> employees; // Tập hợp các nhân viên thuộc phòng ban

}
