package br.gov.caixa.ccr.dominio.barramento.anotacao;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Qualifier
public @interface SIRICMQ {}
