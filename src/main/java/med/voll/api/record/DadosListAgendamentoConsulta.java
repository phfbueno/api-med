package med.voll.api.record;

import med.voll.api.model.AgendamentoConsulta;
import med.voll.api.model.Medico;
import med.voll.api.model.Paciente;

import java.time.LocalDateTime;

public record DadosListAgendamentoConsulta(

        Long id,
        Paciente paciente,
        Medico Medico,
        LocalDateTime dataHoraConsulta,
        LocalDateTime dataHoraFimConsulta
) {
    public DadosListAgendamentoConsulta(AgendamentoConsulta agendamentoConsulta){
        this(

                agendamentoConsulta.getId(),
                agendamentoConsulta.getPaciente(),
                agendamentoConsulta.getMedico(),
                agendamentoConsulta.getDataHoraConsulta(),
                agendamentoConsulta.getDataHoraFimConsulta()
        );
    }
}
