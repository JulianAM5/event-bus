package uabc.julian.eventos;

import java.util.List;

/** Un cliente hizo un pedido. Inmutable: la lista de items se copia de forma defensiva. */
public record PedidoRealizadoEvent(String mesaId, String pedidoId, List<String> items) implements Evento {
    public PedidoRealizadoEvent {
        items = List.copyOf(items);
    }
}
