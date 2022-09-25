package com.autonomouslogic.customspecfilter;

import io.swagger.v3.core.filter.AbstractSpecFilter;
import io.swagger.v3.core.model.ApiDescription;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * SpecFilter for working with Reactive types.
 */
@Slf4j
public class CustomSpecFilter extends AbstractSpecFilter {
	private static final Set<String> filteredSchemas = new HashSet<>(Arrays.asList(
			"BodyConsumer", "DecoderResult", "HttpMethod", "HttpRequest", "HttpVersion", "HttpResponder"));

	@Override
	public Optional<OpenAPI> filterOpenAPI(
			OpenAPI openAPI,
			Map<String, List<String>> params,
			Map<String, String> cookies,
			Map<String, List<String>> headers) {
		openAPI = sortValues(openAPI);
		return super.filterOpenAPI(openAPI, params, cookies, headers);
	}

	@Override
	public Optional<RequestBody> filterRequestBody(
			RequestBody requestBody,
			Operation operation,
			ApiDescription api,
			Map<String, List<String>> params,
			Map<String, String> cookies,
			Map<String, List<String>> headers) {
		// Remove all request bodies from GET and DELETEs.
		if (api.getMethod().equals("GET") || api.getMethod().equals("DELETE")) {
			return Optional.empty();
		}
		// Remove any request bodies which have been auto-resolved to "*/*".
		Content content = requestBody.getContent();
		if (content != null) {
			content.remove("*/*");
			if (content.isEmpty()) {
				return Optional.empty();
			}
		}
		return Optional.of(requestBody);
	}

	@Override
	public Optional<ApiResponse> filterResponse(
			ApiResponse response,
			Operation operation,
			ApiDescription api,
			Map<String, List<String>> params,
			Map<String, String> cookies,
			Map<String, List<String>> headers) {
		// Remove any response bodies which have been auto-resolved to "*/*".
		Content content = response.getContent();
		if (content != null) {
			content.remove("*/*");
			if (content.isEmpty()) {
				return Optional.empty();
			}
		}
		return Optional.of(response);
	}

	@Override
	public Optional<Schema> filterSchema(
			Schema schema,
			Map<String, List<String>> params,
			Map<String, String> cookies,
			Map<String, List<String>> headers) {
		if (filteredSchemas.contains(schema.getName())) {
			return Optional.empty();
		}
		return Optional.of(schema);
	}

	private OpenAPI sortValues(OpenAPI openAPI) {
		// Sort paths.
		if (openAPI.getPaths() != null) {
			openAPI.setPaths(sortPaths(openAPI.getPaths()));
		}
		var components = openAPI.getComponents();
		if (components != null) {
			// Sort schemas.
			if (components.getSchemas() != null) {
				components.setSchemas(sortSchemas(components.getSchemas()));
			}
		}

		return openAPI;
	}

	private Paths sortPaths(@NonNull Paths paths) {
		var newPaths = paths.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(u, v) -> {
							throw new IllegalStateException(String.format("Duplicate key %s", u));
						},
						Paths::new));
		return newPaths;
	}

	private Map<String, Schema> sortSchemas(@NonNull Map<String, Schema> schemas) {
		return schemas.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(u, v) -> {
							throw new IllegalStateException(String.format("Duplicate key %s", u));
						},
						LinkedHashMap::new));
	}
}
