package br.com.tarefas.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.tarefas.controller.response.ErrorResponse;
import br.com.tarefas.exception.TarefaStatusException;

@RestControllerAdvice
public class CustomGlobalExceptionHandler
		extends
			ResponseEntityExceptionHandler {

	@ExceptionHandler(value = EntityNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ErrorResponse entityNotFoundException(
			EntityNotFoundException exception) {
		return new ErrorResponse("Recurso não encontrado");
	}

	@ExceptionHandler(value = TarefaStatusException.class)
	public ResponseEntity<Problem> alteraStatusTarefaHandler(
			TarefaStatusException exception) {
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE,
						MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create().withTitle("Método não permitido")
						.withDetail("Você não pode realizar esta operação: "
								+ exception.getMessage()));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		List<ErrorResponse> errors = ex.getBindingResult().getFieldErrors()
				.stream().map(x -> new ErrorResponse(x.getField(),
						x.getDefaultMessage()))
				.collect(Collectors.toList());
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(value = BadCredentialsException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	ErrorResponse entityBadCredentialsException(BadCredentialsException ex) {
		return new ErrorResponse("Nome de usuário e/ou senha inválidos");
	}
}
