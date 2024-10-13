package med.voll.api.record;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.jetbrains.annotations.NotNull;

public record DadosCadastraisPaciente(


        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefone,

        @NotBlank
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11}", message = "O CPF deve conter exatamente 11 dígitos numéricos, com ou sem caracteres especiais.")
        String cpf,

        @NotNull
        @Valid
        DadosCadastraisEndereco endereco


)  {
}
