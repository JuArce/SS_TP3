# SS TP3: Difusión de un gas 2D

Implementación Trabajo Práctico Número 3: Difusión de un gas 2D para la materia 72.25 - Simulación de Sistemas - Segundo Cuatrimestre 2022.

# Autores

* [Julián Francisco **Arce Doncella**](https://github.com/JuArce)
* [Roberto José **Catalán**](https://github.com/rcatalan98)
* [Gian Luca **Pecile**](https://github.com/glpecile)

# Dependencias

* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

# Compilación

Para compilar el proyecto, se debe ejecutar el siguiente comando en la raíz del proyecto:

```bash
maven clean install
```

## Generator

Parámetros:
* `n`: cantidad de partículas.
* `Lx`: largo del sistema en x (0.12).
* `Ly`: largo del sistema en y (0.09).
* `k`: ancho de la ranura.
* `staticNFile.csv`: nombre del archivo estático de salida.
* `dynamicNFile.csv` nombre del archivo dinámico de salida.

## App

Parámetros:
* `pathToStaticNFile.csv`: nombre del path al archivo estático de entrada.
* `pathToDynamicNFile.csv` nombre del path al archivo dinámico de entrada.