package com.example.is_tfi.excepciones;

import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;

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
    public ResponseEntity<ErrorMessage> pacienteYaPoseeDiagnostico(PacienteYaPoseeDiagnosticoExcepcion excepcion) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.CONFLICT,
                excepcion.getMessage(),
                null
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PacienteNoEncontradoExcepcion.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> pacienteNoEncontrado(PacienteNoEncontradoExcepcion excepcion) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                excepcion.getMessage(),
                null
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DiagnosticoNoEncontradoExcepcion.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> diagnosticoNoEncontrado(DiagnosticoNoEncontradoExcepcion excepcion) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                excepcion.getMessage(),
                null
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DniEnUsoExcepcion.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> dnienUso(DniEnUsoExcepcion excepcion) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.CONFLICT,
                excepcion.getMessage(),
                null
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CuilEnUsoExcepcion.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> cuilenUso(CuilEnUsoExcepcion excepcion) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.CONFLICT,
                excepcion.getMessage(),
                null
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsuarioNoEncontradoExcepcion.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> usuarioNoEncontrado(UsuarioNoEncontradoExcepcion excepcion) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                excepcion.getMessage(),
                null
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MedicoNoEncontradoExcepcion.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> medicoNoEncontrado(MedicoNoEncontradoExcepcion excepcion) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND,
                excepcion.getMessage(),
                null
        );
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ObraSocialNoValidaExcepcion.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> obraSocialNoValida(ObraSocialNoValidaExcepcion excepcion) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, excepcion.getMessage(), null);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EvolucionNoEncontradaExcepcion.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> evolucionNoEncontrada(EvolucionNoEncontradaExcepcion excepcion) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, excepcion.getMessage(), null);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IdYaEstaEnUsoExcepcion.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> idRecetaDigitalYaEstaEnUso(IdYaEstaEnUsoExcepcion excepcion) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT, excepcion.getMessage(), null);
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }
}
