package ru.ulstu.is.sbapp.technique.model;

import jakarta.persistence.*;
import ru.ulstu.is.sbapp.purchase.model.Purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Technique {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    String Type;

    double TechPrice;



    @ManyToMany(mappedBy = "techniques",fetch = FetchType.EAGER)
    private List<Purchase> purchases;

    public Technique(){}

    public Technique(String Type,double TechPrice)
    {
        this.Type = Type;
        this.TechPrice=TechPrice;
    }
    public Long getId()
    {
        return id;
    }
    public String getType()
    {
        return  Type;
    }
    public double getTechPrice()
    {
        return TechPrice;
    }
    public void setType(String Type){
        this.Type=Type;
    }
    public void setTechPrice(Float TechPrice)
    {
        this.TechPrice=TechPrice;
    }
    public List<Purchase> getPurchase()
    {
        return purchases;
    }
    public void setPurchase(Purchase purchase) {
        if (purchases == null){
            purchases = new ArrayList<>();
        }
        this.purchases.add(purchase);
        if (!purchase.getTechnique().contains(this)) { // warning this may cause performance issues if you have a large data set since this operation is O(n)
            purchase.getTechnique().add(this);
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Technique technique = (Technique) o;
        return Objects.equals(id, technique.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Technique{" +
                "id=" + id +
                ", Type='" + Type + '\'' +
                ", TechPrice ='" + TechPrice + '\'' +
                '}';
    }
}
