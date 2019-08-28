package residentEvil.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import residentEvil.domain.model.service.CapitalServiceModel;
import residentEvil.repository.CapitalRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CapitalServiceImpl implements CapitalService {

    private final ModelMapper modelMapper;
    private final CapitalRepository capitalRepository;

    @Autowired
    public CapitalServiceImpl(ModelMapper modelMapper,
                              CapitalRepository capitalRepository) {
        this.modelMapper = modelMapper;
        this.capitalRepository = capitalRepository;
    }

    @Override
    public List<CapitalServiceModel> findAllCapital() {
        return capitalRepository.findAllOrOrderByName().stream()
                .map(capital -> modelMapper.map(capital, CapitalServiceModel.class))
                .collect(Collectors.toList());
    }
}
