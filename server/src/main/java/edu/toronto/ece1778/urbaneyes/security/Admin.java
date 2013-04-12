package edu.toronto.ece1778.urbaneyes.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.seam.security.annotations.SecurityBindingType;

/**
 * Annotation determining if the user has admin rights. Currently only one type
 * of role is supported.
 * 
 * @author mcupak
 * 
 */
@SecurityBindingType
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER,
		ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Admin {
}
