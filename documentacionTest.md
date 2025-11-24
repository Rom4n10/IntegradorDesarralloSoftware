# 🧪 Estrategia de Testing - Mutant Detector

Este proyecto cuenta con una cobertura de código superior al **80%**, verificada automáticamente con JaCoCo durante el build.

## 1. Tests Unitarios (`MutantDetectorTest`)
Se probó exhaustivamente la lógica del algoritmo de detección de mutantes de forma aislada.
- **Casos Positivos:** Mutantes horizontales, verticales, diagonales y mixtos.
- **Casos Negativos:** Humanos sin secuencias o con solo una.
- **Casos Borde (Robustez):** - Caracteres inválidos (Validación interna).
  - Filas nulas dentro de la matriz.
  - Matrices de gran tamaño (Performance check).

## 2. Tests de Servicios (`MutantServiceTest`)
Se utilizaron **Mocks** (Mockito) para aislar la lógica de negocio de la base de datos.
- Verificación de cálculo de Hash.
- Verificación de lógica de Caché (si existe en DB, no re-calcula).
- Verificación de guardado en DB si es un ADN nuevo.

## 3. Tests de Integración (`MutantControllerTest`)
Se utilizó **MockMvc** para simular peticiones HTTP reales al controlador.
- Validación de códigos de estado HTTP (200, 403, 400).
- Validación de DTOs y constraints (`@ValidDnaSequence`).
- Verificación de respuestas JSON correctas.

## 4. Tests de Estadísticas (`StatsServiceTest`)
Verificación matemática de los cálculos de ratio.
- Casos de división por cero (0 humanos).
- Ratios decimales y exactos.

## Reporte de Cobertura
Para generar el reporte visual:
```bash
./gradlew test jacocoTestReport
