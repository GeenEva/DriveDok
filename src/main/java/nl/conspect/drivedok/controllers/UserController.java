package nl.conspect.drivedok.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

import static nl.conspect.drivedok.model.ParkingType.possibleTypes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listPage(Model model) {
        final List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "userlistpage";
    }

    @PostMapping
    public String save(Model model, @ModelAttribute @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "usereditpage";
        }
        userService.save(user);
        model.addAttribute("users", userService.findAll());
        return "userlistpage";
    }

    @GetMapping("/new")
    public String addNewUserPage(Model model) {
        model.addAttribute("user", new User());
        return "usereditpage";
    }

    @GetMapping("/{id}")
    public String editPage(Model model, @PathVariable Long id) {
        final var user = userService.getById(id);
        model.addAttribute("user", user);
        return "usereditpage";
    }

    @DeleteMapping("/{id}")
    public String delete(Model model, @PathVariable Long id) {
        userService.findById(id).ifPresent(userService::delete);
        model.addAttribute("users", userService.findAll());
        return "userlistpage";
    }

    @GetMapping("/{userId}/vehicles/new")
    public String addNewVehicleToUserPage(Model model, @PathVariable Long userId) {
        model.addAttribute("user", userService.getById(userId));
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("parkingTypes", possibleTypes());
        return "vehicleeditpage";
    }

    @PostMapping("/{userId}/vehicles")
    public String addVehicleToUser(Model model,
                                   @PathVariable Long userId,
                                   @ModelAttribute @Valid Vehicle vehicle,
                                   BindingResult bindingResult) {
        var user = userService.getById(userId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("parkingTypes", possibleTypes());
            return "vehicleeditpage";
        }
        user = userService.addVehicleByUserId(userId, vehicle);
        model.addAttribute("user", user);
        return "usereditpage";
    }
}
