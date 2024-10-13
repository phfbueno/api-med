package med.voll.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "agendamento_consulta")
@Entity(name = "AgendamentoConsulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class AgendamentoConsulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(name = "data_hora_consulta", nullable = false)
    private LocalDateTime dataHoraConsulta;

    private LocalDateTime dataHoraFimConsulta;

    private boolean ativo;

    @PrePersist
    public void definirDuracaoConsulta() {
        this.dataHoraFimConsulta = this.dataHoraConsulta.plusHours(1);
    }
}
