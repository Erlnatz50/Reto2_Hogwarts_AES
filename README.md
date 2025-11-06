# Proyecto JavaFX: Gestión de Personas

## 📖 Descripción
Este proyecto es un ejercicio para crear una aplicación JavaFX que permite gestionar una tabla de personas.
Se pueden añadir, eliminar y restaurar filas en la tabla que muestra personas con sus datos (ID, nombre, apellidos y cumpleaños).
Además, se aplica una hoja de estilos CSS y se registran eventos y errores con SLF4J.

## 📂 Archivos más importantes del proyecto
- **Java:**
    - `App.java`: Clase principal que lanza la aplicación y carga la interfaz.
    - `Lanzador.java`: Clase para iniciar la aplicación.
    - `Persona.java`: Modelo de datos para representar a una persona.
    - `VisualizarCliente.java`: Controlador para la vista que gestiona la tabla personas.
    - `PersonaDAO.java`: Clase de acceso a datos para interactuar con la base de datos.
- **CSS:**
    - `estilo.css`: Hoja de estilos para la interfaz gráfica.
- **FXML:**
    - `visualizarCliente.fxml`: Definición de la interfaz gráfica declarativa.
- **XML:**
    - `logback.xml`: Configuración para el sistema de logging SLF4J-Logback.
    - `pom.xml`: Archivo de configuración y dependencias de Maven.
- **Manifest:**
    - `MANIFEST.MF`: Archivo de manifiesto para empaquetado y ejecución.

## 📈 Funcionalidades principales
- Visualizar tabla de personas con atributos.
- Añadir nuevas personas con ID automático.
- Eliminar la persona seleccionada.
- Restaurar la tabla con datos de ejemplo y reiniciar contador de IDs.
- Manejo de errores y notificaciones para el usuario vía alertas.
- Registro detallado de eventos y errores en archivo log.

## 🚀 Instalación y ejecución
1. Clonar el repositorio.
2. Abrir en un IDE compatible con JavaFX y SLF4J (Java +17 recomendado).
3. Configurar dependencias con Maven (`pom.xml`).
4. Ejecutar la clase principal `es.erlantzg.App`.

## ✨ Autor
- 👤 Erlantz García
