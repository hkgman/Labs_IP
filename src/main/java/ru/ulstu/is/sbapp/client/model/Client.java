package ru.ulstu.is.sbapp.client.model;

import jakarta.persistence.*;
import ru.ulstu.is.sbapp.purchase.model.Purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column()
    private String firstName;
    private String lastName;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "client",cascade = CascadeType.REMOVE)
    private List<Purchase> purchases;

    public Client() {
    }

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.purchases = new ArrayList<>();
    }

    public void setPurchase(Purchase purchase) {
        if(purchase==null)
        {
            throw new IllegalArgumentException("Да");
        }
        this.purchases.add(purchase);
    }

    public Long getId() {
        return id;
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

    public  List<Purchase> getPurchases()
    {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases)
    {
        this.purchases=purchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
