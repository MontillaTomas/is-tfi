package com.example.is_tfi.excepciones;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ErrorMessage {
    private HttpStatus status;
    private String message;
    private Map<String,String> detalles;

}
