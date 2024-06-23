package com.sicredi.assembleia.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "sessao_votacao")
public class SessaoVotacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private ZonedDateTime horaAbertura = ZonedDateTime.now();

    @Column(nullable = false)
    private ZonedDateTime horaEncerramento = ZonedDateTime.now();

    @Column(nullable = false)
    private int duracao = 0;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pauta_id")
    private PautaEntity pauta = new PautaEntity();

    private Integer votosFavor = 0;

    private Integer votosContra = 0;

    private Integer total = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SessaoVotacaoEnum status = SessaoVotacaoEnum.ABERTA;
}
