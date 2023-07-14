package ec.pichincha.challenge.infraestructure.adapter.out.external.livecoin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseLiveCoinWatch {

    private DataLiveCoin data;

}
