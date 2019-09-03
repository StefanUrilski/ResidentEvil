package residentEvil.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import residentEvil.domain.model.binding.UserRegisterBindingModel;
import residentEvil.domain.model.service.UserServiceModel;
import residentEvil.service.UserService;

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

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.setViewName("register");

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(ModelAndView modelAndView,
                                 @ModelAttribute(name = "registerModel")UserRegisterBindingModel registerModel) {
        if (!registerModel.getPassword().equals(registerModel.getConfirmPassword())) { return redirect("/register"); }

        if (! userService.registerUser(modelMapper.map(registerModel, UserServiceModel.class))) {
            return view("register");
        }

        return redirect("/login");
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");

        return modelAndView;
    }

}
