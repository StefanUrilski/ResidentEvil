package residentEvil.service;

import residentEvil.domain.model.service.VirusServiceModel;
import residentEvil.domain.model.service.VirusViewServiceModel;

import java.util.List;

public interface VirusService {

    void saveVirus(VirusServiceModel virusServiceModel);

    List<VirusViewServiceModel> findAllViruses();

    boolean removeVirusById(String id);

    VirusServiceModel findVirusById(String id);

    VirusServiceModel editVirus(VirusServiceModel virusServiceModel);
}
