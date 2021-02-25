package pet.jerry.value.reflection;

import pet.jerry.value.DefaultSaveableContainer;
import pet.jerry.value.SimpleValue;
import pet.jerry.value.annotation.Value;
import pet.jerry.value.constraint.Constraint;
import pet.jerry.value.storage.ReflectedValueStorage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectedValueFinder {
	private final Object object;

	public ReflectedValueFinder(Object object) {
		this.object = object;
	}

	public List<? extends pet.jerry.value.Value> find() {
		final List<pet.jerry.value.Value> result = new ArrayList<>();
		for (Field field : object.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(Value.class)) {
				try {
					Value annotation = field.getAnnotation(Value.class);
					SimpleValue value = new SimpleValue(annotation.name(), annotation.id(), new ReflectedValueStorage(field, object));
					for (Constraint<?> constraint : ConstraintAdapters.get().constructConstraints(field)) {
						value.addConstraint(constraint);
					}
					result.add(value);
				} catch (Exception e) {
					System.err.println("Couldn't construct SimpleValue");
				}
			}
		}
		return result;
	}

	public void find(DefaultSaveableContainer container) {
		for (pet.jerry.value.Value value : this.find()) {
			container.add(value);
		}
	}
}
