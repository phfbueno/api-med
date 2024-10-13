package med.voll.api.repository;

import med.voll.api.model.AgendamentoConsulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendamentoConsultaRepository extends JpaRepository<AgendamentoConsulta, Long> {
    Page<AgendamentoConsulta> findAllByAtivoTrue(Pageable paginacao);

    boolean existsByPacienteIdAndDataHoraConsultaBetween(Long pacienteId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    boolean existsByMedicoIdAndDataHoraConsultaBetween(Long medicoId, LocalDateTime dataHoraAgendamento, LocalDateTime dataHoraFimConsulta);
}
