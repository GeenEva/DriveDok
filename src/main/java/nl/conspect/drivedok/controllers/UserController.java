package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.exceptions.UserNotFoundException;
import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.Vehicle;
import nl.conspect.drivedok.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

import static nl.conspect.drivedok.model.ParkingType.possibleTypes;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* Thymeleaf controllers */
    @GetMapping
    public String listPage(Model model) {
        final List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "userlistpage";
    }

    @GetMapping("/{id}")
    public String editPage(Model model, @PathVariable Long id) {
        final var user = userService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("vehicles", user.getVehicles());
        return "usereditpage";
    }

    @GetMapping("/save")
    public String createPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("vehicles", Collections.emptySet());
        return "usereditpage";
    }

    @PostMapping("/save")
    public String save(Model model, @ModelAttribute User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "usereditpage";
        }
        userService.createOrUpdate(user);
        model.addAttribute("users", userService.findAll());
        return "userlistpage";
    }

    @GetMapping("/{id}/vehicles/save")
    public String vehiclesForUser(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("parkingTypes", possibleTypes());
        return "vehicleeditpage";
    }

    @PostMapping("/{id}/vehicles/save")
    public String addVehicleToUser(Model model, @PathVariable Long id, @ModelAttribute Vehicle vehicle) {
        final var user = userService.addVehicleByUserId(id, vehicle);
        model.addAttribute("user", user);
        model.addAttribute("vehicles", user.getVehicles());
        return "usereditpage";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id) {
        userService.findById(id).ifPresent(userService::delete);
        model.addAttribute("users", userService.findAll());
        return "userlistpage";
    }

    /* Json controllers */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public User findById(@PathVariable Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public User create(@RequestBody User user) {
        return userService.createOrUpdate(user);
    }

    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public User update(@PathVariable Long id, @RequestBody User user) {
        return userService.findById(id)
                .map(u -> userService.createOrUpdate(user))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
