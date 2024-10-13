package med.voll.api.service;

import med.voll.api.model.AgendamentoConsulta;
import med.voll.api.model.Medico;
import med.voll.api.repository.AgendamentoConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AgendamentoConsultaService {

    @Autowired
    private AgendamentoConsultaRepository agendamentoConsultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public boolean isHorarioValido(LocalDateTime dataHora) {
        LocalTime inicioExpediente = LocalTime.of(7, 0);
        LocalTime fimExpediente = LocalTime.of(19, 0);
        DayOfWeek diaDaSemana = dataHora.getDayOfWeek();
        boolean diaUtil = diaDaSemana != DayOfWeek.SUNDAY;
        boolean horarioDentroExpediente = !dataHora.toLocalTime().isBefore(inicioExpediente)
                && !dataHora.toLocalTime().isAfter(fimExpediente);

        return diaUtil && horarioDentroExpediente;
    }

    public boolean validarAgendamento(LocalDateTime dataHoraConsulta) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime dataHoraMinima = agora.plusMinutes(30);

        return dataHoraConsulta.isAfter(dataHoraMinima);
    }

    public boolean validarAgendamentoMesmoDiaPaciente(Long pacienteId, LocalDateTime dataHoraAgendamento) {

        LocalDate dataConsulta = dataHoraAgendamento.toLocalDate();

        boolean existeConsultaMesmoDia = agendamentoConsultaRepository.existsByPacienteIdAndDataHoraConsultaBetween(
                pacienteId,
                dataConsulta.atStartOfDay(),
                dataConsulta.atTime(LocalTime.MAX)
        );

        return existeConsultaMesmoDia;

    }

    public boolean validarAgendamentoMesmaHora(Long medicoId, LocalDateTime dataHoraAgendamento, LocalDateTime dataHoraFimConsulta) {


        boolean existeConsultaMesmoHorario = agendamentoConsultaRepository.existsByMedicoIdAndDataHoraConsultaBetween(
                medicoId,
                dataHoraAgendamento,
                dataHoraFimConsulta
        );

        return existeConsultaMesmoHorario;
    }

    public Long buscarMedicosDisponiveis(LocalDateTime dataHoraAgendamento) {

        List<Medico> todosMedicos = medicoRepository.findAll();
        List<AgendamentoConsulta> todosAgendamentos = agendamentoConsultaRepository.findAll();

        List<Medico> medicosIndisponiveis = todosAgendamentos.stream()
                .map(AgendamentoConsulta::getMedico)
                .collect(Collectors.toList());

        List<Medico> medicosDisponiveis = todosMedicos.stream()
                .filter(medico -> !medicosIndisponiveis.contains(medico.getId()))
                .collect(Collectors.toList());

        if (medicosDisponiveis.isEmpty()) {
            throw new RuntimeException("Não existem médicos disponíveis para esse horário. Por favor, escolha um novo dia ou verifique os médicos.");
        }

        Random random = new Random();
        int indiceAleatorio = random.nextInt(medicosDisponiveis.size());

        return medicosDisponiveis.get(indiceAleatorio).getId();
    }
}
