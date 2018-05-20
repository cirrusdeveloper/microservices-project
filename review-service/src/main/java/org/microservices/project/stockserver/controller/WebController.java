package org.microservices.project.stockserver.controller;

import org.microservices.project.stockserver.dto.ReviewDTO;
import org.microservices.project.stockserver.entity.Review;
import org.microservices.project.stockserver.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Review Server REST controller.
 * Review CRUD operations.
 * @author mcaleerj
 */
@RestController
public class WebController {

    protected Logger logger = Logger.getLogger(WebController.class
            .getName());

    protected ReviewRepository reviewRepository;

    @Autowired
    public WebController(final ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @RequestMapping("/reviews/{productId}")
    @PreAuthorize("hasAuthority('READ')") //only users with READ authority may read reviews
    public Set<ReviewDTO> getReviews(@PathVariable("productId") long productId) {
        final Set<ReviewDTO> matching = new HashSet<>();
        Set<Review> reviews = this.reviewRepository.findByProductId(productId);
        for(Review review : reviews) {
            final ReviewDTO dto = new ReviewDTO();
            dto.setId(review.getId());
            dto.setText(review.getText());
            dto.setRating(review.getRating());
            dto.setMaxRating(review.getMaxRating());
            dto.setProductId(productId);
            matching.add(dto);
        }
        return matching;
    }

    @PostMapping("/reviews/{productId}")
    @PreAuthorize("hasAuthority('WRITE')") //only users with WRITE authority may add reviews
    public ResponseEntity<String> addReview(@PathVariable("productId") long productId,
            @RequestBody ReviewDTO reviewDTO) {
        final Review review = new Review();
        review.setText(reviewDTO.getText());
        review.setProductId(productId);
        review.setRating(reviewDTO.getRating());
        review.setMaxRating(reviewDTO.getMaxRating());
        this.reviewRepository.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/reviews/{reviewId}")
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")  //only users with ADMIN authority may delete reviews
    public ResponseEntity<String> deleteReview(@PathVariable("reviewId") long reviewId) {
        this.reviewRepository.deleteById(reviewId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
