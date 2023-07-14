package ec.pichincha.challenge.infraestructure.adapter.out.external.models.client;

import ec.pichincha.challenge.infraestructure.adapter.out.external.models.dto.ResponseCarModel;
import reactor.core.publisher.Flux;

public interface CarModelClient {

    Flux<ResponseCarModel> getLiveCoinWatchByFilters(String id, String model);
}
