package uabc.julian.componentes;

import uabc.julian.bus.EventBus;
import uabc.julian.bus.Suscriptor;
import uabc.julian.eventos.ComidaPreparadaEvent;
import uabc.julian.eventos.PedidoRealizadoEvent;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** Suscriptor de PedidoRealizadoEvent y publicador de ComidaPreparadaEvent. */
public class Cocina {
    private static final Set<String> PLATOS = Set.of("hamburguesa", "pizza", "nachos", "alitas", "ensalada");
    private static final int SEGUNDOS_PREPARACION = 5;

    private final EventBus bus;
    private final ScheduledExecutorService hilo = Executors.newScheduledThreadPool(2, r -> {
        Thread t = new Thread(r, "cocina");
        t.setDaemon(true);
        return t;
    });
    private final Suscriptor<PedidoRealizadoEvent> manejador = this::alRecibirPedido;

    public Cocina(EventBus bus) {
        this.bus = bus;
    }

    public void iniciar() {
        bus.suscribir(PedidoRealizadoEvent.class, manejador);
    }

    public void detener() {
        bus.desuscribir(PedidoRealizadoEvent.class, manejador);
        hilo.shutdownNow();
    }

    private void alRecibirPedido(PedidoRealizadoEvent pedido) {
        for (String item : pedido.items()) {
            if (PLATOS.contains(item.toLowerCase())) {
                System.out.println("[COCINA] Cocinando " + item + " (" + pedido.pedidoId() + ")");
                hilo.schedule(() -> bus.publicar(new ComidaPreparadaEvent(pedido.pedidoId(), item)),
                        SEGUNDOS_PREPARACION, TimeUnit.SECONDS);
            }
        }
    }
}
