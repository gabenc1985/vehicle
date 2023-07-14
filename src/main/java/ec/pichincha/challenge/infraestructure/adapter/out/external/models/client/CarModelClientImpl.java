package ec.pichincha.challenge.infraestructure.adapter.out.external.models.client;

import ec.pichincha.challenge.infraestructure.adapter.out.external.models.dto.ResponseCarModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@Component
@Slf4j
public class CarModelClientImpl implements CarModelClient {

    @Value("${spring.car.model.uri}")
    private String uri;

    @Override
    public Flux<ResponseCarModel> getLiveCoinWatchByFilters(String id, String model) {
        WebClient client = WebClient.create();
        return client.get()
                .uri(uri.concat("/").concat(id).concat("/").concat(model))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ResponseCarModel.class)
                .doOnError(throwable -> log.error("Error getLiveCoinWatchByFilters {}", throwable.getMessage()));
    }
}
