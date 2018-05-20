package org.microservices.project.stockserver.dto;

/**
 * Product Data Transfer Object.
 * @author mcaleerj
 */
public class ProductDTO {

    private Long id;
    private String name;

    public ProductDTO() {
    }

    public ProductDTO(final String name) {
        this.name = name;
    }

    public ProductDTO(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}

