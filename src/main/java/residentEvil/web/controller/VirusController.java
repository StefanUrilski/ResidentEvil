package residentEvil.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import residentEvil.domain.model.binding.VirusBindingModel;
import residentEvil.domain.model.service.VirusServiceModel;
import residentEvil.domain.model.view.CapitalDetailsViewModel;
import residentEvil.domain.model.view.CapitalViewModel;
import residentEvil.domain.model.view.VirusViewModel;
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

    private List<CapitalDetailsViewModel> getAllCapitalsWithDetails() {
        return capitalService.findAllCapital().stream()
                .map(capital -> modelMapper.map(capital, CapitalDetailsViewModel.class))
                .collect(Collectors.toList());
    }

    private VirusServiceModel getVirusById(String id) {
        return virusService.findVirusById(id);
    }

    private List<VirusViewModel> getAllViruses() {
        return virusService.findAllViruses().stream()
                .map(virus -> modelMapper.map(virus, VirusViewModel.class))
                .collect(Collectors.toList());
    }

    private void addObjectsInModelAndViewForAdd(ModelAndView modelAndView) {
        modelAndView.addObject("capitals", getAllCapitals());
        // Stupid fix but it works for making generic form.
        modelAndView.addObject("virus", new VirusServiceModel());
    }
    private void addObjectsInModelAndViewForEdit(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.addObject("capitals", getAllCapitals());
        modelAndView.addObject("virus", getVirusById(id));
    }
    private void addObjectsInModelAndViewForShow(ModelAndView modelAndView) {
        modelAndView.addObject("allViruses", getAllViruses());
        modelAndView.addObject("capitalsWithDetails", getAllCapitalsWithDetails());
    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView modelAndView,
                            @ModelAttribute(name = "bindingModel") VirusBindingModel bindingModel) {
        addObjectsInModelAndViewForAdd(modelAndView);

        return view("add-viruses", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(ModelAndView modelAndView,
                                   @Valid @ModelAttribute(name = "bindingModel") VirusBindingModel bindingModel,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            addObjectsInModelAndViewForAdd(modelAndView);

            return view("add-viruses", modelAndView);
        }
        virusService.saveVirus(modelMapper.map(bindingModel, VirusServiceModel.class));

        return redirect("/");
    }

    @GetMapping("/show")
    public ModelAndView show(ModelAndView modelAndView) {
        addObjectsInModelAndViewForShow(modelAndView);

        return view("show-viruses", modelAndView);
    }



    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id,
                             ModelAndView modelAndView,
                             @ModelAttribute(name = "bindingModel") VirusBindingModel bindingModel) {
        addObjectsInModelAndViewForEdit(id, modelAndView);

        return view("edit-viruses", modelAndView);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editConfirm(@PathVariable("id") String id,
                                    ModelAndView modelAndView,
                                   @Valid @ModelAttribute(name = "bindingModel") VirusBindingModel bindingModel,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            addObjectsInModelAndViewForEdit(id, modelAndView);

            return view("edit-viruses", modelAndView);
        }

        bindingModel.setId(id);
        virusService.editVirus(modelMapper.map(bindingModel, VirusServiceModel.class));

        return redirect("/");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteConfirm(@PathVariable("id") String id) {
        virusService.removeVirusById(id);

        return redirect("/");
    }
}
