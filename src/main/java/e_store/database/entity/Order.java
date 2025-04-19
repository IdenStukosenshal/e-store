package e_store.database.entity;

import e_store.enums.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProductLst = new ArrayList<>();


    public Order() {
    }

    public Order(Long id,
                 User user,
                 OrderStatus status,
                 BigDecimal orderCost,
                 LocalDateTime orderDate,
                 Address address,
                 List<OrderProduct> orderProductLst) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.orderCost = orderCost;
        this.orderDate = orderDate;
        this.address = address;
        this.orderProductLst = orderProductLst;
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

    public List<OrderProduct> getOrderProductLst() {
        return orderProductLst;
    }

    public void setOrderProductLst(List<OrderProduct> orderProductLst) {
        this.orderProductLst = orderProductLst;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) && Objects.equals(getUser(), order.getUser()) && getStatus() == order.getStatus() && Objects.equals(getOrderCost(), order.getOrderCost()) && Objects.equals(getOrderDate(), order.getOrderDate()) && Objects.equals(getAddress(), order.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getStatus(), getOrderCost(), getOrderDate(), getAddress());
    }
}
