package e_store.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "s_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="address_id")
    private Long id;

    @NotNull
    private String city;

    @NotNull
    private String postalCode;

    @NotNull
    private String streetAddress;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY) //когда есть поле в классе можно использовать (mappedBy = "поле")
    private List<Order> ordersLst = new ArrayList<>();

    /*
    public void addOrder(Order order){
        this.ordersLst.add(order);
        order.setAddress(this);
    }

     */


    public Address() {
    }

    public Address(Long id,
                   String city,
                   String postalCode,
                   String streetAddress,
                   List<Order> ordersLst) {
        this.id = id;
        this.city = city;
        this.postalCode = postalCode;
        this.streetAddress = streetAddress;
        this.ordersLst = ordersLst;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getId(), address.getId()) && Objects.equals(getCity(), address.getCity()) && Objects.equals(getPostalCode(), address.getPostalCode()) && Objects.equals(getStreetAddress(), address.getStreetAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCity(), getPostalCode(), getStreetAddress());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                '}';
    }
}
