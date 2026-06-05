package analisis;

import modelo.ActividadAcademica;
import modelo.Curso;
import modelo.EstadoActividad;
import modelo.Prioridad;

import java.util.List;

public class ServicioEntropia {
    public ResultadoEntropia calcular(List<Curso> cursos) {
        int puntaje = 0;
        int total = 0;

        for (Curso curso : cursos) {
            for (ActividadAcademica actividad : curso.getActividades()) {
                total++;
                puntaje += puntajeActividad(actividad);
            }
        }

        if (total == 0) {
            return new ResultadoEntropia("baja", 0,
                    "No hay actividades registradas; la incertidumbre academica es nula.");
        }
        if (puntaje <= 5) {
            return new ResultadoEntropia("baja", puntaje,
                    "La entropia academica es baja: hay pocas senales de desorden en las actividades.");
        }
        if (puntaje <= 12) {
            return new ResultadoEntropia("media", puntaje,
                    "La entropia academica es media: existen tareas pendientes o importantes que requieren organizacion.");
        }
        return new ResultadoEntropia("alta", puntaje,
                "La entropia academica es alta: hay acumulacion de tareas vencidas, urgentes o sin planificacion clara.");
    }

    private int puntajeActividad(ActividadAcademica actividad) {
        int puntaje = 0;
        if (actividad.estaVencida()) {
            puntaje += 3;
        }
        if (actividad.getPrioridad() == Prioridad.ALTA) {
            puntaje += 2;
        } else if (actividad.getPrioridad() == Prioridad.MEDIA) {
            puntaje += 1;
        }
        if (actividad.getEstado() == EstadoActividad.INICIO) {
            puntaje += 1;
        } else if (actividad.getEstado() == EstadoActividad.PROCESO) {
            puntaje += 1;
        }
        if (!actividad.tieneFecha()) {
            puntaje += 1;
        }
        return puntaje;
    }
}
