package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.model.Medico;
import med.voll.api.record.DadosAtualizacaoMedico;
import med.voll.api.record.DadosCadastraisMedicos;
import med.voll.api.record.DadosListMedico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public void cadastrarMedicos(@RequestBody @Valid  DadosCadastraisMedicos dados){
        medicoRepository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListMedico> listar(@PageableDefault(size = 10, sort = {"nome"})Pageable pageable) {

        return medicoRepository.findAllByAtivoTrue(pageable).map(DadosListMedico::new);

    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {

          var medico = medicoRepository.getReferenceById(dados.id());

          medico.atualizarMedico(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id) {

        var medico = medicoRepository.getReferenceById(id);

        medico.excluir();
    }
}
