package ru.ulstu.is.sbapp.User.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.User.model.UserDto;
import ru.ulstu.is.sbapp.User.service.UserService;

@Controller
@RequestMapping("/user")
public class UserMvcController {
    private final UserService userService;
    public UserMvcController(UserService userService)
    {
        this.userService=userService;
    }
    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users",
                userService.findAllUsers().stream()
                        .map(UserDto::new)
                        .toList());
        return "user";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editUser(@PathVariable(required = false) Long id,
                              Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("userDto", new UserDto());
        } else {
            model.addAttribute("userId", id);
            model.addAttribute("userDto", new UserDto(userService.findUser(id)));
        }
        return "user-edit";
    }

    @PostMapping(value = {"/", "/{id}"})
    public String saveUser(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid UserDto userDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "user-edit";
        }
        if (id == null || id <= 0) {
            userService.addUser(userDto.getFirstName(), userDto.getLastName(),userDto.getEmail());
        } else {
            userService.updateUser(id, userDto.getFirstName(), userDto.getLastName(),userDto.getEmail());
        }
        return "redirect:/user";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/user";
    }
}
