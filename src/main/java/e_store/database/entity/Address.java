package e_store.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "s_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @NotNull
    private String city;

    @NotNull
    private String streetAddress;

    public Address() {
    }

    public Address(Long id,
                   String city,
                   String streetAddress) {
        this.id = id;
        this.city = city;
        this.streetAddress = streetAddress;
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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreetAddress(), address.getStreetAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreetAddress());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                '}';
    }
}
