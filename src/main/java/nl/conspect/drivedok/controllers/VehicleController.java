package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.exceptions.VehicleNotFoundException;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.services.VehicleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public String listpage(Model model) {
        model.addAttribute("vehicles", vehicleService.findAll());
        return "vehiclelistpage";
    }

    @PostMapping
    public String createEditpage(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "vehicleeditpage";
    }

    @GetMapping("/{id}")
    public String editpage(Model model, @PathVariable Long id) {
        final var vehicle = vehicleService.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
        model.addAttribute("vehicle", vehicle);
        return "vehicleeditpage";
    }

    @DeleteMapping("/{id}")
    public String deleteAndReturnToListPage(@PathVariable Long id) {
        final var returnpage = vehicleService.pageAfterDelete(id);
        vehicleService.findById(id).ifPresent(vehicleService::delete);
        return returnpage;
    }
}
