package med.voll.api.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.record.DadosAtualizacaoPaciente;
import med.voll.api.record.DadosCadastraisPaciente;

@Table(name = "paciente")
@Entity(name = "paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @Embedded
    private Endereco endereco;
    private boolean ativo;

    public Paciente(@Valid DadosCadastraisPaciente dadosCadastraisPaciente) {
        this.nome = dadosCadastraisPaciente.nome();
        this.email = dadosCadastraisPaciente.email();
        this.telefone = dadosCadastraisPaciente.telefone().replaceAll("[^\\d]", "");
        this.cpf = dadosCadastraisPaciente.cpf().replaceAll("[^\\d]", "");
        this.endereco = new Endereco(dadosCadastraisPaciente.endereco());
        this.ativo = true;
    }

    public void atualizarPaciente(@Valid DadosAtualizacaoPaciente dadosAtualizacaoPaciente) {

        if (dadosAtualizacaoPaciente.nome() != null){
            this.nome = dadosAtualizacaoPaciente.nome();
        }
        if(dadosAtualizacaoPaciente.telefone() != null){
            this.nome = dadosAtualizacaoPaciente.telefone();
        }
        if(dadosAtualizacaoPaciente.endereco() != null){
            this.endereco.atualizarEndereco(dadosAtualizacaoPaciente.endereco());
        }
    }

    public void excluir(Long id) {
        this.ativo = false;
    }
}
