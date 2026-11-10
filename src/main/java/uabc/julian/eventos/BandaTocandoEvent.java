package uabc.julian.eventos;

/** @param duracion duración de la canción en segundos */
public record BandaTocandoEvent(String nombreBanda, String nombreCancion, int duracion) implements Evento {
}
