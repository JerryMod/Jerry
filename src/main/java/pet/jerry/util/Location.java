package pet.jerry.util;

import net.minecraft.entity.Entity;

import java.util.Objects;

public final class Location {
	private final double x;
	private final double y;
	private final double z;

	public Location(Entity entity) {
		this(entity.posX, entity.posY, entity.posZ);
	}

	public Location(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Location location = (Location) o;
		return Double.compare(location.getX(), getX()) == 0 && Double.compare(location.getZ(), getZ()) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getX(), getZ());
	}
}
