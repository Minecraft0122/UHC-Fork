package com.gmail.val59000mc.scenarios;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is meant to be added to values in {@link ScenarioListener} classes on fields.
 * The options get added to the scenarios.yml for customisation.
 *
 * The field type must match the type returned from the configuration,
 * to avoid runtime exceptions. For example, floating-point values must be
 * {@code double}, not {@code float}.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface Option{

	/**
	 * Config key for option, by default it is the same as the method name.
	 * @return Custom option key
	 */
	String key() default "";
}
