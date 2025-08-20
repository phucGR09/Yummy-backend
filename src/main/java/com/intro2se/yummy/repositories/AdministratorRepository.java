package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
}
