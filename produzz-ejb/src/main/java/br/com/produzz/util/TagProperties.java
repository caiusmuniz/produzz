package br.com.produzz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TagProperties {
	private static Logger LOGGER = Logger.getLogger(TagProperties.class);	

	public static final String PROPERTIES = "tags.properties";	
	private static final String ENVIRONMENT_PROPERTY = "br.gov.caixa.tags.properties";
	private static Properties config = null;

	public static void loadConfig(Object clazz) {
		try {
			if (config != null) {
				return;
			}

			config = new Properties();
			InputStream input = null;

			final String properties = System.getProperty(ENVIRONMENT_PROPERTY,PROPERTIES);
			LOGGER.info("Carregando properties em: " + properties);

			final File file = new File(properties);

			if (file.exists()) {
				input = new FileInputStream(file);
			}

			if (input == null) {
				LOGGER.info("Arquivo de properties não localizado em " + properties);
				input = clazz.getClass().getClassLoader().getResourceAsStream(PROPERTIES);
				LOGGER.info("Carregando properties interno.");
			}

			config.load(input);

			input.close();

		} catch (final Exception e) {
			LOGGER.error(e);
			LOGGER.error("Arquivo de properties de configuracao nao encontrado.");
			return;
		}
	}

	public static String get(String key) {
		if (config == null) {
			LOGGER.error("Properties não inicializada para obter a chave " + key);
		}

		return config.getProperty(key);
	}

	public static String get(String key, String def) {
		if (config == null) {
			LOGGER.error("Properties não inicializada para obter a chave " + key);
		}

		return config.getProperty(key, def);
	}

	public static String substuirChavePorValor(String valor) {
		String val = valor;

		for (Map.Entry<Object, Object> prop : config.entrySet()) {
			if (valor.contains(prop.getKey().toString())) {
				val = val.replaceAll(prop.getKey().toString(), prop.getValue().toString());				
			}
		}

		return val;
	}
}
