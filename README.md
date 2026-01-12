# Trabajo de Consulta: EDA, Limpieza de Datos y Herramientas de IA

## Tabla de Contenidos
1. [Definición del Análisis Exploratorio de Datos (EDA)](#1-definición-del-análisis-exploratorio-de-datos-eda)
2. [Objetivos del EDA](#2-objetivos-del-eda)
3. [Fases del EDA: Enfoque en Limpieza de Datos](#3-fases-del-eda-enfoque-en-limpieza-de-datos)
4. [Herramientas del EDA](#4-herramientas-del-eda)
5. [Caso Práctico con IA: Dataset de Películas](#5-caso-práctico-con-ia-dataset-de-películas)
6. [Referencias](#referencias)

---

## Objetivo
Investigar y comprender los conceptos fundamentales del Análisis Exploratorio de Datos (EDA), con un enfoque especial en las fases de limpieza de datos y la integración de herramientas de Inteligencia Artificial para optimizar este proceso.

## Indicaciones
Responde a los siguientes puntos de manera clara y fundamentada. Utiliza ejemplos prácticos y referencias para apoyar tus respuestas.

## Aspectos a Investigar y Desarrollar

### 1. Definición del Análisis Exploratorio de Datos (EDA)

El **Análisis Exploratorio de Datos** (EDA, por sus siglas en inglés) es un enfoque inicial de análisis de datos cuyo objetivo es descubrir patrones, detectar anomalías, probar hipótesis y verificar supuestos a través de resúmenes estadísticos y representaciones gráficas. Se caracteriza por ser un proceso interactivo y visual que ayuda a los analistas a comprender la estructura y las características principales de los datos antes de aplicar modelos predictivos o inferenciales.

#### Importancia del EDA

- Proporciona una comprensión profunda de la estructura y características de los datos
- Permite identificar problemas de calidad que podrían afectar análisis posteriores
- Facilita la detección de relaciones entre variables
- Guía la selección de técnicas analíticas apropiadas

#### Ejemplos de su propósito

- **Datos de ventas**: El EDA revelaría patrones estacionales, productos más vendidos y regiones de mayor rendimiento
- **Datos médicos**: Ayudaría a identificar correlaciones entre síntomas y diagnósticos
- **Datos financieros**: Permitiría detectar transacciones anómalas o tendencias de mercado

---

### 2. Objetivos del EDA

Los principales objetivos del Análisis Exploratorio de Datos son:

1. **Comprender la estructura de los datos**: Identificar tipos de variables, dimensiones del dataset y organización general

2. **Evaluar la calidad de los datos**: Detectar valores faltantes, duplicados, inconsistencias y errores

3. **Identificar patrones y tendencias**: Descubrir relaciones, agrupaciones naturales y comportamientos recurrentes

4. **Detectar anomalías y valores atípicos**: Reconocer observaciones que se desvían significativamente del patrón general

5. **Formular hipótesis**: Generar preguntas de investigación basadas en los hallazgos preliminares

6. **Preparar los datos para análisis avanzados**: Asegurar que los datos estén limpios y en formato adecuado para modelado

---

### 3. Fases del EDA: Enfoque en Limpieza de Datos (Hecho con Gemini)

#### Flujo General del EDA

El Análisis Exploratorio de Datos (EDA) no es un paso lineal, sino un ciclo iterativo. Sin embargo, su estructura lógica suele seguir estos pasos:

1. **Entendimiento del Negocio y Datos**: Comprender qué significan las variables y qué problema se busca resolver.

2. **Carga de Datos**: Importación del dataset desde diversas fuentes (CSV, SQL, API).

3. **Limpieza de Datos (Data Cleaning)**: El proceso de filtrar, corregir y estandarizar los datos crudos. *(Nuestro foco principal)*

4. **Análisis Univariable**: Estudiar cada variable por separado (distribuciones, medias, modas).

5. **Análisis Bivariable/Multivariable**: Buscar relaciones, correlaciones y patrones entre variables.

6. **Comunicación de Hallazgos**: Visualización y reporte de insights.

---

#### Investigación Detallada: La Fase de Limpieza de Datos

La limpieza de datos consume comúnmente entre el 60% y el 80% del tiempo de un proyecto de datos. Su objetivo es garantizar la calidad (integridad, validez, consistencia y precisión) antes de aplicar cualquier modelo estadístico.

A continuación, se documenta la ejecución de cada subproceso clave:

##### A. Detectar y Manejar Valores Faltantes (Missing Values)

Los datos faltantes (NaN, null, None) pueden introducir sesgos severos.

**Detección:**

Se utilizan mapas de calor (heatmaps) o conteos sumarios para visualizar la distribución de los datos faltantes. Es vital entender por qué faltan.

**Estrategias de Tratamiento:**

**Eliminación (Dropping):**
- **Filas**: Se eliminan si el registro tiene demasiados campos vacíos y no aporta información.
- **Columnas**: Se elimina la variable completa si tiene un porcentaje de faltantes muy alto (ej. >50-60%) y no es crítica.

**Imputación (Relleno):**
- **Numérica Simple**: Rellenar con la Media (si la distribución es normal) o la Mediana (si hay sesgos o outliers, ya que es más robusta).
- **Categórica**: Rellenar con la Moda (el valor más frecuente) o crear una nueva categoría llamada "Desconocido".
- **Avanzada**: Usar algoritmos como K-Nearest Neighbors (KNN) para estimar el valor basándose en registros similares, o interpolación en series temporales (rellenar con el valor anterior o siguiente).

##### B. Eliminar Duplicados

La duplicidad infla las métricas de conteo y sesga las estadísticas descriptivas.

**Importancia de la Unicidad:**
- Asegura que cada observación sea independiente.
- Evita el sobreajuste (overfitting) en modelos de Machine Learning, donde el modelo memoriza datos repetidos en lugar de aprender patrones.

**Ejecución:**

Se debe identificar la "llave primaria" o el conjunto de columnas que definen la unicidad (ej. ID_Cliente + Fecha_Transaccion). Se busca filas idénticas y se mantiene generalmente la primera o la última ocurrencia, eliminando el resto.

##### C. Corregir Tipos de Datos (Casting)

Los datos a menudo se cargan con formatos incorrectos (ej. números interpretados como texto "1,000" o fechas como "2023-01-01" en formato string).

**Procesos Comunes:**
- **Numéricos**: Convertir strings a int o float. Esto a menudo requiere eliminar símbolos de moneda ($, €) o separadores de miles antes de la conversión.
- **Fechas**: Convertir objetos a datetime. Esto habilita la extracción de características temporales (día de la semana, mes, año, diferencia de tiempo).
- **Categóricos**: Convertir variables de texto con baja cardinalidad (pocas opciones únicas, como "Sí/No", "Rojo/Verde/Azul") al tipo category. Esto ahorra memoria y acelera el procesamiento.

##### D. Normalizar y Limpiar Texto

El texto sucio es una de las mayores fuentes de inconsistencia (ej. "Mexico", "mexico ", "Méxicö").

**Técnicas de Unificación:**
- **Casing**: Convertir todo a minúsculas o mayúsculas para que "Data" y "data" sean tratados como iguales.
- **Trimming**: Eliminar espacios en blanco al inicio y al final (strip), y reducir múltiples espacios internos a uno solo.
- **Eliminación de caracteres especiales**: Quitar acentos, puntuación o símbolos no alfanuméricos si no son relevantes.
- **Corrección de Typos**: Usar distancias de edición (como Levenshtein) para agrupar palabras mal escritas con su forma correcta.

##### E. Manejo de Valores Atípicos (Outliers)

Los outliers son puntos de datos que difieren significativamente de otras observaciones. Pueden ser errores de medición o variaciones naturales extremas.

**Identificación:**

- **Método del Rango Intercuartílico (IQR)**: Se define un rango "normal". Los valores fuera de los "bigotes" del diagrama de caja (Boxplot) son candidatos a outliers.
  - Lim_Inferior = Q1 − 1.5 × IQR
  - Lim_Superior = Q3 + 1.5 × IQR

- **Z-Score**: Cuántas desviaciones estándar se aleja un punto de la media. Un |Z| > 3 suele considerarse atípico.

**Decisión sobre Tratamiento:**
- **Eliminar**: Solo si se confirma que es un error de entrada de datos (ej. Edad = 200 años) o un error técnico.
- **Transformar**: Aplicar logaritmos (log(x)) para reducir el impacto de valores extremos.
- **Capping (Winsorizing)**: Reemplazar los valores por encima del percentil 99 con el valor del percentil 99.
- **Conservar**: Si el outlier es real (ej. detección de fraudes), es la información más valiosa y no debe tocarse.

##### F. Renombrar Columnas

Tener nombres de columnas claros facilita la escritura de código y la legibilidad del análisis.

**Estándares de Ejecución:**
- **Snake Case**: Reemplazar espacios por guiones bajos (ej. Fecha de Venta → fecha_venta).
- **Eliminar caracteres especiales**: Quitar tildes, paréntesis o signos (%, $) de los nombres.
- **Significado**: Cambiar nombres crípticos (X01, Var_2) por nombres descriptivos (edad, ingreso_anual).

---

### 4. Herramientas del EDA

#### Estadística Descriptiva

La estadística descriptiva juega un papel fundamental en el EDA, permitiendo resumir y comprender las características principales de los datos.

**Medidas de Tendencia Central:**
- **Media**: Promedio aritmético de los valores
- **Mediana**: Valor central que divide los datos en dos mitades iguales
- **Moda**: Valor que aparece con mayor frecuencia

**Medidas de Dispersión:**
- **Desviación estándar**: Mide la variabilidad de los datos respecto a la media
- **Varianza**: Cuadrado de la desviación estándar
- **Rango intercuartílico (IQR)**: Diferencia entre el tercer y primer cuartil

#### Visualizaciones Gráficas

Las visualizaciones son esenciales para identificar patrones y anomalías:

- **Histogramas**: Muestran la distribución de frecuencias de una variable numérica
- **Diagramas de caja (Boxplots)**: Visualizan la distribución, mediana, cuartiles y outliers
- **Gráficos de dispersión (Scatter plots)**: Revelan relaciones entre dos variables numéricas
- **Gráficos de barras**: Comparan valores entre categorías
- **Matrices de correlación**: Muestran relaciones lineales entre múltiples variables

---

### 5. Caso Práctico con IA: Dataset de Películas

#### Herramientas IA

Para este ejercicio se utilizarán herramientas como **Julius AI** y **Gemini** para realizar el análisis exploratorio.

#### Dataset

Se utilizará el archivo de datos de **películas (Movies)** disponible en:
- [Enlace al Dataset (Google Drive)](https://drive.google.com/file/d/1uHvGXxrcULwZM0QiKZeCpu94GGlcHhiG/view?usp=drive_link)

**Columnas disponibles:** `adult`, `belongs_to_collection`, `budget`, `genres`, `homepage`, `id`, `imdb_id`, `original_language`, `original_title`, `overview`, `popularity`, `poster_path`, `production_companies`, `production_countries`, `release_date`, `revenue`, `runtime`, `spoken_languages`, `status`, `tagline`, `title`, `video`, `vote_average`, `vote_count`, `keywords`, `cast`, `crew`, `ratings`.

---

#### Actividad - EDA en 3 Fases (en este ejemplo Gemini lo hizo en Python)

##### Fase 1: Resumen y Limpieza (Vista General) - *Punto Principal*

**1. Carga y Resumen General**

Primero, cargamos los datos y generamos una "radiografía" inicial para identificar los problemas.
```python
import pandas as pd
import numpy as np

# Carga del dataset (detectando separador ';')
try:
    df = pd.read_csv('pi_movies_small.xlsx - pi_movies_small.csv', sep=';')
except:
    df = pd.read_csv('pi_movies_small.xlsx - pi_movies_small.csv')

# Resumen General
print("--- ESTADO INICIAL ---")
print(f"Dimensiones: {df.shape}")
print(df.info())
print("\n--- EJEMPLO DE DATOS CRUDOS ---")
print(df.head(2))
```

**Diagnóstico Inicial:**
- **Estructura**: El dataset contiene columnas con tipos de datos mezclados (ej. release_date y budget aparecen como objetos o números no estandarizados).
- **Nulos**: Se detectan valores faltantes en campos como belongs_to_collection y tagline.
- **Formato**: Columnas de texto pueden tener espacios extra e id es un nombre ambiguo.

**2. Ejecución de los 6 Pasos de Limpieza (Documentación)**

A continuación, se detalla cómo la IA resolvió cada uno de los problemas de calidad solicitados.

**A. Detección y Manejo de Nulos**

Identificamos la cantidad de nulos por columna.

**Estrategia Aplicada:**
- **Numéricos** (budget, revenue): Se rellenaron los huecos con 0, asumiendo que la falta de dato indica ausencia de presupuesto/ingresos reportados.
- **Texto** (overview, tagline): Se rellenaron con "Desconocido" o vacíos para no perder la fila.
- **Críticos** (release_date, title): Si faltan, se eliminan las filas, ya que una película sin fecha ni título no es útil para el análisis temporal.
```python
# Conteo de nulos
null_counts = df.isnull().sum()
print(f"Nulos encontrados (Top 5):\n{null_counts[null_counts > 0].head(5)}")

# Tratamiento
df.dropna(subset=['release_date', 'title'], inplace=True) # Eliminar críticos
df.fillna({'budget': 0, 'revenue': 0, 'overview': ''}, inplace=True) # Imputar
print("-> Nulos críticos eliminados y valores numéricos imputados con 0.")
```

**B. Eliminación de Duplicados**

Se busca asegurar que no haya registros idénticos que inflen los resultados.

**Acción:** Se buscaron filas completamente idénticas y se eliminaron.
```python
duplicados = df.duplicated().sum()
print(f"Duplicados encontrados: {duplicados}")
if duplicados > 0:
    df.drop_duplicates(inplace=True)
    print("-> Duplicados eliminados.")
```

**C. Corrección de Tipos de Datos**

Las columnas críticas no tenían el formato correcto para operar matemáticamente.

**Corrección:**
- **release_date**: Convertido de texto a objeto datetime (permite extraer año/mes).
- **budget, revenue, popularity**: Forzados a tipo numérico (float o int). Los errores de conversión se transformaron en NaN y luego en 0.
```python
# Conversión
df['release_date'] = pd.to_datetime(df['release_date'], errors='coerce')
cols_numericas = ['budget', 'revenue', 'popularity', 'vote_average']
for col in cols_numericas:
    df[col] = pd.to_numeric(df[col], errors='coerce').fillna(0)

print("-> Fechas y números estandarizados.")
```

**D. Limpieza de Texto**

El texto suele venir sucio con espacios al inicio/final o mayúsculas inconsistentes.

**Acción:** Se aplicó strip() a todas las columnas de texto para quitar espacios en blanco sobrantes.
```python
# Normalización de texto
df_obj = df.select_dtypes(['object'])
df[df_obj.columns] = df_obj.apply(lambda x: x.str.strip())
print("-> Espacios en blanco eliminados de columnas de texto.")
```

**E. Detección de Outliers (Valores Atípicos)**

Analizamos la columna revenue (ingresos) para ver valores extremos.

**Método:** Rango Intercuartílico (IQR).

**Resultado:** Se identificaron películas con ingresos extremadamente altos (blockbusters) que se salen de la norma estadística habitual. **Nota:** En cine, estos outliers suelen ser datos reales y valiosos, no errores.
```python
Q1 = df['revenue'].quantile(0.25)
Q3 = df['revenue'].quantile(0.75)
IQR = Q3 - Q1
outliers = df[(df['revenue'] < (Q1 - 1.5 * IQR)) | (df['revenue'] > (Q3 + 1.5 * IQR))]
print(f"-> Detectados {len(outliers)} outliers en 'revenue' (probablemente éxitos de taquilla).")
```

**F. Renombrado de Columnas**

Para evitar conflictos con palabras reservadas (como id en Python) y mejorar la claridad.

**Cambio:** id → movie_id.
```python
df.rename(columns={'id': 'movie_id'}, inplace=True)
print("-> Columna 'id' renombrada a 'movie_id'.")
```

**Resumen Final del Dataset Limpio**
```python
print("\n--- ESTADO FINAL ---")
print(df.info())
```

(El output de este código mostraría un DataFrame sin nulos en columnas clave, con fechas tipo datetime64 y columnas numéricas tipo float/int, listo para el EDA visual).

---

##### Fase 2: Análisis Univariable (Caso de Estudio)

Para este análisis profundo, seleccioné la variable **vote_average** (Promedio de Votos) y **budget** (Presupuesto), ya que ofrecen perspectivas interesantes sobre la calidad percibida y la inversión financiera.

**1. Estadísticas Descriptivas**

Aquí tienes los datos clave de las variables seleccionadas:

| Estadística | Budget ($) | Revenue ($) | Vote Average (0-10) | Runtime (min) |
|-------------|------------|-------------|---------------------|---------------|
| Conteo      | 95         | 95          | 95                  | 95            |
| Promedio    | 3.74 M     | 17.3 M      | 5.43                | 98.6          |
| Mínimo      | 0          | 0           | 0.0                 | 0             |
| Máximo      | 130 M      | 847 M       | 9.5                 | 360           |
| Mediana     | 0          | 0           | 6.0                 | 98.0          |

**Interpretación de budget:**
- La mediana es 0, lo que indica que más del 50% de las películas en esta muestra pequeña no tienen datos de presupuesto registrados (valor 0).
- Existe una desviación estándar enorme (19.2 M), sugiriendo una variabilidad extrema entre películas independientes de bajo costo y grandes producciones.

**Interpretación de vote_average:**
- El promedio es 5.4, lo que sugiere una calidad "media" general en la muestra.
- El rango va de 0 a 9.5, cubriendo todo el espectro desde películas sin votos o muy malas hasta obras maestras aclamadas.

**2. Gráficos de Distribución**

![Histograma y diagrama de caja de Vote Average](https://github.com/user-attachments/assets/da8d77e7-093c-4a01-9601-2b322e7508be)

![Histograma y diagrama de caja de Budget](https://github.com/user-attachments/assets/37caa441-7b9c-4f0a-851d-3e43a4a2dda8)

**Interpretación de los gráficos:**

- **Gráfico de barras (Histograma)**: Muestra cómo se distribuyen los valores de la variable. Por ejemplo, podemos ver cuántas películas tienen calificaciones entre 5-6, 6-7, etc.

- **Gráfico de cajas**: Muestra visualmente el valor mínimo, el 25% de los datos (Q1), la mediana (línea central), el 75% de los datos (Q3) y el valor máximo. Los puntos fuera de las "cajas" son los valores atípicos.

---

##### Fase 3: Análisis Bivariable / Multivariable (Relaciones)

Exploramos cómo se relacionan estas variables entre sí.

**1. Matriz y grafico de Correlación**

<img width="800" height="600" alt="image" src="https://github.com/user-attachments/assets/b2fbc5a1-0279-4de2-a569-b0e1c480d00d" />


Calculé la correlación de Pearson para entender la fuerza de las relaciones lineales:

| Relación | Correlación (r) | Interpretación |
|----------|-----------------|----------------|
| Budget vs Revenue | 0.97 | **Correlación Positiva Muy Fuerte**. Como era de esperarse, a mayor inversión, mayor retorno en taquilla (en esta muestra). |
| Budget vs Popularity | 0.79 | **Correlación Fuerte**. Las películas más caras tienden a ser más populares. |
| Vote Average vs Popularity | 0.27 | **Correlación Débil**. Que una película sea popular no garantiza que tenga buena calificación, y viceversa. |
| Runtime vs Vote Average | 0.28 | **Correlación Débil**. La duración no influye determinantemente en la calificación. |

**2. Gráficos de dispersión**

![Gráfico de dispersión Budget vs Revenue](https://github.com/user-attachments/assets/3f4675d4-6ea5-4f26-853d-2a189c5d0999)

![Gráfico de dispersión Vote Average vs Popularity](https://github.com/user-attachments/assets/b4a3ad5f-1a21-4dda-9483-567cdb7644c3)

**Interpretación de los gráficos:**

- **Gráfico de Dispersión (Budget vs Revenue)**: Muestra una línea de tendencia clara ascendente. Las películas que gastan más, ganan más. Sin embargo, hay muchos puntos acumulados en la esquina inferior izquierda (0,0), lo que corresponde a las películas sin datos financieros registrados.

- **Gráfico de Dispersión (Vote Average vs Popularity)**: Es una nube de puntos más dispersa. Se observa que las películas con muy baja calificación (cerca de 0) tienen popularidad nula. A medida que aumenta el vote_average hacia 6-8, la popularidad varía enormemente, confirmando que una buena película no siempre es "famosa".

**Conclusión del Caso de Estudio:**

El análisis revela que, aunque el dinero (budget) es un excelente predictor del éxito comercial (revenue) y la visibilidad (popularity), no garantiza la calidad (vote_average). La calidad, medida por los votos, es una variable mucho más independiente que no se compra necesariamente con presupuesto.

---


### 6. Referencias

Client challenge. (s. f.). *MODULO EDA 2.0*. Scribd. https://es.scribd.com/document/58974067/MODULO-EDA-2-0

Guía práctica de introducción al análisis exploratorio de datos en R. (s. f.). *datos.gob.es*. 

    https://datos.gob.es/es/conocimiento/guia-practica-de-introduccion-al-analisis-exploratorio-de-datos-en-r

Telang, P. (2024, 15 de octubre). What is EDA? Importance, types & tools of exploratory data analysis. 

    *Business Analyst - TechCanvas*. https://businessanalyst.techcanvass.com/what-is-exploratory-data-analysis/
---

## Formato de Entrega

- **Formato:** Documento tipo Wiki sobre este archivo README.md
- **Fecha de Entrega:** 18 de diciembre 2025.
- **Evaluación:** Se priorizará la calidad y detalle en la **Fase 1 (Limpieza)**, la correcta ejecución del caso de estudio univariable y la evidencia del uso de IA.

