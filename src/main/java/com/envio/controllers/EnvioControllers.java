package com.envio.controllers;

import com.envio.dto.EnvioDTO;
import com.envio.servicies.EnvioServicies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.Link;

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
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        envioServicies.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    

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

    // MÉTODOS HATEOAS

    // HATEOAS: Buscar por ID
    @GetMapping("/hateoas/{id}")
    public EnvioDTO obtenerHATEOAS(@PathVariable Integer id) {
        EnvioDTO dto = envioServicies.obtenerPorId(id);

        // Links de la misma API
        dto.add(linkTo(methodOn(EnvioControllers.class).obtenerHATEOAS(id)).withSelfRel());
        dto.add(linkTo(methodOn(EnvioControllers.class).obtenerTodosHATEOAS()).withRel("todos"));
        // Si tienes el método eliminar descomentado, puedes agregar este link:
        // dto.add(linkTo(methodOn(EnvioControllers.class).eliminar(id)).withRel("eliminar"));

        // Links HATEOAS para API Gateway (ejemplo)
        dto.add(Link.of("http://localhost:8888/api/proxy/envios/" + dto.getIdEnvio()).withSelfRel());
        dto.add(Link.of("http://localhost:8888/api/proxy/envios/" + dto.getIdEnvio()).withRel("Modificar HATEOAS").withType("PUT"));
        dto.add(Link.of("http://localhost:8888/api/proxy/envios/" + dto.getIdEnvio()).withRel("Eliminar HATEOAS").withType("DELETE"));

        return dto;
    }

    // HATEOAS: Listar todos
    @GetMapping("/hateoas")
    public List<EnvioDTO> obtenerTodosHATEOAS() {
        List<EnvioDTO> lista = envioServicies.listar();

        for (EnvioDTO dto : lista) {
            // Link de la misma API
            dto.add(linkTo(methodOn(EnvioControllers.class).obtenerHATEOAS(dto.getIdEnvio())).withSelfRel());

            // Links HATEOAS para API Gateway (ejemplo)
            dto.add(Link.of("http://localhost:8888/api/proxy/envios").withRel("Get todos HATEOAS"));
            dto.add(Link.of("http://localhost:8888/api/proxy/envios/" + dto.getIdEnvio()).withRel("Crear HATEOAS").withType("POST"));
        }

        return lista;
    }
}
