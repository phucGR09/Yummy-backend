package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
