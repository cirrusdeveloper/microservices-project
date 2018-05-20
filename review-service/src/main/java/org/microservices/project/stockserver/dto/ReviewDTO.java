package org.microservices.project.stockserver.dto;

/**
 * Review Data Transfer Object.
 * @author mcaleerj
 */
public class ReviewDTO {

    private Long id;
    private String text;
    private Long productId;
    private int rating;
    private int maxRating;

    public ReviewDTO() {
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

    public void setText(final String name) {
        this.text = name;
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

