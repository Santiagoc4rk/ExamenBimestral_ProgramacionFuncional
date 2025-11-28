case class Estudiante(nombre: String, notas: List[Double])
case class EstudianteConConsistencia(estudiante: Estudiante, consistencia: Double)

val estudiantes: List[Estudiante] = List(
  Estudiante("Ana",       List(8.5, 9.0, 9.2, 8.8, 9.1)),
  Estudiante("Juan",      List(7.5, 8.0, 7.8, 7.9, 8.1)),
  Estudiante("María",     List(9.5, 9.0, 8.9, 9.1, 9.2)),
  Estudiante("Carlos",    List(6.0, 7.0, 8.5, 5.5, 7.8)),
  Estudiante("Lucía",     List(9.0, 8.8, 9.2, 9.1, 8.9)),
  Estudiante("Pedro",     List(5.0, 6.0, 7.5, 7.8, 8.0)),
  Estudiante("Sofía",     List(8.8, 8.7, 8.9, 8.6, 8.8)),
  Estudiante("Diego",     List(7.2, 7.5, 7.8, 7.9, 7.0)),
  Estudiante("Elena",     List(9.1, 9.0, 9.3, 9.2, 9.4)),
  Estudiante("Raúl",      List(6.5, 6.8, 7.0, 7.2, 7.1)),
  Estudiante("Patricia",  List(8.0, 7.8, 8.2, 8.1, 8.0)),
  Estudiante("Fernando",  List(9.5, 8.5, 9.0, 8.8, 7.5)),
  Estudiante("Mónica",    List(8.2, 8.3, 8.1, 8.4, 8.3)),
  Estudiante("Héctor",    List(7.0, 7.2, 7.1, 7.3, 7.2)),
  Estudiante("Gabriela",  List(9.0, 9.1, 9.2, 9.0, 9.1)),
  Estudiante("Andrés",    List(6.8, 7.0, 6.9, 7.1, 7.0)),
  Estudiante("Valeria",   List(8.9, 9.0, 8.8, 8.9, 9.1)),
  Estudiante("Jorge",     List(7.5, 7.6, 7.4, 7.7, 7.6)),
  Estudiante("Carmen",    List(9.3, 9.2, 9.4, 9.5, 9.3)),
  Estudiante("Luis",      List(5.5, 6.0, 6.5, 6.8, 7.0)),
  Estudiante("Natalia",   List(8.0, 8.1, 8.2, 8.0, 8.1)),
  Estudiante("Sebastián", List(7.8, 8.0, 8.2, 7.9, 8.1))
)

def encontrarEstudianteMasConsistente(estudiantes: List[Estudiante]): Unit = {
  val estudiantesValidos = estudiantes.filter(_.notas.length >= 3)

  if (estudiantesValidos.isEmpty) {
    println("No hay estudiantes con al menos 3 calificaciones")
  } else {
    val estudiantesConConsistencia = estudiantesValidos.map { estudiante =>
      val diferencia = estudiante.notas.max - estudiante.notas.min
      EstudianteConConsistencia(estudiante, diferencia)
    }

    val resultado = estudiantesConConsistencia.minBy(_.consistencia)

    println(s"Estudiante mas consistente: ${resultado.estudiante.nombre}")
    println(f"Consistencia: ${resultado.consistencia}%.1f")
  }
}

encontrarEstudianteMasConsistente(estudiantes)

