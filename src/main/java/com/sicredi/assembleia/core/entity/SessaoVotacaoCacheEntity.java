package com.sicredi.assembleia.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RedisHash("SessaoVotacaoCache")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessaoVotacaoCacheEntity {

    //Mesmo id sessaoVotacao
    @Id
    private Long id;

    private Long pautaId;

    private ZonedDateTime horaAbertura = ZonedDateTime.now();

    private ZonedDateTime horaEncerramento = ZonedDateTime.now();

    private List<String> associadosCpfs = new ArrayList<>();

    private Integer total = 0;
    @TimeToLive
    private Long ttl = 86400L;
}
