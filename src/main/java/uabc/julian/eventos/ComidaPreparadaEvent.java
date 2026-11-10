package uabc.julian.eventos;

public record ComidaPreparadaEvent(String pedidoId, String plato) implements Evento {
}
