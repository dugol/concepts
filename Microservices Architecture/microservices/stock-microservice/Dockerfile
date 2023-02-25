# Imagen base para el contenedor
FROM openjdk:17

#Vamos a utilziar .dockerignore y multi - stage build

# Copia el archivo JAR de la aplicación
COPY ./build/libs/stock-microservice-0.0.1-SNAPSHOT.jar app.jar

# Especifica el comando de inicio del contenedor
CMD ["java", "-jar", "app.jar"]