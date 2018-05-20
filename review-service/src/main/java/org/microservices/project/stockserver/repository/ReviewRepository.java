package org.microservices.project.stockserver.repository;

import org.microservices.project.stockserver.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * JPA Repository interface for persistence of reviews.
 * @author mcaleerj
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Set<Review> findByProductId(Long productId);
    void deleteById(Long productId);

}
