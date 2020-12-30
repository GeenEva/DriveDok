package nl.conspect.drivedok.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password; // TODO: 02/12/2020 should probably not be a String but a Char[]
    @OneToMany
    private Set<Vehicle> vehicles;

    public User() {
    }

    public User(String name, String email, String password, Set<Vehicle> vehicles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.vehicles = vehicles;
    }

    public User(Long id, String name, String email, String password, Set<Vehicle> vehicles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.vehicles = vehicles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                email.equals(that.email) &&
                password.equals(that.password) &&
                vehicles.equals(that.vehicles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, vehicles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", vehicles=" + vehicles +
                '}';
    }
}
