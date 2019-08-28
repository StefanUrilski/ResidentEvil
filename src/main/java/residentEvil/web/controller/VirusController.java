package residentEvil.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import residentEvil.domain.model.binding.VirusAddBindingModel;
import residentEvil.domain.model.service.VirusServiceModel;
import residentEvil.domain.model.view.CapitalViewModel;
import residentEvil.service.CapitalService;
import residentEvil.service.VirusService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {

    private final ModelMapper modelMapper;
    private final VirusService virusService;
    private final CapitalService capitalService;

    @Autowired
    public VirusController(ModelMapper modelMapper,
                           VirusService virusService,
                           CapitalService capitalService) {
        this.modelMapper = modelMapper;
        this.virusService = virusService;
        this.capitalService = capitalService;
    }

    private List<CapitalViewModel> getAllCapitals() {
        return capitalService.findAllCapital().stream()
                .map(capital -> modelMapper.map(capital, CapitalViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView modelAndView,
                            @ModelAttribute(name = "bindingModel") VirusAddBindingModel bindingModel) {
        modelAndView.addObject("bindingModel", bindingModel);

        modelAndView.addObject("capitals", getAllCapitals());


        return view("add-viruses", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(ModelAndView modelAndView,
                                   @Valid @ModelAttribute(name = "bindingModel") VirusAddBindingModel bindingModel,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("bindingModel", bindingModel);
            modelAndView.addObject("capitals", getAllCapitals());

            return view("add-viruses", modelAndView);
        }

        virusService.saveVirus(modelMapper.map(bindingModel, VirusServiceModel.class));

        return redirect("/");
    }

    @GetMapping("/show")
    public ModelAndView show(ModelAndView modelAndView) {
        modelAndView.addObject("allViruses", virusService.findAllViruses());

//        modelAndView.setViewName("show-viruses");
//        return modelAndView;
        return view("show-viruses", modelAndView);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id,
                             ModelAndView modelAndView,
                             @ModelAttribute(name = "bindingModel") VirusAddBindingModel bindingModel) {
        modelAndView.addObject("bindingModel", bindingModel);

        modelAndView.addObject("capitals", getAllCapitals());
        VirusServiceModel virus = virusService.findVirusById(id);

        modelAndView.addObject("virus", virus);

        return view("edit-viruses", modelAndView);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteConfirm(@PathVariable("id") String id) {
        virusService.removeVirusById(id);

        return redirect("/");
    }
}
