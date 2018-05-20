package org.microservices.project.stockserver.dto;

/**
 * Product Category Data Transfer Object.
 * @author mcaleerj
 */
public class ProductCategoryDTO {

    private Long id;
    private String name;

    public ProductCategoryDTO() {
    }

    public ProductCategoryDTO(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public ProductCategoryDTO(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
