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

@Entity
@Table(name = "employees_certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesCertification implements Serializable {

    private static final long serialVersionUID = 5139833965326436815L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_certification_id", unique = true, nullable = false)
    private Long employeeCertificationId;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @JsonIgnore
    @JsonBackReference
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "certification_id", referencedColumnName = "certification_id")
    @JsonBackReference
    private Certification certification;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "score", nullable = false)
    private Double score;

}
