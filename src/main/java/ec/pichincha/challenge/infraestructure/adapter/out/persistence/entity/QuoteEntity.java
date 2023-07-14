package ec.pichincha.challenge.infraestructure.adapter.out.persistence.entity;

import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("public.quote")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class QuoteEntity {

    private Integer id;

    private String fullName;

    @Column("create_date")
    private LocalDateTime createDate;

    private String model;

    private String version;

    private String cryptocurrency;

    @Column("price_usd")
    private BigDecimal priceUsd;

    private BigDecimal priceCryptocurrency;

    private String purchaseId;


}
