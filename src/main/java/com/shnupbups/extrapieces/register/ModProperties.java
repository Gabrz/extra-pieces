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
		Z0("z0"), Z1("z1"), Z2("z2"), Z3("z3"),
		X0("x0"), X1("x1"), X2("x2"), X3("x3"),
		Y0("y0"), Y1("y1"), Y2("y2"), Y3("y3"),
		Y0Z0("y0z0"), Y0X0("y0x0"), Z0X0("z0x0"), Y0Z0X0("y0z0x0"),
		Y0Z1("y0z1"), Y0X1("y0x1"), Z1X1("z1x1"), Y0Z1X1("y0z1x1"),
		Y1Z3("y1z3"), Y1X0("y1x0"), Z3X0("z3x0"), Y1Z3X0("y1z3x0"),
		Y1Z2("y1z2"), Y1X1("y1x1"), Z2X1("z2x1"), Y1Z2X1("y1z2x1"),
		Y2Z3("y2z3"), Y2X3("y2x3"), Z3X3("z3x3"), Y2Z3X3("y2z3x3"),
		Y2Z2("y2z2"), Y2X2("y2x2"), Z2X2("z2x2"), Y2Z2X2("y2z2x2"),
		Y3Z0("y3z0"), Y3X3("y3x3"), Z0X3("z0x3"), Y3Z0X3("y3z0x3"),
		Y3Z1("y3z1"), Y3X2("y3x2"), Z1X2("z1x2"), Y3Z1X2("y3z1x2");

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

	private static TrimType getTrimType(String value) {
		TrimType result = null;
		for (ModProperties.TrimType t : ModProperties.TrimType.values()) {
			if (value.equals(t.toString().toLowerCase())) {
				result = t;
				break;
			}
		}
		return result;
	}

	public static TrimType getTrimTypeByValue(String value) {
		TrimType result = null;
		switch (value.length()) {
			case 4:
				result = getTrimType(value);
				if (result == null)
					result = getTrimType(value.substring(2, 4) + value.substring(0, 2));
				break;
			case 6:
				String part_1 = value.substring(0, 2);
				String part_2 = value.substring(2, 4);
				String part_3 = value.substring(4, 6);
				result = getTrimType(part_1 + part_2 + part_3);
				if (result == null) {
					result = getTrimType(part_1 + part_3 + part_2);
				}
				if (result == null) {
					result = getTrimType(part_2 + part_1 + part_3);
				}
				if (result == null) {
					result = getTrimType(part_2 + part_3 + part_1);
				}
				if (result == null) {
					result = getTrimType(part_3 + part_1 + part_2);
				}
				if (result == null) {
					result = getTrimType(part_3 + part_2 + part_1);
				}
				break;
			default:
				result = getTrimType(value);
		}
		return result;
	}
}
