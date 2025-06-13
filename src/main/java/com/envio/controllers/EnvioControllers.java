package com.envio.controllers;

import com.envio.dto.EnvioDTO;
import com.envio.servicies.EnvioServicies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/envios")
public class EnvioControllers {

    @Autowired
    private EnvioServicies envioServicies;

    // POST /envios
    @PostMapping
    public ResponseEntity<EnvioDTO> crear(@RequestBody EnvioDTO dto) {
        return ResponseEntity.ok(envioServicies.crear(dto));
    }

    // GET /envios
    @GetMapping
    public ResponseEntity<List<EnvioDTO>> listar() {
        return ResponseEntity.ok(envioServicies.listar());
    }

    // GET /envios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EnvioDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(envioServicies.obtenerPorId(id));
    }

    // PUT /envios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EnvioDTO> actualizar(@PathVariable Integer id, @RequestBody EnvioDTO dto) {
        return ResponseEntity.ok(envioServicies.actualizar(id, dto));
    }

    // DELETE /envios/{id}
    /*
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        envioServicies.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    */

    // PUT /envios/{id}/estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<EnvioDTO> actualizarEstado(@PathVariable Integer id, @RequestBody String nuevoEstado) {
        // Elimina llaves de comillas si vienen en el body como JSON string
        if (nuevoEstado != null && nuevoEstado.startsWith("\"") && nuevoEstado.endsWith("\"")) {
            nuevoEstado = nuevoEstado.substring(1, nuevoEstado.length() - 1);
        }
        EnvioDTO actualizado = envioServicies.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.ok(actualizado);
    }
}
