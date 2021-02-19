package com.edgleidson.cursomc.service.validacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

// -> Interface criada junto com a Classe(ClienteInsertValidação) para customizar 
//		 validação de CPf, CNPJ e Email.

@Constraint(validatedBy = ClienteInsertValidacao.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ClienteInsert {

	String message() default "Erro de validação";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}