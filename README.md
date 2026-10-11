# SGMMS — Sistema de Gestión y Monitoreo de Movilidad y Seguridad

Proyecto académico de **Algoritmos y Programación II**, Universidad Icesi, semestre 2026-2.

## Descripción

Prototipo en Java para registrar incidentes de movilidad y seguridad, registrar vehículos de atención, asignar recursos compatibles, consultar registros y resolver incidentes. Incluye estructuras de datos y algoritmos implementados para la asignatura.

## Tecnologías

* Java 17
* Maven
* JUnit Jupiter (JUnit 5)
* Interfaz de consola
* Arquitectura MVC

## Organización del proyecto

```text
src/main/java/com/icesi/sgmms/
├── Main.java
├── algorithms/
│   ├── SearchingAlgorithms.java
│   └── SortingAlgorithms.java
├── controller/
│   └── SGMMSController.java
├── exceptions/
│   └── InvalidAssignmentException.java
├── model/
│   ├── Incident.java
│   ├── Vehicle.java
│   └── enums/
│       ├── IncidentStatus.java
│       ├── IncidentType.java
│       ├── Severity.java
│       ├── VehicleStatus.java
│       └── VehicleType.java
├── structures/
│   ├── LinkedList.java
│   └── Node.java
└── view/
    └── ConsoleView.java
```

## Ejecutar

Desde IntelliJ IDEA, ejecutar el método `main` de `com.icesi.sgmms.Main`.

O desde la raíz del proyecto:

```powershell
mvn clean compile
java -cp target/classes com.icesi.sgmms.Main
```

## Pruebas unitarias

```powershell
mvn clean test
```

Consultar los resultados reales en `target/surefire-reports/`. No registrar un total de pruebas aprobadas sin comprobar la salida de Maven.

## Funcionalidades del menú

1. Registrar incidente
2. Registrar vehículo
3. Buscar incidente
4. Buscar vehículo
5. Asignar vehículo a incidente
6. Resolver incidente
7. Consultar estadísticas básicas
8. Salir

### Compatibilidad de asignaciones

|Tipo de incidente|Recurso admitido|
|-|-|
|ACCIDENT|AMBULANCE|
|ROBBERY|PATROL|
|FIRE|FIRETRUCK|

La asignación exige que el vehículo esté disponible. La operación de resolución devuelve el vehículo asignado al estado `AVAILABLE`.

## Estructuras y algoritmos: análisis de complejidad

Las siguientes cotas se basan en una **lista simplemente enlazada con acceso por índice lineal**. Confirmar contra la implementación final.

|Operación|Tiempo|Espacio auxiliar|
|-|-|-|
|`LinkedList.get(i)`|O(n)|O(1)|
|`LinkedList.set(i, value)`|O(n)|O(1)|
|`LinkedList.remove(i)`|O(n)|O(1)|
|`LinkedList.size()`|O(1), si almacena contador|O(1)|
|`LinkedList.isEmpty()`|O(1)|O(1)|
|`LinkedList.add(value)`|O(n) sin referencia a cola; O(1) con cola|O(1)|
|Insertion sort con `get`/`set` indexados|O(n³) en peor caso|O(1)|
|Búsqueda binaria con `get` indexado|O(n log n)|O(1), si iterativa|
|Buscar incidente/vehículo por ID|O(n)|O(1)|
|Registrar incidente/vehículo|O(n) por comprobación de duplicados; puede sumar O(n) de inserción|O(1) auxiliar|
|Calcular puntuación de incidente|O(1)|O(1)|

**Importante:** la búsqueda binaria requiere datos ordenados por el mismo criterio usado en la comparación. Una lista enlazada no proporciona acceso aleatorio O(1).

## Modelo MVC

* **Model:** `Incident`, `Vehicle`, enumeraciones y reglas de negocio.
* **View:** `ConsoleView`.
* **Controller:** `SGMMSController`, que coordina los registros y las operaciones.

## Entregables de APO II 

* Código fuente del modelo, estructuras y algoritmos.
* Pruebas unitarias JUnit.
* Diseño de casos de prueba.
* Diagrama UML de clases y archivo editable.

## Control de versiones

Repositorio: https://github.com/Ju4Cr0/TareaIntegradoraApo

## Limitaciones y verificaciones pendientes

* El prototipo actual utiliza una vista de consola; no se afirma que implemente todos los escenarios gráficos del enunciado integrador.
* 

