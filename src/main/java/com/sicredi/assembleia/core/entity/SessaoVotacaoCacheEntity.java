package com.sicredi.assembleia.core.entity;

import com.sicredi.assembleia.core.factory.SetCpfFactory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.ZonedDateTime;
import java.util.Set;

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

    private Set<Long> associadosCpfs = SetCpfFactory.create();

    private Integer total = 0;

    @TimeToLive
    private Long ttl = 86400L;
}
