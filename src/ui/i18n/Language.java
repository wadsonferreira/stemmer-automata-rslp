package ui.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import ui.RSLPTestConfig;

public final class Language {
	
	private static ResourceBundle labels;
	
	static {
		changeLanguage();
	}

	public static void changeLanguage() {
		Locale.setDefault(new Locale(RSLPTestConfig.language, RSLPTestConfig.country));
		labels = ResourceBundle.getBundle("ui.i18n.strings");
	}
	
	public static final String getString(String key) {
		return labels.getString(key);
	}
	
}
