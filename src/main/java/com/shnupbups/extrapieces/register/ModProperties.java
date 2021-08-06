package com.shnupbups.extrapieces.register;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public class ModProperties {
	public static final EnumProperty<SidingType> SIDING_TYPE;
	public static final EnumProperty<TrimType> TRIM_TYPE;

	static {
		SIDING_TYPE = EnumProperty.of("type", SidingType.class);
		TRIM_TYPE = EnumProperty.of("type", TrimType.class);
	}

	public enum SidingType implements StringIdentifiable {
		SINGLE("single"),
		DOUBLE("double");

		private final String name;

		SidingType(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}

		public String asString() {
			return this.name;
		}
	}

	public enum TrimType implements StringIdentifiable {
		Q0("q0"),
		Q1("q1"),
		Q2("q2"),
		Q3("q3");

		private final String name;

		TrimType(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}

		public String asString() {
			return this.name;
		}
	}
}
