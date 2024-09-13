# Custom SpecFilter

![GitHub release (latest by date)](https://img.shields.io/github/v/release/autonomouslogic/custom-spec-filter)
[![javadoc](https://javadoc.io/badge2/com.autonomouslogic.customspecfilter/custom-spec-filter/javadoc.svg)](https://javadoc.io/doc/com.autonomouslogic.customspecfilter/custom-spec-filter)
[![GitHub Workflow Status (branch)](https://img.shields.io/github/workflow/status/autonomouslogic/custom-spec-filter/Test/main)](https://github.com/autonomouslogic/custom-spec-filter/actions)
[![GitHub](https://img.shields.io/github/license/autonomouslogic/custom-spec-filter)](https://spdx.org/licenses/MIT-0.html)


A custom SpecFilter for the OpenAPI/Swagger.

## Removes implementation arguments from method signatures
Some API implementations, like [CDAP Netty](https://github.com/cdapio/netty-http), for which this filter was originally
written, requires method signatures like this:
```java
public void concatenate(HttpRequest req, HttpResponder res, @PathParam("str1") String str1, @PathParam("str2") String str2)
```
This results in the OpenAPI tools to register `HttpRequest` and `HttpResponder` as schemas in the resulting API spec.
The Custom SpecFilter removes these entries from the method signatures before the spec is written.

## Sorts everything by name
When adding and removing API calls, the OpenAPI spec is sorted in whatever internal representation the plugin has.
This means that when you add new things, it'll also move things around seemingly at random.
If your spec is checked into source control, this can make for annoying diffs.
The Custom SpecFilter makes sure everything is sorted before writing the spec.

## Usage
Include the Custom SpecFilter as a normal dependency and configure your the `filterClass` property of your respective
plugin:

[Gradle](https://github.com/swagger-api/swagger-core/tree/master/modules/swagger-gradle-plugin):
```groovy
resolve {
	filterClass = "com.autonomouslogic.customspecfilter.CustomSpecFilter"
}
```

[Maven](https://github.com/swagger-api/swagger-core/tree/master/modules/swagger-maven-plugin):
```xml
<project>
    <build>
        <plugins>
            <plugin>
                <groupId>io.swagger.core.v3</groupId>
                <artifactId>swagger-maven-plugin</artifactId>
                <configuration>
                    <filterClass>com.autonomouslogic.customspecfilter.CustomSpecFilter</filterClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

A number of schema names are removed by default.
To override the default and customise which schemas are removed, create a file in the root of your project called `customspecfilter.json`:
```json
{
  "filteredSchemas": [
    "BodyConsumer",
    "DecoderResult",
    "HttpMethod",
    "HttpRequest",
    "FullHttpRequest",
    "HttpVersion",
    "HttpResponder"
  ]
}
```

## Resources
* https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Extensions#filters

## Versioning
Custom SpecFilter follows [semantic versioning](https://semver.org/).

## License
Custom SpecFilter is licensed under the [MIT-0 license](https://spdx.org/licenses/MIT-0.html).

## Status
| Type          | Status                                                                                                                                                                                                                                                                                                                                                                                                                |
|---------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| LGTM         | [![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/autonomouslogic/custom-spec-filter.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/autonomouslogic/custom-spec-filter/context:java) [![Total alerts](https://img.shields.io/lgtm/alerts/g/autonomouslogic/custom-spec-filter.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/autonomouslogic/custom-spec-filter/alerts/)                              |
| CodeClimate   | [![Maintainability](https://api.codeclimate.com/v1/badges/04243b52f38c8cecf66c/maintainability)](https://codeclimate.com/github/autonomouslogic/custom-spec-filter/maintainability)                                                                                                                                                                                                                                        |
| SonarCloud    | [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=autonomouslogic_custom-spec-filter&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=autonomouslogic_custom-spec-filter) [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=autonomouslogic_custom-spec-filter&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=autonomouslogic_custom-spec-filter) |
| Libraries.io  | ![Libraries.io dependency status for latest release](https://img.shields.io/librariesio/release/maven/com.autonomouslogic.customspecfilter:custom-spec-filter)                                                                                                                                                                                                                                                                 |
| Snyk          | [![Known Vulnerabilities](https://snyk.io/test/github/autonomouslogic/custom-spec-filter/badge.svg)](https://snyk.io/test/github/autonomouslogic/custom-spec-filter)                                                                                                                                                                                                                                                            |
| Synatype Lift | [link](https://lift.sonatype.com/)                                                                                                                                                                                                                                                                                                                                                                                    |
