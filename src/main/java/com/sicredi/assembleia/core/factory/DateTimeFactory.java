package com.sicredi.assembleia.core.factory;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeFactory {
    public static ZonedDateTime now (){
        Instant instant = Instant.now(); // Obtém o instante atual
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo"); // Define o fuso horário
        return ZonedDateTime.ofInstant(instant, zoneId);
    }
}
