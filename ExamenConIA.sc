chat ayudame a hacer esto con progrmacion funcional en scala, haciendo lo que te pide ahi
def estudianteMasConsistente(estudiantes: List[Estudiante]): (String, Double) = {

  // 1. Filtrar estudiantes con al menos 3 notas
  val validos = estudiantes.filter(e => e.notas.length >= 3)

  // Si no hay válidos, devolvemos un mensaje y consistencia infinita
  if (validos.isEmpty) {
    ("No existen estudiantes válidos", Double.MaxValue)
  } else {

    // 2. Calcular la consistencia de cada estudiante:
    // diferencia entre nota máxima y mínima.
    // Lo hacemos a mano recorriendo la lista de notas.
    def consistencia(notas: List[Double]): Double = {
      val max = notas.foldLeft(Double.MinValue)((m, n) => if (n > m) n else m)
      val min = notas.foldLeft(Double.MaxValue)((m, n) => if (n < m) n else m)
      max - min
    }

    // 3. Encontrar al estudiante con menor consistencia
    def mejor(actual: Estudiante, mejorHastaAhora: Estudiante): Estudiante = {
      val cActual  = consistencia(actual.notas)
      val cMejor   = consistencia(mejorHastaAhora.notas)
      if (cActual < cMejor) actual else mejorHastaAhora
    }

    val ganador = validos.reduce((a, b) => mejor(a, b))
    val consist = consistencia(ganador.notas)

    (ganador.nombre, consist)
  }
}