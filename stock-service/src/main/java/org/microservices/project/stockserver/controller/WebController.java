package org.microservices.project.stockserver.controller;

import org.microservices.project.stockserver.dto.ProductCategoryDTO;
import org.microservices.project.stockserver.dto.ProductDTO;
import org.microservices.project.stockserver.entity.Product;
import org.microservices.project.stockserver.entity.ProductCategory;
import org.microservices.project.stockserver.repository.ProductCategoryRepository;
import org.microservices.project.stockserver.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

/**
 * Stock Server REST controller.
 * Product/Category CRUD operations.
 * @author mcaleerj
 */
@RestController
public class WebController {

    protected static Logger logger = Logger.getLogger(WebController.class
            .getName());

    protected ProductCategoryRepository categoryRepository;
    protected ProductRepository productRepository;

    @Autowired
    public WebController(final ProductCategoryRepository categoryRepository, final ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @RequestMapping("/categories")
    @PreAuthorize("hasAuthority('READ')") //only users with READ authority may read categories
    public List<ProductCategoryDTO> getCategories() {
        final List<ProductCategoryDTO> categories = new ArrayList<>();
        for(ProductCategory category :this.categoryRepository.findAll()) {
            categories.add(new ProductCategoryDTO(category.getId(), category.getName()));

        }
        return categories;
    }

    @PreAuthorize("hasAuthority('ADMIN')") //only users with ADMIN authority may create categories
    @RequestMapping(path = "/categories", method = RequestMethod.POST)
    public ResponseEntity<?> addCategory(@RequestBody ProductCategoryDTO category) {
        final ProductCategory newCategory = new ProductCategory(category.getName());
        this.categoryRepository.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAuthority('READ')") //only users with READ authority may read products
    @RequestMapping("/categories/{categoryId}/products")
    public Set<ProductDTO> getProductsInCategory(@PathVariable("categoryId") long categoryId) {
        final Set<ProductDTO> products = new HashSet<>();
        Optional<ProductCategory> cat = this.categoryRepository.findById(categoryId);
        if(cat.isPresent()) {
            for(Product product : cat.get().getProducts()) {
                products.add(new ProductDTO(product.getId(), product.getName()));
            }
        }
        return products;
    }

    @PreAuthorize("hasAuthority('ADMIN')") //only users with ADMIN authority may add products
    @RequestMapping(path = "/categories/{categoryId}/products", method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(@PathVariable("categoryId") long categoryId, @RequestBody ProductDTO productDTO) {
        final Optional<ProductCategory> category = this.categoryRepository.findById(categoryId);
        if(category.isPresent()) {
            final Product product = new Product(productDTO.getName());
            product.setCategory(category.get());
            this.productRepository.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
