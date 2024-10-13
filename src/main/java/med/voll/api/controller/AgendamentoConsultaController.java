package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.model.AgendamentoConsulta;
import med.voll.api.model.Medico;
import med.voll.api.record.DadosCadastroAgendamentoConsulta;
import med.voll.api.record.DadosListAgendamentoConsulta;
import med.voll.api.repository.AgendamentoConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.service.AgendamentoConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
public class AgendamentoConsultaController {

    @Autowired
    private AgendamentoConsultaRepository agendamentoConsultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private AgendamentoConsultaService agendamentoConsultaService;

    @PostMapping
    @Transactional
    public  void cadastrarAgendamento(@RequestBody @Valid DadosCadastroAgendamentoConsulta dados){

        AgendamentoConsulta agendamentoConsulta = new AgendamentoConsulta();

        agendamentoConsulta.setAtivo(true);

        agendamentoConsulta.setDataHoraConsulta(dados.dataHoraConsulta());


        agendamentoConsulta.setPaciente(pacienteRepository.findByIdAndAtivoTrue(dados.pacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado ou inativo")));

        if(dados.medicoId() == null) {
            Long medicoId = agendamentoConsultaService.buscarMedicosDisponiveis(dados.dataHoraConsulta());

            agendamentoConsulta.setMedico(medicoRepository.findByIdAndAtivoTrue(medicoId)
                .orElseThrow(() -> new IllegalArgumentException("Medico não encontrado ou inativo")));
        }

        if (!agendamentoConsultaService.isHorarioValido(agendamentoConsulta.getDataHoraConsulta())) {
            throw new IllegalArgumentException("O horário de agendamento deve ser entre 07:00 e 19:00, de segunda a sábado.");
        }

        if (!agendamentoConsultaService.validarAgendamento(agendamentoConsulta.getDataHoraConsulta())) {
            throw new IllegalArgumentException("A consulta deve ser agendada com pelo menos 30 minutos de antecedência.");
        }

        if(agendamentoConsultaService.validarAgendamentoMesmoDiaPaciente(dados.pacienteId(), dados.dataHoraConsulta())){
            throw new IllegalArgumentException("O paciente já tem uma consulta agendada neste dia.");
        }

        if(agendamentoConsultaService.validarAgendamentoMesmaHora(dados.medicoId(), dados.dataHoraConsulta(), dados.dataHoraFimConsulta())){
            throw new IllegalArgumentException("O Medico já tem uma consulta agendada neste horario.");
        }

        agendamentoConsultaRepository.save(agendamentoConsulta);
    }

    @GetMapping
    public Page<DadosListAgendamentoConsulta>listar(@PageableDefault(size = 10) Pageable pageable) {

        return agendamentoConsultaRepository.findAllByAtivoTrue(pageable).map(DadosListAgendamentoConsulta::new);
    }
}
