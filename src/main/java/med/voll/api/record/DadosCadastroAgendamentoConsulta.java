package med.voll.api.record;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

public record DadosCadastroAgendamentoConsulta(

        @NotNull
        Long pacienteId,
        Long medicoId,
        @NotNull
        LocalDateTime dataHoraConsulta,
        @NotNull
        LocalDateTime dataHoraFimConsulta


) {
}
