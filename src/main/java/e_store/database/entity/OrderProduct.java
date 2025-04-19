package e_store.database.entity;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "s_order_product")
public class OrderProduct {

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
        this.order.getOrderProductLst().add(this);
    }

    public void setProduct(Product product) {
        this.product = product;
        this.product.getProductOrderLst().add(this);
    }


    public OrderProduct() {
    }

    public OrderProduct(Long id,
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct that = (OrderProduct) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getOrder(), that.getOrder()) && Objects.equals(getProduct(), that.getProduct()) && Objects.equals(getQuantity(), that.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrder(), getProduct(), getQuantity());
    }
}
