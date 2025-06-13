package com.envio.servicies;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envio.dto.EnvioDTO;
import com.envio.models.EnvioModels;
import com.envio.repositories.EnvioRepositories;

@Service
public class EnvioServicies {

    @Autowired
    private EnvioRepositories envioRepositories;

    private EnvioDTO toDTO(EnvioModels envio){
        return new EnvioDTO(
            envio.getIdEnvio(),
            envio.getIdVenta(),
            envio.getDireccionEnvio(),
            envio.getEstadoEnvio(),
            envio.getFechaEnvio(),
            envio.getFechaEntrega()
        );
    }

    private EnvioModels toEntity(EnvioDTO dto) {
        EnvioModels envio = new EnvioModels();
        envio.setIdEnvio(dto.getIdEnvio());
        envio.setIdVenta(dto.getIdVenta());
        envio.setDireccionEnvio(dto.getDireccionEnvio());
        envio.setEstadoEnvio(dto.getEstadoEnvio());
        envio.setFechaEnvio(dto.getFechaEnvio());
        envio.setFechaEntrega(dto.getFechaEntrega());
        return envio;
    }

    public EnvioDTO crear(EnvioDTO dto) {
        EnvioModels envio = toEntity(dto);
        return toDTO(envioRepositories.save(envio));
    }

    public List<EnvioDTO> listar() {
        return envioRepositories.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EnvioDTO obtenerPorId(Integer id) {
        EnvioModels envio = envioRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
        return toDTO(envio);
    }

    public EnvioDTO actualizar(Integer id, EnvioDTO dto) {
        EnvioModels existente = envioRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));

        existente.setIdVenta(dto.getIdVenta());
        existente.setDireccionEnvio(dto.getDireccionEnvio());
        existente.setEstadoEnvio(dto.getEstadoEnvio());
        existente.setFechaEnvio(dto.getFechaEnvio());
        existente.setFechaEntrega(dto.getFechaEntrega());

        return toDTO(envioRepositories.save(existente));
    }

    public EnvioDTO actualizarEstado(Integer id, String nuevoEstado) {
        EnvioModels envio = envioRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));
        envio.setEstadoEnvio(nuevoEstado);
        return toDTO(envioRepositories.save(envio));
    }

    public void eliminar(Integer id) {
        envioRepositories.deleteById(id);
    }
}
