package co.com.anfega.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTechnologyDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 90, message = "La descripción no puede superar los 90 caracteres")
    private String description;
}