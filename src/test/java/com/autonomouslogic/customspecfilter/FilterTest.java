package com.autonomouslogic.customspecfilter;

import com.autonomouslogic.customspecfilter.util.Spec;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterTest {
	@Test
	public void shouldFilterApiCalls() {
		var spec = Spec.get();
		var path = spec.getPaths().get("/v1/testapi/{str1}/{str2}");
		var get = path.getGet();
		assertNull(get.getRequestBody());
	}

	@Test
	public void shouldFilterSchemas() {
		var spec = Spec.get();
		var schemaNames = spec.getComponents().getSchemas().keySet();
		assertFalse(schemaNames.contains("HttpRequest"));
		assertFalse(schemaNames.contains("HttpResponder"));
	}
}
