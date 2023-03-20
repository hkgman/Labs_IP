package ru.ulstu.is.sbapp.client.model;

import jakarta.persistence.*;
import ru.ulstu.is.sbapp.Favourite.model.Favourite;

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

    private String email;

    @OneToOne
    @JoinColumn(name="favourite_id")
    private Favourite favourite;

    public Client() {
    }

    public Client(String firstName, String lastName,String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
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

    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }

    public void setFavourite(Favourite favourite)
    {
        this.favourite=favourite;
    }
    public Favourite getFavourite()
    {
        return favourite;
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
                ", email='" + email + '\'' +
                '}';
    }

}
