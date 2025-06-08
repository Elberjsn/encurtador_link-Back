package org.elberjsn.encurtador_link.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;


@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> valicadaoHibernate(ConstraintViolationException ex){
        List<String> mensagensDeErro = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagensDeErro.get(0));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> chavePrimari(DataIntegrityViolationException ex){
        var msg = ex.getMessage().split("Chave")[1];


        var indice = msg.indexOf("]");
        if (indice != -1) {
            msg = msg.substring(0,indice);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(msg);
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public static ResponseEntity<String> jaExiste(SQLIntegrityConstraintViolationException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> vazio(NullPointerException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> erroCredenciais(BadCredentialsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> erroValidacao(MethodArgumentNotValidException ex){
        var msg = ex.getMessage().split("message")[2];
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.replace("[", "").replace("]", "") );
    }
    

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<String> erroToken(JWTVerificationException ex){
       
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso Negado,Token Invalido");
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<String> erroTokenExpirado(TokenExpiredException ex){
       
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso Negado,Token Expirado");
    }

    @ExceptionHandler(SignatureVerificationException.class)
    public ResponseEntity<String> erroTokenAssinatura(SignatureVerificationException ex){
       
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso Negado,Erro Interno");
    }
    
    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<String> erroTokenDecodificacao(JWTDecodeException ex){
       
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso Negado,Erro Interno");
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> erroJson(HttpMessageNotReadableException ex){
       
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
