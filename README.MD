# Spring Config Auto Reloading solution for file-based configuration

It is almost impossible to hot reload the properties value from a native spring solution.
For native java application, there's a solution from apache library ([commons-configuration](https://commons.apache.org/proper/commons-configuration/#:~:text=The%20Commons%20Configuration%20software%20library,from%20a%20variety%20of%20sources))
but,
- it is a different library from spring and low level (none declarative), not wise to give up spring config.
- there's some example of integration [link](https://www.baeldung.com/spring-reloading-properties), but it is not as perfect (patchy)

### Spring Cloud Config
- Spring Cloud Config support config reloading via [RefreshScope](https://www.javadoc.io/doc/org.springframework.cloud/spring-cloud-commons-parent/1.1.4.RELEASE/org/springframework/cloud/context/scope/refresh/RefreshScope.html) but not all integration type support automatic properties reload.
- E.g. Spring Cloud Config Consul support but Spring Cloud Config Server doesn't.
- RefreshScope is usually triggered via Spring Actuator Endpoint `/actuator/refresh` or via Spring Bus with queue system [example](https://soshace.com/spring-cloud-config-refresh-strategies/)

### Project Introduction
- In this project, we are using Spring Cloud Config of [Embedding the Config Server](https://cloud.spring.io/spring-cloud-config/reference/html/#_embedding_the_config_server) with `native` file type [boostrap.yml](src\main\resources\boostrap.yml).
- To support automatic reloading, we created a utility class to monitor the properties file. If the file changed is detected, a refresh scope event will be fired.
- This integration will work in `docker` with the configuration folder is volume mounted, but it is not working with Kubernates ConfigMap volume mount.
- Observation from testing, file from ConfigMap volume will only updated upon access (e.g. cat config file from terminal)
- For ConfigMap integration, used Spring Config Kubernates Integration [reference](https://medium.com/swlh/kubernetes-configmap-confuguration-and-reload-strategy-9f8a286f3a44)

### Development Notes
Config search order is as such, it will look through config from all sources (appended effect) [reference](https://docs.spring.io/spring-boot/docs/2.1.9.RELEASE/reference/html/boot-features-external-config.html)
```
file:./config/
file:./
classpath:/config/
classpath:/
```
