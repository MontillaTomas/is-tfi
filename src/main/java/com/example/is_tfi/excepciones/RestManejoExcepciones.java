package com.example.is_tfi.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestManejoExcepciones {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> manejarValidaciones(MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, String> errores = new HashMap<>();


        ex.getBindingResult().getFieldErrors().forEach(error -> {

            String mensajePersonalizado = "El valor de " + error.getField() + " no es válido: " + error.getDefaultMessage();
            errores.put(error.getField(), mensajePersonalizado);
        });

        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST,
                "Error de validación: revisa los campos proporcionados",
                null
        );
        errorMessage.setDetalles(errores);


        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PacienteYaPoseeDiagnosticoExcepcion.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> pacienteYaPoseeDiagnostico(PacienteYaPoseeDiagnosticoExcepcion excepcion) {
        return new ResponseEntity<>(excepcion.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PacienteNoEncontradoExcepcion.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> pacienteNoEncontrado(PacienteNoEncontradoExcepcion excepcion) {
        return new ResponseEntity<>(excepcion.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DiagnosticoNoEncontradoExcepcion.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> diagnosticoNoEncontrado(DiagnosticoNoEncontradoExcepcion excepcion) {
        return new ResponseEntity<>(excepcion.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DniEnUsoExcepcion.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> dnienUso(DniEnUsoExcepcion excepcion) {
        return new ResponseEntity<>(excepcion.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CuilEnUsoExcepcion.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> cuilenUso(CuilEnUsoExcepcion excepcion) {
        return new ResponseEntity<>(excepcion.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsuarioNoEncontradoExcepcion.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> usuarioNoEncontrado(UsuarioNoEncontradoExcepcion excepcion) {
        return new ResponseEntity<>(excepcion.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MedicoNoEncontradoExcepcion.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> medicoNoEncontrado(MedicoNoEncontradoExcepcion excepcion) {
        return new ResponseEntity<>(excepcion.getMessage(), HttpStatus.NOT_FOUND);
    }
}
