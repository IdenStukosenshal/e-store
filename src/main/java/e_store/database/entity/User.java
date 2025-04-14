package e_store.database.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "s_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> ordersLst = new ArrayList<>();

    public void addOrder(Order order){
        this.ordersLst.add(order);
        order.setUser(this);
    }




    public User() {
    }

    public User(Long id,
                String firstName,
                String lastName,
                String email,
                List<Order> ordersLst) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.ordersLst = ordersLst;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrdersLst() {
        return ordersLst;
    }

    public void setOrdersLst(List<Order> ordersLst) {
        this.ordersLst = ordersLst;
    }
}
