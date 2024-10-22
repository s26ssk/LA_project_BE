package com.luvina.la.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

@Entity
@Table(name = "certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Certification implements Serializable {

    private static final long serialVersionUID = 349856938003329390L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_id", unique = true, nullable = false)
    private Long certificationId;

    @Column(name = "certification_name", nullable = false, length = 50)
    private String certificationName;

    @Column(name = "certification_level", nullable = false)
    private Integer certificationLevel;

    @OneToMany(mappedBy = "certification", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<EmployeesCertification> employeesCertifications;


}
