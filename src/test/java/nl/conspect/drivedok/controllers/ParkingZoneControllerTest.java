package nl.conspect.drivedok.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ParkingZoneControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ParkingZoneController parkingZoneController;

    @Test
    public void shouldCreateParkingZone() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/createParkingZone"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}