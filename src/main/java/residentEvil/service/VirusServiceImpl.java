package residentEvil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import residentEvil.domain.entity.BaseEntity;
import residentEvil.domain.entity.Capital;
import residentEvil.domain.entity.Virus;
import residentEvil.domain.model.service.VirusServiceModel;
import residentEvil.domain.model.service.VirusViewServiceModel;
import residentEvil.repository.CapitalRepository;
import residentEvil.repository.VirusRepository;

import java.time.LocalDate;
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

    @Override
    public VirusServiceModel editVirus(VirusServiceModel virusServiceModel) {
        Virus virus = virusRepository.findById(virusServiceModel.getId()).orElse(null);

        if (virus == null) return null;

        virus.getCapitals().stream().filter(capital ->
                !virusServiceModel.getCapitals().contains(capital.getId()))
                .forEach(capital -> virusRepository.removeByCapitalsId(virus.getId(), capital.getId()));

        for (Capital capital : virus.getCapitals()) {
            virusServiceModel.getCapitals().remove(capital.getId());
        }

        List<String> capitalId = virusServiceModel.getCapitals();

        LocalDate releasedOn = virus.getReleasedOn();

        modelMapper.map(virusServiceModel, virus);

        virus.setReleasedOn(releasedOn);
        virus.setCapitals(new ArrayList<>());

        virus.getCapitals().addAll(
                capitalId.stream()
                        .map(capital -> capitalRepository.findById(capital).orElse(null))
                        .collect(Collectors.toList())
        );

        virusRepository.saveAndFlush(virus);

        return modelMapper.map(virus, VirusServiceModel.class);
    }
}
