package com.sicredi.assembleia.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VotoEnum votoEnum = VotoEnum.NAO;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "associado_id")
    private AssociadoEntity associado = new AssociadoEntity();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sessao_id")
    @JsonIgnore
    private SessaoVotacaoEntity sessao = new SessaoVotacaoEntity();
}
