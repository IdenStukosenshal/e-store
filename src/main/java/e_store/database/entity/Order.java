package e_store.database.entity;

import e_store.enums.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "s_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_cost")
    private BigDecimal orderCost;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;


    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<ProductOrder> productOrderLst = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "s_product_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> productsLst = new ArrayList<>();


    public Order() {
    }

    public Order(Long id,
                 User user,
                 OrderStatus status,
                 BigDecimal orderCost,
                 LocalDateTime orderDate,
                 Address address,
                 List<ProductOrder> productOrderLst,
                 List<Product> productsLst) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.orderCost = orderCost;
        this.orderDate = orderDate;
        this.address = address;
        this.productOrderLst = productOrderLst;
        this.productsLst = productsLst;
    }

    public List<Product> getProductsLst() {
        return productsLst;
    }

    public void setProductsLst(List<Product> productsLst) {
        this.productsLst = productsLst;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<ProductOrder> getProductOrderLst() {
        return productOrderLst;
    }

    public void setProductOrderLst(List<ProductOrder> productOrderLst) {
        this.productOrderLst = productOrderLst;
    }
}
