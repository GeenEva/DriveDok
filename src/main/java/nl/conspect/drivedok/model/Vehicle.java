package nl.conspect.drivedok.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Vehicle extends AbstractPersistable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 1, max = 64, message = "Name should be between 1 and 64 characters")
    @Column(nullable = false, length = 64)
    private String name;

    @NotNull
    @Size(min = 6, max = 8, message = "Licence plate should be between 6 and 8 characters")
    @Column(nullable = false, length = 8)
    private String licencePlate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ParkingType parkingType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    public Vehicle() {
    }

    public Vehicle(String name, String licencePlate, ParkingType parkingType) {
        this.name = name;
        this.licencePlate = licencePlate;
        this.parkingType = parkingType;
    }

    public Vehicle(String name, String licencePlate, ParkingType parkingType, User user) {
        this.name = name;
        this.licencePlate = licencePlate;
        this.parkingType = parkingType;
        this.user = user;
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

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public ParkingType getParkingType() {
        return parkingType;
    }

    public void setParkingType(ParkingType parkingType) {
        this.parkingType = parkingType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
