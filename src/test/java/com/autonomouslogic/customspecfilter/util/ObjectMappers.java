package com.autonomouslogic.customspecfilter.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMappers {
	public static ObjectMapper objectMapper() {
		var mapper = new ObjectMapper();
		return mapper;
	}
}
