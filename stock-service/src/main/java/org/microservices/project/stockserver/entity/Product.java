package org.microservices.project.stockserver.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Locale;

/**
 * Persistable Product entity.
 * @author mcaleerj
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

    public Product(){
    }

    public Product(final String name) {
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

    public void setCategory(final ProductCategory category) {
        this.category = category;
    }

    public ProductCategory getCategory() {
        return this.category;
    }

}

