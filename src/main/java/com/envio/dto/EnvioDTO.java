package com.envio.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDTO extends RepresentationModel<EnvioDTO> {

    private Integer idEnvio;
    private Integer idVenta;
    private String direccionEnvio;
    private String estadoEnvio;
    private Date fechaEnvio;
    private Date fechaEntrega;

}
