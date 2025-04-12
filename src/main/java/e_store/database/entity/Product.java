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

    @ManyToMany(mappedBy = "productsLst")
    private List<Order> ordersLst = new ArrayList<>();


    public Product() {
    }

    public Product(Long id,
                   String name,
                   String description,
                   BigDecimal price,
                   List<Order> ordersLst) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.ordersLst = ordersLst;
    }

    public List<Order> getOrdersLst() {
        return ordersLst;
    }

    public void setOrdersLst(List<Order> ordersLst) {
        this.ordersLst = ordersLst;
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
