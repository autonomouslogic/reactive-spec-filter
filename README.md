# Custom SpecFilter
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

## Resources
* https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Extensions#filters
