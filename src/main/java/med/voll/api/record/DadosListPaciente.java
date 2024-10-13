package med.voll.api.record;

import med.voll.api.model.Paciente;

public record DadosListPaciente(
        Long id,
        String nome,
        String email,
        String cpf
) {
    public DadosListPaciente(Paciente paciente) {
        this(

                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getCpf()
        );
    }
}
