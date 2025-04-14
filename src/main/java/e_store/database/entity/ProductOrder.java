package e_store.database.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "s_order_product")
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long quantity;


    public void setOrder(Order order) {
        this.order = order;
        this.order.getProductOrderLst().add(this);
    }

    public void setProduct(Product product) {
        this.product = product;
        this.product.getProductOrderLst().add(this);
    }


    public ProductOrder() {
    }

    public ProductOrder(Long id,
                        Order order,
                        Product product,
                        Long quantity) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }


    public Product getProduct() {
        return product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
