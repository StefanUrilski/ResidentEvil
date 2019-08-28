package residentEvil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.stereotype.Service;
import residentEvil.domain.entity.Virus;
import residentEvil.domain.model.service.VirusServiceModel;
import residentEvil.domain.model.service.VirusViewServiceModel;
import residentEvil.repository.CapitalRepository;
import residentEvil.repository.VirusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirusServiceImpl implements VirusService {

    private final ModelMapper modelMapper;
    private final VirusRepository virusRepository;
    private final CapitalRepository capitalRepository;

    @Autowired
    public VirusServiceImpl(ModelMapper modelMapper,
                            VirusRepository virusRepository,
                            CapitalRepository capitalRepository) {
        this.modelMapper = modelMapper;
        this.virusRepository = virusRepository;
        this.capitalRepository = capitalRepository;
    }

    @Override
    public void saveVirus(VirusServiceModel virusServiceModel) {
        Virus virus = modelMapper.map(virusServiceModel, Virus.class);

        virus.setCapitals(new ArrayList<>());

        virusRepository.saveAndFlush(virus);

        virusServiceModel.getCapitals().stream()
                .map(capital -> capitalRepository.findById(capital).orElse(null))
                .forEach(capital -> virus.getCapitals().add(capital));

        virusRepository.saveAndFlush(virus);
    }

    @Override
    public List<VirusViewServiceModel> findAllViruses() {
        return virusRepository.findAll().stream()
                .map(virus -> modelMapper.map(virus, VirusViewServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeVirusById(String id) {
        try {
            virusRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public VirusServiceModel findVirusById(String id) {
        Virus virus = virusRepository.findById(id).orElse(null);

        if (virus == null) return null;

        VirusServiceModel virusServiceModel = modelMapper.map(virus, VirusServiceModel.class);

        virusServiceModel.setCapitals(new ArrayList<>());

        virus.getCapitals()
                .forEach(capital -> virusServiceModel.getCapitals().add(capital.getName()));

        return virusServiceModel;
    }
}
