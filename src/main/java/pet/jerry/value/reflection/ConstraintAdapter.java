package pet.jerry.value.reflection;

import pet.jerry.value.constraint.Constraint;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface ConstraintAdapter<T extends Constraint<?>> {
	Class<? extends Annotation> getAnnotationType();
	T constructConstraint(Field field);
}
