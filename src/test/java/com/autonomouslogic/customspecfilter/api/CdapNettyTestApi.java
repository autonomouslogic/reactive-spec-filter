package com.autonomouslogic.customspecfilter.api;

import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import io.netty.handler.codec.http.HttpRequest;
import io.cdap.http.HttpResponder;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("/v1")
@Tag(name = "cdap-netty")
public class CdapNettyTestApi {
	@GET
	@Path("/testapi/{str1}/{str2}")
	@Operation(
		description = "Concatenates strings.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "The string.",
				content = @Content(
					mediaType = "text/plain",
					schema = @Schema(type = "string")
				)
			)
		}
	)
	public void concatenate(HttpRequest req, HttpResponder res, @PathParam("str1") String str1, @PathParam("str2") String str2) {
		var headers = new DefaultHttpHeaders()
			.add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN);
		res.sendString(HttpResponseStatus.OK, str1 + str2, headers);
	}
}
