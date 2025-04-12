package e_store.database.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "s_product_order")
public class ProductOrder {

    @EmbeddedId
    private ProductOrderPK productOrderPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @MapsId("orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @MapsId("productId")
    private Product product;

    private Long quantity;


    public ProductOrder() {
    }

    public ProductOrder(ProductOrderPK productOrderPK,
                        Order order,
                        Product product,
                        Long quantity) {
        this.productOrderPK = productOrderPK;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public ProductOrderPK getProductOrderPK() {
        return productOrderPK;
    }

    public void setProductOrderPK(ProductOrderPK productOrderPK) {
        this.productOrderPK = productOrderPK;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
