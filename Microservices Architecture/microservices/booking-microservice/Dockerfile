# Imagen base para el contenedor
FROM openjdk:17

# Copia el archivo JAR de la aplicación
COPY ./build/libs/booking-microservice-0.0.1-SNAPSHOT.jar app.jar

# Especifica el comando de inicio del contenedor
CMD ["java", "-jar", "app.jar"]