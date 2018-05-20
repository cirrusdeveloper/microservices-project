package org.microservices.project.stockserver.repository;

import org.microservices.project.stockserver.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository interface for persistence of products.
 * @author mcaleerj
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long productId);
}
