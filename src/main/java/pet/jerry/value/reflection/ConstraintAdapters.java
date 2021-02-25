package pet.jerry.value.reflection;

import pet.jerry.value.annotation.Clamp;
import pet.jerry.value.constraint.Constraint;
import pet.jerry.value.constraint.NumberClampConstraint;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ConstraintAdapters {
	private static final ConstraintAdapters INSTANCE = new ConstraintAdapters();

	private final Set<ConstraintAdapter<?>> constraintAdapters = new HashSet<>();

	private ConstraintAdapters() {
		this.constraintAdapters.add(new ConstraintAdapter<NumberClampConstraint<?>>() {
			@Override
			public Class<? extends Annotation> getAnnotationType() {
				return Clamp.class;
			}

			@Override
			public NumberClampConstraint<?> constructConstraint(Field field) {
				Clamp clamp = field.getAnnotation(Clamp.class);
				if(field.getType() == Integer.class || field.getType() == int.class) {
					return new NumberClampConstraint<>((int) Math.floor(clamp.min()), (int) Math.floor(clamp.max()));
				} else if(field.getType() == Double.class || field.getType() == double.class) {
					return new NumberClampConstraint<>(clamp.min(), clamp.max());
				} else if(field.getType() == Float.class || field.getType() == float.class) {
					return new NumberClampConstraint<>((float) clamp.min(), (float) clamp.max());
				}

				return null;
			}
		});
	}

	public static ConstraintAdapters get() {
		return INSTANCE;
	}

	public ConstraintAdapter<?> getAdapter(Class<? extends Annotation> annotationClass) {
		for (ConstraintAdapter<?> adapter : this.constraintAdapters) {
			if(adapter.getAnnotationType().equals(annotationClass)) {
				return adapter;
			}
		}

		return null;
	}

	public List<Constraint<?>> constructConstraints(Field field) {
		final List<Constraint<?>> result = new ArrayList<>();
		for (Annotation annotation : field.getAnnotations()) {
			ConstraintAdapter<?> adapter = this.getAdapter(annotation.annotationType());
			if(adapter != null) {
				Constraint<?> constraint = adapter.constructConstraint(field);
				if(null != constraint)
					result.add(constraint);
			}
		}
		return result;
	}
}
