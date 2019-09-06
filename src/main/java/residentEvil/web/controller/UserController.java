package residentEvil.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import residentEvil.domain.model.binding.UserRegisterBindingModel;
import residentEvil.domain.model.service.RoleServiceModel;
import residentEvil.domain.model.service.UserServiceModel;
import residentEvil.domain.model.view.UserDetailsViewModel;
import residentEvil.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController extends BaseController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public UserController(ModelMapper modelMapper,
                          UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    private List<UserDetailsViewModel> gettingAllUsersInfo() {
        return userService.findAllUsers().stream()
                .map(user -> {
                    UserDetailsViewModel userModel = modelMapper.map(user, UserDetailsViewModel.class);

                    Set<String> roles = user.getAuthorities().stream()
                            .map(RoleServiceModel::getAuthority)
                            .collect(Collectors.toSet());

                    userModel.setAuthorities(roles);
                    return userModel;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.setViewName("user/register");

        return modelAndView;
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(ModelAndView modelAndView,
                                 @ModelAttribute(name = "registerModel")UserRegisterBindingModel registerModel) {
        if (!registerModel.getPassword().equals(registerModel.getConfirmPassword())) { return view("user/register"); }

        if (! userService.registerUser(modelMapper.map(registerModel, UserServiceModel.class))) {
            return view("user/register");
        }

        return redirect("login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("user/login");

        return modelAndView;
    }

    @GetMapping("/users/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allUsers(ModelAndView modelAndView) {
        modelAndView.addObject("users", gettingAllUsersInfo());

        return view("user/all-users", modelAndView);
    }



}
