package org.microservices.project.stockserver.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Persistable Review entity.
 * @author mcaleerj
 */
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="reviewtext")
    private String text;

    @NotNull
    private Long productId;

    private int rating;
    private int maxRating;

    public Review(){
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(final Long productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(final int rating) {
        this.rating = rating;
    }

    public int getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(final int maxRating) {
        this.maxRating = maxRating;
    }
}

