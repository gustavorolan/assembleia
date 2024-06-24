CREATE TABLE public.sessao_votacao
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    duracao           int4 NOT NULL,
    total             int4 NULL,
    total_messages    int4 NULL,
    votos_contra      int4 NULL,
    votos_favor       int4 NULL,
    hora_abertura     timestamptz(6) NOT NULL,
    hora_encerramento timestamptz(6) NOT NULL,
    pauta_id          BIGINT NULL,
    status            varchar(255) NOT NULL,
    CONSTRAINT sessao_votacao_pauta_id_key UNIQUE (pauta_id),
    CONSTRAINT sessao_votacao_pkey PRIMARY KEY (id),
    CONSTRAINT sessao_votacao_status_check CHECK (((status)::text = ANY ((ARRAY['ABERTA':: character varying, 'ENCERRADA':: character varying])::text[])
) ),
	CONSTRAINT fk_pauta_id_to_pauta FOREIGN KEY (pauta_id) REFERENCES public.pauta(id)
);