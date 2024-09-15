package med.voll.api.record;

import org.jetbrains.annotations.NotNull;

public record DadosAtualizacaoMedico(

        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosCadastraisEndereco endereco
        ) {
}
