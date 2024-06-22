package com.sicredi.assembleia.factory;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeFactory {
    public static ZonedDateTime criarAbertaura() {
        return ZonedDateTime.of(
                2024, 6, 21,
                12, 0, 0,
                0,
                ZoneId.of("America/Sao_Paulo")
        );
    }

    public static ZonedDateTime criarEncerramento() {
        return ZonedDateTime.of(
                2024, 6, 21,
                13, 0, 0,
                0,
                ZoneId.of("America/Sao_Paulo")
        );
    }

    public static ZonedDateTime criarDataEntreEncerramentoEAbertura() {
        return ZonedDateTime.of(
                2024, 6, 21,
                12, 30, 0,
                0,
                ZoneId.of("America/Sao_Paulo")
        );
    }

    public static ZonedDateTime criarForaDoEncerramentoEAbertura() {
        return ZonedDateTime.of(
                2024, 6, 21,
                15, 0, 0,
                0,
                ZoneId.of("America/Sao_Paulo")
        );
    }
}
