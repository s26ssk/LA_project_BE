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
 * Lớp đại diện cho thực thể Certification (Chứng chỉ) trong cơ sở dữ liệu.
 * Sử dụng @Entity để chỉ định rằng lớp này là một thực thể JPA.
 * Sử dụng @Table để xác định bảng trong cơ sở dữ liệu mà lớp này tương ứng.
 * Sử dụng @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor từ Lombok để tự động sinh các phương thức getter, setter,
 * cũng như các constructor không tham số và có tham số.
 */
@Entity
@Table(name = "certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Certification implements Serializable {

    private static final long serialVersionUID = 349856938003329390L; // Serial version UID cho tính tương thích

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_id", unique = true, nullable = false) // Cột ID của chứng chỉ
    private Long certificationId;

    @Column(name = "certification_name", nullable = false, length = 50) // Tên của chứng chỉ
    private String certificationName;

    @Column(name = "certification_level", nullable = false) // Cấp độ của chứng chỉ
    private Integer certificationLevel;

    @OneToMany(mappedBy = "certification", cascade = CascadeType.ALL)
    @JsonManagedReference // Tham chiếu quản lý trong mối quan hệ
    private Set<EmployeesCertification> employeesCertifications; // Tập hợp các chứng chỉ của nhân viên

}
