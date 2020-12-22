package nl.conspect.drivedok.services;

import nl.conspect.drivedok.model.ParkingSpot;
import nl.conspect.drivedok.model.ParkingType;
import nl.conspect.drivedok.repositories.ParkingSpotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ParkingSpotServiceTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ParkingSpotRepository parkingSpotRepository;

    ParkingSpotService parkingSpotService;

    @BeforeEach
    public void init() {
        parkingSpotService = new ParkingSpotService(parkingSpotRepository);
    }

    @Test
    @DisplayName("Create 3 ParkingSpots and persist. Expect findAll() to return a list with size 3")
    void findAll() {
        ParkingSpot spot1 = new ParkingSpot(ParkingType.DISABLED);
        ParkingSpot spot2 = new ParkingSpot(ParkingType.ELECTRIC);
        ParkingSpot spot3 = new ParkingSpot(ParkingType.MOTOR);
        testEntityManager.persist(spot1);
        testEntityManager.persist(spot2);
        testEntityManager.persist(spot3);
        assertEquals(3, parkingSpotService.findAll().size());
    }

    @Test
    @DisplayName("Persist two ParkingSpots. Expect findById to retrieve the correct ParkingSpot. Verify by checking the ParkingType")
    void findById() {
        ParkingSpot spot1 = new ParkingSpot(ParkingType.ELECTRIC);
        testEntityManager.persist(spot1);
        ParkingSpot spot2 = new ParkingSpot(ParkingType.MOTOR);
        testEntityManager.persist(spot2);

        ParkingType parkingType = parkingSpotService.findById(spot1.getId())
                .map(ParkingSpot::getParkingType)
                .orElse(null);
        assertEquals(ParkingType.ELECTRIC, parkingType);
        assertNotEquals(ParkingType.MOTOR, parkingType);
    }

    @Test
    @DisplayName("Assert initial findAll() to return empty list. Expect subsequent findAll() to be > 0 after create()")
    void create() {
        assertTrue(parkingSpotService.findAll().isEmpty());

        ParkingSpot spot = new ParkingSpot(ParkingType.DISABLED);
        parkingSpotService.create(spot);
        assertEquals(1, parkingSpotService.findAll().size());
    }

    @Test
    @DisplayName("Persist a ParkingSpot with ParkingType Disabled. Retrieve this and set the type to Electric and call update(). Expect the ParkingType to be Electric after subsequent retrieval")
    void update() {
        ParkingSpot spot = new ParkingSpot(ParkingType.DISABLED);
        testEntityManager.persist(spot);

        ParkingSpot updatedSpot = parkingSpotService.findById(spot.getId()).orElse(null);
        assertNotNull(updatedSpot);
        updatedSpot.setParkingType(ParkingType.ELECTRIC);
        parkingSpotService.update(updatedSpot);

        ParkingSpot checkSpot = parkingSpotService.findById(spot.getId()).orElse(null);
        assertNotNull(checkSpot);
        assertEquals(ParkingType.ELECTRIC, checkSpot.getParkingType());
    }



    @Test
    @DisplayName("Persist 3 ParkingSpots. Delete the second. Expect findAll() to have size 2")
    void deleteById() {
        ParkingSpot spot1 = new ParkingSpot(ParkingType.DISABLED);
        ParkingSpot spot2 = new ParkingSpot(ParkingType.ELECTRIC);
        ParkingSpot spot3 = new ParkingSpot(ParkingType.MOTOR);
        testEntityManager.persist(spot1);
        testEntityManager.persist(spot2);
        testEntityManager.persist(spot3);
        assertEquals(3, parkingSpotService.findAll().size());

        parkingSpotService.deleteById(spot2.getId());
        assertEquals(2, parkingSpotService.findAll().size());
    }
}