# Stock Capital IO

# Configuration URI

    /{application}/{profile}[/{label}]
    /{application}-{profile}.yml
    /{label}/{application}-{profile}.yml
    /{application}-{profile}.properties
    /{label}/{application}-{profile}.properties

# spring-boot:run

    mvn clean spring-boot:run -Dmaven.test.skip=true

    mvn clean spring-boot:run -Dmaven.test.skip=true -Dspring.profile.active=jenkins

# Status Page and Health Indicator

    http://127.0.0.1:9001/actuator/info
    http://127.0.0.1:9001/actuator/health
