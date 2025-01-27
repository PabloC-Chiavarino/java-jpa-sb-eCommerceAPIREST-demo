# Usar una imagen base de Maven (que ya incluye Java y Maven)
FROM maven:3.8.4-openjdk-17

# Copiar los archivos del proyecto al contenedor
COPY . /app

# Establecer el directorio de trabajo
WORKDIR /app

# Ejecutar Maven para construir el proyecto
RUN mvn clean install -DskipTests

# Exponer el puerto donde la aplicación correrá
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
