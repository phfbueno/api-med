package med.voll.api.record;

import med.voll.api.enums.Especialidade;
import med.voll.api.model.Medico;

public record DadosListMedico(
        Long id,
        String nome,
        String email,
        String crm,
        Especialidade especialidade

) {
    public DadosListMedico(Medico medico) {
        this(

            medico.getId(),
            medico.getNome(),
            medico.getEmail(),
            medico.getCrm(),
            medico.getEspecialidade()
        );
    }
}
