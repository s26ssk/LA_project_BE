package com.luvina.la.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    private static final long serialVersionUID = 2117609759622903107L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", unique = true, nullable = false)
    private Long employeeId;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    @JsonBackReference
    private Department department;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(name = "employee_name_kana")
    private String employeeNameKana;

    @Column(name = "employee_birth_date")
    private Date employeeBirthDate;

    @Column(name = "employee_email", nullable = false)
    private String employeeEmail;

    @Column(name = "employee_telephone", length = 50)
    private String employeeTelephone;

    @Column(name = "employee_login_id", nullable = false, length = 50)
    private String employeeLoginId;

    @Column(name = "employee_login_password", length = 100)
    private String employeeLoginPassword;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonManagedReference
    private Set<EmployeesCertification> employeesCertifications;
}
