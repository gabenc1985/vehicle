package ec.pichincha.challenge.domain.port.out.external;

import ec.pichincha.challenge.domain.models.external.CarModelExternalModel;
import reactor.core.publisher.Flux;

public interface CarModelConnector {

    Flux<CarModelExternalModel> getCarModelByIdAndModel(String id, String model);
}
