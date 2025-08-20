package com.intro2se.yummy.repositories;

import com.intro2se.yummy.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
