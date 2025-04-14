package e_store.database.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "s_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductOrder> productOrderLst = new ArrayList<>();


    public Product() {
    }

    public Product(Long id,
                   String name,
                   String description,
                   BigDecimal price,
                   List<ProductOrder> productOrderLst) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productOrderLst = productOrderLst;
    }

    public List<ProductOrder> getProductOrderLst() {
        return productOrderLst;
    }

    public void setProductOrderLst(List<ProductOrder> productOrderLst) {
        this.productOrderLst = productOrderLst;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
