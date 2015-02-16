package quadrum.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.Maps;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.registry.LanguageRegistry;

import quadrum.Quadrum;

public class LanguageHelper {

	private static final FilenameFilter FILTER = new ExtensionFilter("lang");

	public static void loadDirectory(File dir) {
		if (!dir.isDirectory())
			return;

		for (File file : dir.listFiles(FILTER))
			loadFile(file);
	}

	public static void loadFile(File file) {
		try {
			String lang = file.getName().substring(0, file.getName().lastIndexOf("."));
			Properties properties = new Properties();
			properties.load(new FileInputStream(file));
			HashMap<String, String> map = Maps.newHashMap();
			for (Map.Entry<Object, Object> entry : properties.entrySet())
				map.put(entry.getKey().toString(), entry.getValue().toString());
			LanguageRegistry.instance().injectLanguage(lang, map);
		}
		catch (IOException ex) {
			Quadrum.log(Level.WARN, "Failed to load localizations from %s. Reason: %s", file.getName(), ex.toString());
		}
	}
}
