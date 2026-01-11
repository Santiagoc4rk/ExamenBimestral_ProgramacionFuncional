# Trabajo de Consulta: EDA, Limpieza de Datos y Herramientas de IA

## Tabla de Contenidos
1. [Definición del Análisis Exploratorio de Datos (EDA)](#1-definición-del-análisis-exploratorio-de-datos-eda)
2. [Objetivos del EDA](#2-objetivos-del-eda)
3. [Fases del EDA: Enfoque en Limpieza de Datos](#3-fases-del-eda-enfoque-en-limpieza-de-datos)
4. [Herramientas del EDA](#4-herramientas-del-eda)
5. [Caso Práctico con IA: Dataset de Películas](#5-caso-práctico-con-ia-dataset-de-películas)
6. [Referencias](#referencias)

---

## 1. Definición del Análisis Exploratorio de Datos (EDA)

El **Análisis Exploratorio de Datos (EDA)** es un enfoque analítico fundamental que permite examinar, resumir y visualizar conjuntos de datos para descubrir patrones, detectar anomalías, verificar supuestos y generar hipótesis antes de aplicar técnicas de modelado estadístico formal.

### Importancia del EDA

- Proporciona una comprensión profunda de la estructura y características de los datos
- Permite identificar problemas de calidad que podrían afectar análisis posteriores
- Facilita la detección de relaciones entre variables
- Guía la selección de técnicas analíticas apropiadas

### Ejemplos de su propósito

- **Datos de ventas**: El EDA revelaría patrones estacionales, productos más vendidos y regiones de mayor rendimiento
- **Datos médicos**: Ayudaría a identificar correlaciones entre síntomas y diagnósticos
- **Datos financieros**: Permitiría detectar transacciones anómalas o tendencias de mercado

---

## 2. Objetivos del EDA

Los principales objetivos del Análisis Exploratorio de Datos son:

1. **Comprender la estructura de los datos**: Identificar tipos de variables, dimensiones del dataset y organización general

2. **Evaluar la calidad de los datos**: Detectar valores faltantes, duplicados, inconsistencias y errores

3. **Identificar patrones y tendencias**: Descubrir relaciones, agrupaciones naturales y comportamientos recurrentes

4. **Detectar anomalías y valores atípicos**: Reconocer observaciones que se desvían significativamente del patrón general

5. **Formular hipótesis**: Generar preguntas de investigación basadas en los hallazgos preliminares

6. **Preparar los datos para análisis avanzados**: Asegurar que los datos estén limpios y en formato adecuado para modelado

---

## 3. Fases del EDA: Enfoque en Limpieza de Datos

### Flujo General del EDA

El proceso de EDA típicamente sigue estas etapas:

1. **Recolección de datos**
2. **Limpieza de datos** ⭐ **(FOCO PRINCIPAL)**
3. **Análisis univariable**
4. **Análisis bivariable y multivariable**
5. **Conclusiones y recomendaciones**

---

### 3.1 Detectar y Manejar Valores Faltantes

#### ¿Qué son?
Valores ausentes o nulos (null, None, vacíos) en el dataset que pueden comprometer el análisis.

#### Estrategias de manejo

**1. Eliminación**
- Remover filas o columnas con valores faltantes
- Usar cuando el porcentaje de nulos es bajo (<5%)
- Filtrar registros que no cumplan validaciones

**2. Imputación Simple**
- **Media**: Para datos numéricos sin outliers extremos
- **Mediana**: Para datos con distribución asimétrica
- **Moda**: Para datos categóricos
- **Valor por defecto**: Para campos opcionales

**3. Validación con Option**
- Usar tipos `Option[T]` para campos opcionales
- Transformar valores inválidos a `None`
- Aplicar `flatMap` para normalización

#### Ejemplo práctico en Scala
```scala
// Validar string no vacío
def isValidString(s: String): Boolean =
  s != null && s.trim.nonEmpty && !s.equalsIgnoreCase("null")

// Normalizar texto con Option
def normalizarTexto(txt: String): Option[String] =
  val limpio = txt.trim.replaceAll("\\s+", " ")
  if (limpio.isEmpty) None else Some(limpio)

// Filtrar registros con campos obligatorios válidos
val peliculasValidas = peliculas.filter { p =>
  isValidString(p.title) &&
  isValidString(p.overview) &&
  p.budget >= 0 &&
  p.revenue >= 0
}

// Contar nulos en una columna
def contarNulos(lista: List[Movie], extractor: Movie => String): Int =
  lista.count(m => !isValidString(extractor(m)))

val nulosOverview = contarNulos(peliculas, _.overview)
```

---

### 3.2 Eliminar Duplicados

#### Importancia

Los registros duplicados pueden:
- Sesgar análisis estadísticos
- Inflar métricas de conteo
- Generar correlaciones artificiales

#### Proceso de eliminación

1. Identificar duplicados exactos o parciales
2. Decidir criterios de unicidad (id, combinación de campos)
3. Mantener primera aparición o la más completa
4. Documentar número de duplicados eliminados

#### Ejemplo práctico en Scala
```scala
// Eliminar duplicados por ID
val peliculasUnicas = peliculas.distinctBy(_.id)

// Eliminar duplicados por combinación de campos
val crewUnico = crewMembers.distinctBy(c => (c.id, c.name, c.job))

// Contar duplicados antes de eliminar
val totalOriginal = peliculas.size
val totalUnicas = peliculasUnicas.size
val duplicados = totalOriginal - totalUnicas

println(s"Registros originales: $totalOriginal")
println(s"Registros únicos: $totalUnicas")
println(s"Duplicados eliminados: $duplicados")
```

---

### 3.3 Corregir Tipos de Datos

#### Objetivo
Asegurar que cada campo tenga el tipo de dato correcto para su contenido usando el sistema de tipos de Scala.

#### Conversiones comunes

- **String a numéricos**: `toInt`, `toDouble` con manejo de excepciones
- **String a fechas**: Parseo con validación de formato
- **Valores booleanos**: Validar "True"/"False" o convertir a Boolean
- **Option types**: Para campos opcionales

#### Beneficios

- Type safety en tiempo de compilación
- Previene errores en tiempo de ejecución
- Permite pattern matching
- Optimiza operaciones

#### Ejemplo práctico en Scala
```scala
// Modelo con tipos apropiados
case class MovieCompleta(
  adult: String,              // "True" o "False"
  budget: Double,             // Numérico >= 0
  id: Double,                 // Identificador único
  release_date: String,       // Formato: YYYY-MM-DD
  revenue: Double,            // Numérico >= 0
  runtime: Double,            // Minutos > 0
  vote_average: Double,       // 0.0 - 10.0
  vote_count: Double,         // >= 0
  title: String,              // Texto obligatorio
  overview: String            // Texto obligatorio
)

// Validar formato de fecha
def validarFecha(fecha: String): Boolean =
  fecha.matches("\\d{4}-\\d{2}-\\d{2}")

// Convertir con manejo de errores
val movieId = try {
  parts(idIndex).toInt
} catch {
  case _: Exception => 0
}

// Validar booleanos
def validarBoolean(s: String): Boolean =
  s == "True" || s == "False"

val peliculasValidas = peliculas.filter { p =>
  validarFecha(p.release_date) &&
  validarBoolean(p.adult) &&
  validarBoolean(p.video)
}
```

---

### 3.4 Normalizar y Limpiar Texto

#### Operaciones de limpieza

- **Espacios**: Eliminar espacios iniciales/finales y múltiples
- **Formato consistente**: Trim, lowercase cuando sea apropiado
- **Caracteres especiales**: Manejo de comillas, escapado
- **JSON parsing**: Limpiar strings JSON-like para parseo

#### Ejemplo práctico en Scala
```scala
// Normalizar texto básico
def normalizarTexto(txt: String): Option[String] =
  val limpio = txt.trim.replaceAll("\\s+", " ")
  if (limpio.isEmpty) None else Some(limpio)

// Preparar JSON para parseo
def prepararJSONParaParseo(crew: String): String =
  if (crew == null || crew.trim.isEmpty) return "[]"
  crew.trim
    .replaceAll("None", "null")
    .replaceAll("True", "true")
    .replaceAll("False", "false")
    .replace("\"", "\\\"")
    .replaceAll("(?<![a-zA-Z0-9])'|'(?![a-zA-Z0-9])", "\"")

// Normalizar un objeto completo
def normalizarCrewMember(c: Crew): Crew =
  c.copy(
    credit_id = c.credit_id.flatMap(normalizarTexto),
    department = c.department.flatMap(normalizarTexto),
    job = c.job.flatMap(normalizarTexto),
    name = c.name.flatMap(normalizarTexto),
    profile_path = c.profile_path.flatMap(normalizarTexto)
  )

// Aplicar a una lista
val crewNormalizado = crewList.map(normalizarCrewMember)
```

---

### 3.5 Manejo de Valores Atípicos (Outliers)

#### ¿Qué son?
Observaciones que se desvían significativamente del patrón general de los datos.

#### Métodos de detección

**Rango Intercuartílico (IQR)**
- Método robusto basado en cuartiles
- Valores fuera de Q1 - 1.5×IQR y Q3 + 1.5×IQR
- No asume distribución normal

#### Estrategias de tratamiento

- **Mantener**: Si son valores legítimos (películas blockbuster)
- **Eliminar**: Si son errores evidentes
- **Filtrado flexible**: Permitir outliers en algunas variables
- **Separar**: Analizar outliers independientemente

#### Ejemplo práctico en Scala
```scala
// Calcular cuartil
def calcularCuartil(ordenados: List[Double], percentil: Double): Double =
  if ordenados.isEmpty then return 0.0
  val pos = percentil * (ordenados.size - 1)
  val lower = ordenados(pos.toInt)
  val upper = if pos.toInt + 1 < ordenados.size 
              then ordenados(pos.toInt + 1) 
              else lower
  val fraction = pos - pos.toInt
  lower + fraction * (upper - lower)

// Obtener límites IQR
def obtenerLimitesIQR(datos: List[Double]): (Double, Double) =
  if datos.isEmpty || datos.size < 4 then (0.0, Double.MaxValue)
  else
    val sorted = datos.sorted
    val q1 = calcularCuartil(sorted, 0.25)
    val q3 = calcularCuartil(sorted, 0.75)
    val iqr = q3 - q1
    val limiteInferior = math.max(0, q1 - 1.5 * iqr)
    val limiteSuperior = q3 + 1.5 * iqr
    (limiteInferior, limiteSuperior)

// Aplicar filtro de outliers
val budgetsNoZero = peliculas.map(_.budget).filter(_ > 0)
val (limInf, limSup) = obtenerLimitesIQR(budgetsNoZero)

val sinOutliers = peliculas.filter { p =>
  p.budget == 0 || (p.budget >= limInf && p.budget <= limSup)
}

// Estrategia flexible: permitir máximo 1 outlier
val paso2 = paso1.filter { m =>
  val fueraDeRango = Seq(
    m.budget > 0 && (m.budget < limInfBudget || m.budget > limSupBudget),
    m.revenue > 0 && (m.revenue < limInfRev || m.revenue > limSupRev)
  ).count(identity)
  
  fueraDeRango <= 1  // Máximo 1 variable puede ser outlier
}
```

---

### 3.6 Renombrar Columnas

#### Propósitos

- Estandarizar nomenclatura (camelCase en Scala)
- Clarificar significado de campos
- Consistencia con convenciones del lenguaje
- Facilitar pattern matching

#### Buenas prácticas en Scala

- Usar camelCase para nombres de campos
- Nombres descriptivos pero concisos
- Evitar caracteres especiales
- Documentar con comentarios

#### Ejemplo práctico en Scala
```scala
// Case class con nombres claros y consistentes
case class Movie(
  movieId: Int,           // Antes: id
  title: String,          // Sin cambios
  originalTitle: String,  // Antes: original_title
  releaseDate: String,    // Antes: release_date
  budget: Double,         // Sin cambios
  revenue: Double,        // Sin cambios
  voteAverage: Double,    // Antes: vote_average
  voteCount: Double,      // Antes: vote_count
  runtime: Double         // Sin cambios (minutos)
)

// Transformar datos con nombres actualizados
def renombrarCampos(old: MovieCompleta): Movie =
  Movie(
    movieId = old.id.toInt,
    title = old.title,
    originalTitle = old.original_title,
    releaseDate = old.release_date,
    budget = old.budget,
    revenue = old.revenue,
    voteAverage = old.vote_average,
    voteCount = old.vote_count,
    runtime = old.runtime
  )
```

---

## 4. Herramientas del EDA

### 4.1 Estadística Descriptiva

La estadística descriptiva resume y describe las características principales de un conjunto de datos.

#### Medidas de Tendencia Central

**Media (promedio) - μ**
- Suma de todos los valores dividida por el número de observaciones
- Fórmula: `μ = Σx / n`
- Sensible a valores extremos
- Útil para distribuciones simétricas

**Mediana**
- Valor central cuando los datos están ordenados
- Robusta ante outliers
- Preferible para distribuciones asimétricas
- Divide el dataset en dos mitades iguales

**Moda**
- Valor que aparece con mayor frecuencia
- Útil para datos categóricos
- Puede haber múltiples modas (bimodal, multimodal)

#### Medidas de Dispersión

**Desviación Estándar - σ**
- Mide la dispersión promedio respecto a la media
- Fórmula: `σ = √[Σ(x - μ)² / n]`
- Mismas unidades que los datos originales
- Valores altos indican mayor variabilidad

**Varianza - σ²**
- Promedio de las desviaciones cuadradas respecto a la media
- Fórmula: `σ² = Σ(x - μ)² / n`
- Unidades al cuadrado

**Rango Intercuartílico (IQR)**
- Diferencia entre el tercer y primer cuartil (Q3 - Q1)
- Contiene el 50% central de los datos
- Robusto ante valores extremos

#### Ejemplo de cálculo en Scala
```scala
// Calcular estadísticas descriptivas
def calcularEstadisticas(datos: List[Double]): Map[String, Double] =
  if datos.isEmpty then Map.empty
  else
    val ordenados = datos.sorted
    val n = ordenados.size
    val media = datos.sum / n
    val varianza = datos.map(x => math.pow(x - media, 2)).sum / n
    val mediana = if n % 2 == 1 then ordenados(n / 2)
                  else (ordenados(n / 2 - 1) + ordenados(n / 2)) / 2.0
    
    Map(
      "min" -> ordenados.head,
      "max" -> ordenados.last,
      "media" -> media,
      "mediana" -> mediana,
      "desv_std" -> math.sqrt(varianza),
      "q1" -> calcularCuartil(ordenados, 0.25),
      "q3" -> calcularCuartil(ordenados, 0.75)
    )

// Usar las estadísticas
val statsRevenue = calcularEstadisticas(peliculas.map(_.revenue).filter(_ > 0))
println(s"Media: ${statsRevenue("media")}")
println(s"Mediana: ${statsRevenue("mediana")}")
println(s"Desv. Std: ${statsRevenue("desv_std")}")
```

---

### 4.2 Visualización Gráfica

#### Histogramas
- Muestran distribución de frecuencias
- Revelan forma de la distribución (normal, asimétrica)
- Permiten identificar modas múltiples
- Útiles para variables numéricas continuas

#### Diagramas de Caja (Boxplots)
- Resumen cinco estadísticas clave (mín, Q1, mediana, Q3, máx)
- Visualizan outliers claramente
- Permiten comparar distribuciones entre grupos
- Compactos y eficientes

#### Gráficos de Dispersión (Scatter plots)
- Muestran relación entre dos variables numéricas
- Revelan correlaciones y patrones
- Identifican clusters y outliers bivariables
- Base para análisis de regresión

#### Gráficos de Barras
- Representan frecuencias de categorías
- Comparan magnitudes entre grupos
- Útiles para variables categóricas

---

## 5. Caso Práctico con IA: Dataset de Películas

### Dataset de Películas

**Columnas disponibles (28 campos):**
```
adult, belongs_to_collection, budget, genres, homepage, id, imdb_id, 
original_language, original_title, overview, popularity, poster_path, 
production_companies, production_countries, release_date, revenue, 
runtime, spoken_languages, status, tagline, title, video, 
vote_average, vote_count, keywords, cast, crew, ratings
```

---

### Fase 1: Resumen y Limpieza (Vista General) ⭐ **PUNTO PRINCIPAL**

#### Herramientas recomendadas
- **Julius AI** (https://julius.ai)
- **Google Gemini** (https://gemini.google.com)
- **ChatGPT** con análisis de datos

#### Prompt sugerido para la IA
```
He cargado el dataset de películas "pi_movies_complete.csv" con 28 columnas:
adult, belongs_to_collection, budget, genres, homepage, id, imdb_id, 
original_language, original_title, overview, popularity, poster_path, 
production_companies, production_countries, release_date, revenue, 
runtime, spoken_languages, status, tagline, title, video, 
vote_average, vote_count, keywords, cast, crew, ratings.

Por favor, realiza un análisis exploratorio completo siguiendo estos pasos:

**PASO 1: RESUMEN GENERAL**
- Muestra las dimensiones del dataset (filas x columnas)
- Lista los tipos de datos de cada columna
- Muestra las primeras 5 filas del dataset
- Genera un resumen estadístico (describe)

**PASO 2: DETECCIÓN DE VALORES NULOS**
- Cuenta valores nulos por columna
- Calcula el porcentaje de nulos para cada campo
- Identifica cuáles son campos obligatorios vs opcionales
- Sugiere estrategias de imputación o eliminación

**PASO 3: ELIMINACIÓN DE DUPLICADOS**
- Busca registros duplicados por ID
- Identifica duplicados completos (todas las columnas)
- Reporta cuántos duplicados fueron encontrados
- Muestra ejemplos de duplicados si los hay

**PASO 4: CORRECCIÓN DE TIPOS DE DATOS**
- Verifica que budget, revenue, runtime sean numéricos
- Convierte release_date a formato fecha
- Valida que adult y video sean booleanos
- Identifica valores inválidos en cada campo

**PASO 5: LIMPIEZA DE TEXTO**
- Elimina espacios extra en title, original_title, overview
- Normaliza formato de campos de texto
- Identifica caracteres especiales o encoding incorrecto
- Muestra ejemplos antes/después de limpieza

**PASO 6: DETECCIÓN DE OUTLIERS**
- Usa método IQR para: budget, revenue, runtime, popularity
- Calcula límites inferior y superior para cada variable
- Cuenta cuántos outliers hay en cada campo
- Muestra los 5 valores más extremos de cada variable
- Sugiere si mantener o eliminar outliers

Documenta cada paso con:
- Estadísticas ANTES de la limpieza
- Operaciones realizadas
- Estadísticas DESPUÉS de la limpieza
- Número de registros afectados
```

#### Entregables de la Fase 1

📊 **Documentar obligatoriamente cada uno de los 6 pasos:**

**1. Resumen General**
```
Total de registros: X,XXX
Total de columnas: 28
Primeras 5 filas: [Captura de pantalla]
Tipos de datos: [Tabla con cada columna y su tipo]
```

**2. Valores Nulos**
```
Columna              | Nulos | Porcentaje | Estrategia
---------------------|-------|------------|------------------
title                |     X |      X.X%  | Eliminar registro
overview             |    XX |      X.X%  | Eliminar registro
budget               |   XXX |     XX.X%  | Mantener 0
revenue              |   XXX |     XX.X%  | Mantener 0
homepage             | X,XXX |     XX.X%  | Mantener vacío
...
```

**3. Duplicados**
```
Duplicados por ID: X registros
Duplicados completos: X registros
Acción tomada: [Descripción]
Registros finales: X,XXX
```

**4. Tipos de Datos**
```
Campo            | Tipo Original | Tipo Correcto | Valores Inválidos
-----------------|---------------|---------------|------------------
budget           | object        | float64       | X registros
revenue          | object        | float64       | X registros
release_date     | object        | datetime64    | X registros
adult            | object        | bool          | X registros
...
```

**5. Limpieza de Texto**
```
Ejemplos de limpieza en 'title':
ANTES: "  The Matrix   "
DESPUÉS: "The Matrix"

ANTES: "Star  Wars:  Episode  IV"
DESPUÉS: "Star Wars: Episode IV"

Total de registros limpiados: X,XXX
```

**6. Outliers Detectados**
```
Variable    | Q1      | Q3        | IQR       | Lím. Inf | Lím. Sup | Outliers
------------|---------|-----------|-----------|----------|----------|----------
budget      | XX.XXM  | XXX.XXM   | XX.XXM    | 0        | XXX.XXM  | XXX
revenue     | XX.XXM  | XXX.XXM   | XX.XXM    | 0        | X.XXB    | XXX
runtime     | XX min  | XXX min   | XX min    | XX min   | XXX min  | XX
popularity  | X.XX    | XX.XX     | XX.XX     | 0        | XXX.XX   | XXX

Decisión: Mantener outliers en budget y revenue (blockbusters legítimos)
          Eliminar outliers en runtime (errores de datos)
```

**💡 IMPORTANTE: Incluir capturas de pantalla de la IA ejecutando cada paso**

---

### Fase 2: Análisis Univariable (Caso de Estudio)

#### Variable seleccionada: `revenue` (Ingresos de la película)

#### Prompt para la IA
```
ANÁLISIS UNIVARIABLE PROFUNDO DE LA VARIABLE 'revenue'

Realiza un análisis estadístico y visual completo de la variable revenue 
(ingresos de películas) siguiendo estos pasos:

**1. ESTADÍSTICAS DESCRIPTIVAS COMPLETAS**
- Cuenta total de registros
- Valores faltantes o cero
- Mínimo, Máximo, Rango
- Media (promedio)
- Mediana
- Moda (si aplica)
- Cuartiles (Q1, Q2/Mediana, Q3)
- Rango Intercuartílico (IQR)
- Desviación estándar
- Varianza
- Coeficiente de variación

**2. ANÁLISIS DE DISTRIBUCIÓN**
- Asimetría (skewness): ¿La distribución está sesgada?
- Curtosis: ¿Hay colas pesadas o ligeras?
- ¿Porcentaje de películas con revenue = 0?
- ¿Cuántas películas superan 100M, 500M, 1B?

**3. VISUALIZACIONES REQUERIDAS**
a) Histograma con 30-50 bins
   - Incluir líneas verticales para media y mediana
   - Etiquetar ejes claramente
   
b) Boxplot horizontal
   - Identificar outliers visualmente
   - Mostrar Q1, Q2, Q3
   
c) Histograma con escala logarítmica (si hay muchos ceros)
   - Para visualizar mejor la distribución

d) Tabla de frecuencias por rangos
   - 0 - 10M, 10M - 50M, 50M - 100M, 100M - 500M, 500M+

**4. INTERPRETACIÓN**
Responde:
- ¿La distribución es normal, asimétrica positiva o negativa?
- ¿Qué porcentaje de películas tienen ingresos bajos (<10M)?
- ¿Cuáles son las películas con mayores ingresos (top 10)?
- ¿Hay necesidad de transformación logarítmica?
- ¿Qué insights se pueden extraer sobre el éxito comercial?

**5. VALORES EXTREMOS**
- Lista los 10 valores más altos
- Lista los 10 valores más bajos (excluyendo 0)
- ¿Los outliers son errores o blockbusters legítimos?
```

#### Interpretación esperada

La variable `revenue` típicamente presenta:

- **Distribución altamente asimétrica positiva** (skewed right)
- Mayoría de películas con ingresos bajos a moderados
- Pocas películas "blockbusters" con ingresos extraordinariamente altos
- Presencia de muchos ceros (películas sin información de ingresos)
- **Mediana significativamente menor que la media** debido a outliers
- Necesidad potencial de transformación logarítmica para normalizar

#### Entregables de la Fase 2

- Tabla completa de estadísticas descriptivas
- Histograma de distribución con interpretación
- Boxplot identificando outliers
- Análisis escrito de la distribución observada
- Recomendaciones para tratamiento de la variable

---

### Fase 3: Análisis Bivariable/Multivariable (Relaciones)

#### Prompt para la IA
