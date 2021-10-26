package com.viasoft.viasoftbackend.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "services_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NfModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uf")
    private String uf;

    @Column(name = "status")
    private boolean status;

    @Column(name = "verification_date")
    @CreationTimestamp
    private LocalDateTime verificationTime;
}
