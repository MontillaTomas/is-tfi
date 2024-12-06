package com.example.is_tfi.controlador;

import com.example.is_tfi.dominio.Medico;
import com.example.is_tfi.dominio.Usuario;
import com.example.is_tfi.dto.AutenticacionPeticionDTO;
import com.example.is_tfi.dto.AutenticacionRespuestaDTO;
import com.example.is_tfi.dto.MedicoDTO;
import com.example.is_tfi.dto.mapper.MedicoMapper;
import com.example.is_tfi.servicio.AuthenticationService;
import com.example.is_tfi.servicio.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("api/v1/autenticacion")
@RestController
public class ControladorAutenticacion {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public ControladorAutenticacion(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AutenticacionRespuestaDTO> autenticar(@RequestBody AutenticacionPeticionDTO peticion) {
        Medico medico = authenticationService.authenticate(peticion);
        String token = jwtService.generateToken(Map.of("matriculaMedico", medico.getMatricula()), medico.getUsuario());
        AutenticacionRespuestaDTO respuesta = new AutenticacionRespuestaDTO(token, jwtService.getExpirationTime());
        return ResponseEntity.ok(respuesta);
    }
}
