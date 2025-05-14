package e_store.database.entity;

import e_store.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "s_order")
@EntityListeners({AuditingEntityListener.class})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @NotNull
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NotNull
    private BigDecimal orderCost;

    @CreatedDate
    @NotNull
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    private LocalDateTime deliveryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    @NotNull
    private Address address;

    @NotNull
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProductLst = new ArrayList<>();

    public void addProduct(Product product, Integer quantity) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setQuantity(quantity);
        this.orderProductLst.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public void removeProduct() {//TODO неплохо было бы реализовать

    }


    public Order() {
    }

    public Order(Long id,
                 String orderNumber,
                 User user,
                 OrderStatus status,
                 BigDecimal orderCost,
                 Instant createdAt,
                 Instant updatedAt,
                 LocalDateTime deliveryDate,
                 Address address,
                 List<OrderProduct> orderProductLst) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.user = user;
        this.status = status;
        this.orderCost = orderCost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deliveryDate = deliveryDate;
        this.address = address;
        this.orderProductLst = orderProductLst;
    }

    public void setUser(User user) {
        this.user = user;
        this.user.getOrdersLst().add(this);
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public List<OrderProduct> getOrderProductLst() {
        return orderProductLst;
    }

    public void setOrderProductLst(List<OrderProduct> orderProductLst) {
        this.orderProductLst = orderProductLst;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) && Objects.equals(getOrderNumber(), order.getOrderNumber()) && Objects.equals(getUser(), order.getUser()) && Objects.equals(getOrderCost(), order.getOrderCost()) && Objects.equals(getCreatedAt(), order.getCreatedAt()) && Objects.equals(getAddress(), order.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrderNumber(), getUser(), getOrderCost(), getCreatedAt(), getAddress());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", user=" + user +
                ", status=" + status +
                ", orderCost=" + orderCost +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deliveryDate=" + deliveryDate +
                ", address=" + address +
                '}';
    }
}
