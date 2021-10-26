package com.viasoft.viasoftbackend.api.repository;

import com.viasoft.viasoftbackend.api.model.NfModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public interface NfRepository extends JpaRepository<NfModel, Integer> {

    @Query(value = "SELECT MAX(id) AS id, uf, status, MAX(verification_date) AS verification_date FROM services_status GROUP BY uf, status ORDER BY verification_date DESC LIMIT 15 ;", nativeQuery = true)
    List<NfModel> findCurrentStatus();

    @Query(value = "SELECT MAX(id) AS id, uf, status, MAX(verification_date) AS verification_date FROM services_status WHERE uf = ?1 GROUP BY uf, status ORDER BY verification_date DESC LIMIT 1;", nativeQuery = true)
    Optional<NfModel> findByUf(String uf);

    @Query(value = " SELECT * FROM services_status WHERE TO_DATE(TEXT(verification_date), 'YYYY-MM-DD') = TO_DATE(?1, 'YYYY-MM-DD') ; ", nativeQuery = true)
    Optional<List<NfModel>> findByVerificationTime(String date);

    @Query(value = "SELECT s.uf\n" +
            "FROM services_status AS s\n" +
            "JOIN (SELECT uf, COUNT(status) FROM services_status WHERE status = false\n" +
            "\t  GROUP BY uf\n" +
            "\t  ORDER BY COUNT(status) DESC\n" +
            "\t  LIMIT 1) AS t ON t.uf = s.uf LIMIT 1;", nativeQuery = true)
    Optional<String> findUfWithMoreIndisponibility();
}
