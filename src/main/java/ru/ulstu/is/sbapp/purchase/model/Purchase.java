package ru.ulstu.is.sbapp.purchase.model;

import jakarta.persistence.*;
import ru.ulstu.is.sbapp.client.model.Client;
import ru.ulstu.is.sbapp.technique.model.Technique;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    Date DateOfPurchase;

    double Price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="client_fk")
    private Client client;

    @ManyToMany
    @JoinTable(name = "purchases_techniques",
    joinColumns = @JoinColumn(name = "purchase_fk"),
    inverseJoinColumns = @JoinColumn(name = "technique_fk"))
    private List<Technique> techniques;


    public Purchase()
    {

    }

    public Purchase(Date DateOfPurchase,double Price)
    {
        this.DateOfPurchase=DateOfPurchase;
        this.Price = Price;
    }
    public List<Technique> getTechnique() {
        return techniques;
    }
    public void setTechnique(Technique technique) {
        if (techniques == null){
            techniques = new ArrayList<>();
        }
        this.techniques.add(technique);
        if (!technique.getPurchases().contains(this)) { // warning this may cause performance issues if you have a large data set since this operation is O(n)
            technique.getPurchases().add(this);
        }
    }
    public Long getId()
    {
        return id;
    }
    public Date getDateOfPurchase()
    {
        return  DateOfPurchase;
    }
    public void setDateOfPurchase(Date DateOfPurchase)
    {
        this.DateOfPurchase=DateOfPurchase;
    }
    public double getPrice()
    {
        return Price;
    }
    public  void setPrice(Float Price)
    {
        this.Price=Price;
    }
    public Client getClient()
    {
        return client;
    }
    public void setClient(Client client)
    {
        this.client=client;
        if (!client.getPurchases().contains(this)) {
            client.getPurchases().add(this);
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(id, purchase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", DateOfPurchase='" + DateOfPurchase + '\'' +
                ", Price ='" + Price + '\'' +
                '}';
    }

}
