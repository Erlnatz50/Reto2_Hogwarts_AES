# Aplicación de Cifrado y Descifrado - CryptoWarts

## 📖 Descripción del repositorio
Este proyecto es una aplicación Java diseñada para cifrar y descifrar textos y archivos utilizando dos algoritmos diferentes: **AES** y **Vigenère**. Utiliza **JavaFX** para la interfaz de usuario y **Maven** para la gestión de dependencias.

## 📂 Estructura del Proyecto

El proyecto está organizado siguiendo una arquitectura modular que separa la lógica de negocio, la interfaz de usuario y los algoritmos de cifrado de forma clara.

---

### 📌 **1. Aplicación**
- `📁 es/cryptowarts/`
    - 📌 `App.java` → Clase principal de la aplicación JavaFX.
    - 📌 `Lanzador.java` → Punto de entrada para iniciar la aplicación (especialmente útil para empaquetado JAR).

---

### 📌 **2. Controladores**
Gestionan la lógica de la interfaz gráfica y la interacción entre la vista (FXML) y los servicios de cifrado.
- `📁 es/cryptowarts/controladores/`
    - 📌 `ControladorVentana.java` → Controlador de la ventana principal que gestiona toda la interfaz de usuario.

---

### 📌 **3. Algoritmos de Cifrado**
Contienen las implementaciones de los diferentes algoritmos de cifrado soportados por la aplicación.
- `📁 es/cryptowarts/cifrado/`
    - 📌 `CifradoAES.java` → Implementa el cifrado **AES** (Advanced Encryption Standard) en modo CBC con PKCS5Padding.
    - 📌 `CifradoVigenere.java` → Implementa el cifrado clásico **Vigenère** para textos.

---

### 📌 **4. Configuración del Módulo**
- 📌 `module-info.java` → Configuración del módulo para Java Platform Module System (JPMS).

---

### 📌 **5. Archivos de Configuración**
- 📌 `pom.xml` → Definición de dependencias Maven y configuración de plugins.
- 📌 `.gitignore` → Exclusiones de control de versiones.
- 📌 `README.md` → Documentación general del proyecto.

---

### 📌 **6. Recursos Estáticos**
Contiene archivos de estilo, interfaces y otros recursos necesarios para la interfaz gráfica.

#### 🎨 **CSS**
- `📁 es/cryptowarts/css/`
    - 📌 `estilo.css` → Define los estilos visuales aplicados a la UI JavaFX.

#### 🖼️ **FXML**
- `📁 es/cryptowarts/fxml/`
    - 📌 `ventana.fxml` → Interfaz principal de la aplicación.

#### 🖼️ **Imágenes**
- `📁 es/cryptowarts/imagenes/`
    - 🖼️ `escudoCasa-01.png` → Escudo de Gryffindor 
    - 🖼️ `escudoCasa-02.png` → Escudo de Hufflepuff
    - 🖼️ `escudoCasa-03.png` → Escudo de Ravenclaw
    - 🖼️ `escudoCasa-04.png` → Escudo de Slytherin
    - 🖼️ `hogwarts-01.png` → Escudo principal de Hogwarts
    - 🖼️ `icono.png` → Icono de la aplicación
    - 🖼️ `logo_españa.png` → Bandera de España para selección de idioma
    - 🖼️ `logo.euskera.png` → Bandera vasca para selección de idioma
    - 🖼️ `logo.ingles.png` → Bandera inglesa para selección de idioma
    - 🖼️ `logo.hogwarts.png` → Logo alternativo de Hogwarts

#### 🌍 **Internacionalización**
- `📁 es/cryptowarts/` (en resources)
    - 📌 `mensaje.properties` → Archivo de propiedades para internacionalización.

#### 📊 **Configuración de Logs**
- `📁 META-INF/`
    - 📌 `logback.xml` → Configuración del sistema de logging con Logback.

---

## ⚙️ Características de la Aplicación

### 🔐 **Algoritmos Soportados:**
- **AES (Advanced Encryption Standard)**:
    - Modo CBC con PKCS5Padding
    - Clave de 128 bits
    - Vector de inicialización fijo
    - Soporte para textos y archivos binarios

- **Vigenère**:
    - Cifrado clásico de sustitución
    - Solo para texto
    - Preserva mayúsculas/minúsculas y caracteres no alfabéticos

### 🎯 **Funcionalidades:**
- Cifrado y descifrado de texto en tiempo real
- Cifrado y descifrado de archivos completos
- Interfaz intuitiva con áreas de texto separadas
- Selección dinámica entre algoritmos
- Gestión de archivos mediante diálogos
- Validación de entradas y manejo de errores
- Internacionalización (múltiples idiomas)
- Temática visual de Hogwarts con escudos de las casas
- Sistema completo de logging

### 📁 **Procesamiento de Archivos:**
- Los archivos cifrados/descifrados se guardan con sufijos:
    - `_cifrado` para archivos cifrados
    - `_descifrado` para archivos descifrados
- Mantienen la extensión original del archivo

---

## ⚙️ Requisitos de la aplicación
- ☕ **JDK 22** o superior
- 🎭 **JavaFX 24**
- 🏗️ **Maven 3.6+**

---

## 🚀 Instalación y Ejecución

### Método 1: Desde el IDE (Recomendado para desarrollo)
1. Clona el repositorio:
   ```sh
   git clone https://github.com/Erlnatz50/Reto2_Hogwarts_AES.git
   ```

2. Importa el proyecto en tu IDE como proyecto Maven.

3. Ejecuta la clase `Lanzador.java` para iniciar la aplicación.

### Método 2: Desde Maven
```sh
mvn clean javafx:run
```

### Método 3: JAR Ejecutable
```sh
mvn clean package
java -jar target/Reto2_Hogwarts_AES-1.0-SNAPSHOT.jar
```

---

## 🛠️ Dependencias Principales

- **JavaFX 24** - Para la interfaz gráfica
- **SLF4J + Logback** - Para logging y registro de eventos
- **JUnit** - Para pruebas unitarias (implícito en Maven)

---

## 📋 Uso de la Aplicación

1. **Selecciona el algoritmo**: Elige entre AES o Vigenère
2. **Elige la operación**: Cifrar o Descifrar
3. **Introduce la clave**: La misma clave debe usarse para cifrar y descifrar
4. **Procesa**:
    - **Texto**: Escribe en el área izquierda y haz clic en el botón de acción
    - **Archivos**: Usa el botón "Seleccionar archivo" para procesar archivos

---

## 🎨 Temática Visual
La aplicación cuenta con una completa temática de Hogwarts que incluye:
- Escudos de las 4 casas de Hogwarts
- Logo principal de Hogwarts
- Iconos de banderas para selección de idioma
- Diseño cohesivo con colores y estilos temáticos

---

## ⚠️ Consideraciones de Seguridad

- **AES**: El IV está fijado (para fines educativos). En producción, usar IV aleatorio.
- **Vigenère**: Algoritmo histórico, no seguro para uso real.
- Las claves se normalizan a 16 bytes para AES.

---

## 📊 Sistema de Logs
La aplicación incluye configuración completa de logging con Logback:
- Registro de eventos de la aplicación
- Traza de errores y excepciones
- Configuración flexible de niveles de log

---

## ✨ Autores
- 👤 **Erlantz Garcia**
- 👤 **Telmo Castillo**

---

## 📄 Licencia
Este proyecto es con fines educativos como parte de un reto de programación.