package pet.jerry.value.constraint;

public class NumberClampConstraint implements Constraint<Number> {
	private final Number lowerBound;
	private final Number upperBound;

	public NumberClampConstraint(Number lowerBound, Number upperBound) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	@Override
	public boolean validate(Number value) {
		return value.doubleValue() <= upperBound.doubleValue()
				&& value.doubleValue() >= lowerBound.doubleValue();
	}

	public static class Builder {
		private Number lowerBound = Long.MIN_VALUE;
		private Number upperBound = Long.MAX_VALUE;

		public Builder withLowerBound(Number lowerBound) {
			this.lowerBound = lowerBound;
			return this;
		}

		public Builder withUpperBound(Number upperBound) {
			this.upperBound = upperBound;
			return this;
		}

		public NumberClampConstraint build() {
			return new NumberClampConstraint(lowerBound, upperBound);
		}
	}
}
