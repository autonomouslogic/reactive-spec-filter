package com.autonomouslogic.customspecfilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import lombok.SneakyThrows;

class ConfigLoader {
	private static final String filename = "customspecfilter.json";

	@SneakyThrows
	static Config loadConfig() {
		var objectMapper = new ObjectMapper();
		var file = new File(filename);
		if (file.exists()) {
			try (var in = new FileInputStream(file)) {
				return objectMapper.readValue(in, Config.class);
			}
		}
		try (var in = ConfigLoader.class.getClassLoader().getResourceAsStream(filename)) {
			if (in == null) {
				throw new RuntimeException("Default config not found in resources " + filename);
			}
			return objectMapper.readValue(in, Config.class);
		}
	}
}
