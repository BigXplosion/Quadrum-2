package quadrum.util;

public class Utils {

	public static String getIconForRegister(String icon) {
		if (icon.contains(":"))
			return icon;
		return "qresource:" + icon;
	}
}
