/**
 * Copyright(C) 2024  Luvina
 * EmployeesCertification.java, 04/10/2024 KhanhNV
 */
package com.luvina.la.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * Lớp đại diện cho thực thể EmployeesCertification (Chứng chỉ của nhân viên) trong cơ sở dữ liệu.
 * Sử dụng @Entity để chỉ định rằng lớp này là một thực thể JPA.
 * Sử dụng @Table để xác định bảng trong cơ sở dữ liệu mà lớp này tương ứng.
 * Sử dụng @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor từ Lombok để tự động sinh các phương thức getter, setter,
 * cũng như các constructor không tham số và có tham số.
 */
@Entity
@Table(name = "employees_certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesCertification implements Serializable {

    private static final long serialVersionUID = 5139833965326436815L; // Serial version UID cho tính tương thích

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động sinh ID cho chứng chỉ của nhân viên
    @Column(name = "employee_certification_id", unique = true, nullable = false) // Cột ID của chứng chỉ nhân viên
    private Long employeeCertificationId;

    @ManyToOne // Mối quan hệ nhiều-1 với Employee
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id") // Cột ID nhân viên
    @JsonIgnore // Bỏ qua việc tuần tự hóa thuộc tính này
    @JsonBackReference // Tham chiếu ngược trong mối quan hệ
    private Employee employee; // Nhân viên liên kết với chứng chỉ

    @ManyToOne // Mối quan hệ nhiều-1 với Certification
    @JoinColumn(name = "certification_id", referencedColumnName = "certification_id") // Cột ID chứng chỉ
    @JsonBackReference // Tham chiếu ngược trong mối quan hệ
    private Certification certification; // Chứng chỉ liên kết với nhân viên

    @Column(name = "start_date", nullable = false) // Ngày bắt đầu chứng chỉ
    private Date startDate;

    @Column(name = "end_date", nullable = false) // Ngày kết thúc chứng chỉ
    private Date endDate;

    @Column(name = "score", nullable = false) // Điểm của nhân viên trong chứng chỉ
    private Double score;

}
