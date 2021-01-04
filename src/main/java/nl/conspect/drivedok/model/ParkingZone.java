package nl.conspect.drivedok.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
public class ParkingZone {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min=3, message = "Your name should at least have 3 letters")
    @Size(max = 20, message = "Your name can not have more than 20 letters")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<ParkingSpot> parkingSpots = new LinkedHashSet<>();

    @NotNull
    @Min(value = 1, message = "Your zone should have at least 1 parking spot")
    private int totalParkingSpots;

    public ParkingZone() {
    }

    public ParkingZone(String name, Set<ParkingSpot> parkingSpots, int totalParkingSpots) {
        this.name = name;
        this.parkingSpots = parkingSpots;
        this.totalParkingSpots = totalParkingSpots;
    }

    public ParkingZone(Long id, String name, Set<ParkingSpot> parkingSpots, int totalParkingSpots) {
        this.id = id;
        this.name = name;
        this.parkingSpots = parkingSpots;
        this.totalParkingSpots = totalParkingSpots;
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

    public Set<ParkingSpot> getParkingSpots() {
        return Collections.unmodifiableSet(parkingSpots);
    }

    public void addParkingSpot(ParkingSpot parkingSpot){
        parkingSpots.add(parkingSpot);
    }

    public int getTotalParkingSpots() {
        return totalParkingSpots;
    }

    public void setTotalParkingSpots(int totalParkingSpots) {
        this.totalParkingSpots = totalParkingSpots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingZone that = (ParkingZone) o;
        return totalParkingSpots == that.totalParkingSpots && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(parkingSpots, that.parkingSpots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parkingSpots, totalParkingSpots);
    }

    @Override
    public String toString() {
        return "ParkingZone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parkingSpots=" + parkingSpots +
                ", totalParkingSpots=" + totalParkingSpots +
                '}';
    }
}
