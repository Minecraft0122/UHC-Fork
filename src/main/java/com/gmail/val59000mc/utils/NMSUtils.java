package com.gmail.val59000mc.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NMSUtils {

	public static List<Field> getAnnotatedFields(Class<?> c, Class<? extends Annotation> annotation) {
		List<Field> fields = new ArrayList<>();
		for (Field field : c.getFields()) {
			if (field.isAnnotationPresent(annotation)) {
				field.setAccessible(true);
				fields.add(field);
			}
		}

		for (Field field : c.getDeclaredFields()) {
			if (field.isAnnotationPresent(annotation)) {
				field.setAccessible(true);
				fields.add(field);
			}
		}

		return fields;
	}

}
