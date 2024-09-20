package med.voll.api.record;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoPaciente(

        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosCadastraisEndereco endereco

) {
}
