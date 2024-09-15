package med.voll.api.model;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.enums.Especialidade;
import med.voll.api.record.DadosAtualizacaoMedico;
import med.voll.api.record.DadosCadastraisMedicos;
import med.voll.api.record.DadosCadastraisEndereco;
import med.voll.api.record.DadosListMedico;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String nome;
        private String email;
        private String crm;

        private String telefone;
        @Enumerated(EnumType.STRING)
        private Especialidade especialidade;

        @Embedded
        private Endereco endereco;
        private boolean ativo;

        public Medico(DadosCadastraisMedicos dados) {
                this.nome = dados.nome();
                this.email =dados.email();
                this.crm = dados.crm();
                this.telefone = dados.telefone();
                this.especialidade = dados.especialidade();
                this.endereco = new Endereco(dados.endereco());
                this.ativo = true;
        }

        public void atualizarMedico(DadosAtualizacaoMedico dados) {

                if (dados.nome() != null){
                        this.nome = dados.nome();
                }
                if (dados.telefone() != null){
                        this.telefone = dados.telefone();
                }
                if (dados.endereco() != null){
                        this.endereco.atualizarEndereco(dados.endereco());
                }


        }

        public void excluir() {
                this.ativo = false;
        }
}
