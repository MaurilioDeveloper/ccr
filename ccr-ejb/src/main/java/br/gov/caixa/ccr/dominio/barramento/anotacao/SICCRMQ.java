package br.gov.caixa.ccr.dominio.barramento.anotacao;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;

@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Qualifier
public @interface SICCRMQ {}
