package product.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import product.application.domain.Product;

import javax.persistence.*;

@Entity
@Table
public class ProductDetail {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;
    private String description;
    private String comment;

    // Build a one-to-onne relationship between Product and ProductDetail.
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "productDetail_id")
    @JsonIgnore
    private Product product;

    public ProductDetail(Long id, String description, String comment) {
        this.id = id;
        this.description = description;
        this.comment = comment;
    }

    public ProductDetail(String description, String comment) {
        this.description = description;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", comment='" + comment + '\'' +
                ", product=" + product +
                '}';
    }
}
