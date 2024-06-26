CREATE TABLE public.message_sessao_votacao
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY (INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    sessao_votacao_id BIGINT                                                                                                                    NOT NULL,
    total             INTEGER DEFAULT 0,
    CONSTRAINT message_sessao_votacao_pkey PRIMARY KEY (id),
    CONSTRAINT fk_sessao_votacao_id_to_sessao FOREIGN KEY (sessao_votacao_id) REFERENCES public.sessao_votacao (id),
    CONSTRAINT message_sessao_votacao_sessao_votacao_id_key UNIQUE (sessao_votacao_id)
);