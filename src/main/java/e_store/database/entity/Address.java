package e_store.database.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "s_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "store_name")
    private String storeName;

    private String city;

    @Column(name = "street_address")
    private String streetAddress;

    @OneToMany(mappedBy = "address")
    private List<Order> ordersLst;


    public Address() {
    }

    public Address(Long id, String storeName, String city, String streetAddress, List<Order> ordersLst) {
        this.id = id;
        this.storeName = storeName;
        this.city = city;
        this.streetAddress = streetAddress;
        this.ordersLst = ordersLst;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public List<Order> getOrdersLst() {
        return ordersLst;
    }

    public void setOrdersLst(List<Order> ordersLst) {
        this.ordersLst = ordersLst;
    }
}
