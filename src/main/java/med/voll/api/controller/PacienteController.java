package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.model.Paciente;
import med.voll.api.record.DadosAtualizacaoPaciente;
import med.voll.api.record.DadosCadastraisPaciente;
import med.voll.api.record.DadosListPaciente;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public void cadastrarPaciente(@RequestBody @Valid DadosCadastraisPaciente dadosCadastraisPaciente) {
        pacienteRepository.save(new Paciente(dadosCadastraisPaciente));
    }

    @GetMapping
    public Page<DadosListPaciente> listarPaciente(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
        return pacienteRepository.findAllByAtivoTrue(pageable).map(DadosListPaciente::new);
    }

    @PutMapping
    @Transactional
    public  void atualizarPaciente(@RequestBody @Valid DadosAtualizacaoPaciente dadosAtualizacaoPaciente){
        var paciente = pacienteRepository.getReferenceById(dadosAtualizacaoPaciente.id());
        paciente.atualizarPaciente(dadosAtualizacaoPaciente);
    }

    @DeleteMapping({"/{id}"})
    @Transactional
    public void deletarPAciente(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.excluir(id);
    }

}
