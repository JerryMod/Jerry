package pet.jerry.feature.annotation;

import pet.jerry.feature.category.FeatureCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FeatureInfo {
	String id();
	String name();
	FeatureCategory category() default FeatureCategory.UNCATEGORIZED;
}
