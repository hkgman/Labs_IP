package ru.ulstu.is.sbapp.User.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ulstu.is.sbapp.Comment.model.CommentDto;
import ru.ulstu.is.sbapp.Post.model.PostDto;
import ru.ulstu.is.sbapp.User.model.User;
import ru.ulstu.is.sbapp.User.model.UserDto;
import ru.ulstu.is.sbapp.User.model.UserRole;
import ru.ulstu.is.sbapp.User.service.UserService;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/user")
public class UserMvcController {
    private final UserService userService;
    public UserMvcController(UserService userService)
    {
        this.userService=userService;
    }
    @GetMapping(value = "/all")
    @Secured({UserRole.AsString.ADMIN})
    public String getUsers(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int size,
                           Principal principal, Model model) {
        final Page<UserDto> users = userService.findAllPages(page, size)
                .map(UserDto::new);
        model.addAttribute("users", users);
        final int totalPages = users.getTotalPages();
        final List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .toList();
        model.addAttribute("pages", pageNumbers);
        model.addAttribute("totalPages", totalPages);
        return "users";
    }
    @GetMapping
    public String showUpdateUserForm(Principal principal, Model model) {
        UserDto userDto = new UserDto(userService.findByLogin(principal.getName()));
        Long id = userService.findByLogin(principal.getName()).getId();
        model.addAttribute("userId",id);
        model.addAttribute("userDto", userDto);
        model.addAttribute("posts",userService.GetUserPosts(id).stream()
                .map(PostDto::new)
                .toList());
        return "user";
    }

    @PostMapping
    public String updateUser(@ModelAttribute @Valid UserDto userDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "user";
        }
        try {
            userService.updateUser(userDto);
            @SuppressWarnings("unchecked")
            Collection<SimpleGrantedAuthority> nowAuthorities =
                    (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext()
                            .getAuthentication()
                            .getAuthorities();
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDto.getLogin(), userDto.getPassword(), nowAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ValidationException e) {
            model.addAttribute("errors", e.getMessage());
        }
        return "user";
    }

    @GetMapping("/post")
    public String showCreatePostInfo(Principal principal, Model model) {
        Long id = userService.findByLogin(principal.getName()).getId();
        model.addAttribute("userId",id);
        model.addAttribute("postDto", new PostDto());
        return "post-create";
    }
    @PostMapping("/createPost/{userId}")
    public String createPost(@PathVariable(value = "userId") Long id,
                             @ModelAttribute @Valid PostDto postDto,
                             @RequestParam(value = "multipartFile") MultipartFile multipartFile,
                             BindingResult bindingResult,
                             Model model) throws IOException
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "post-create";
        }
        postDto.setImage("data:" + multipartFile.getContentType() + ";base64," + Base64.getEncoder().encodeToString(multipartFile.getBytes()));
        userService.addNewPost(id,postDto);
        return "redirect:/user";
    }
    @PostMapping("/delete/{id}")
    @Secured({UserRole.AsString.ADMIN})
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/user";
    }
}
