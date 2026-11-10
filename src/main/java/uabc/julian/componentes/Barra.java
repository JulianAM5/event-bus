package uabc.julian.componentes;

import uabc.julian.bus.EventBus;
import uabc.julian.bus.Suscriptor;
import uabc.julian.eventos.BebidaServidaEvent;
import uabc.julian.eventos.PedidoRealizadoEvent;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** Suscriptor de PedidoRealizadoEvent y publicador de BebidaServidaEvent. */
public class Barra {
    private static final Set<String> BEBIDAS = Set.of("cerveza", "refresco", "vino", "agua", "pocion");
    private static final int SEGUNDOS_PREPARACION = 2;

    private final EventBus bus;
    private final ScheduledExecutorService hilo = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "barra");
        t.setDaemon(true);
        return t;
    });
    private final Suscriptor<PedidoRealizadoEvent> manejador = this::alRecibirPedido;

    public Barra(EventBus bus) {
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
            if (BEBIDAS.contains(item.toLowerCase())) {
                System.out.println("[BARRA] Preparando " + item + " para " + pedido.mesaId());
                hilo.schedule(() -> bus.publicar(new BebidaServidaEvent(pedido.mesaId(), item)),
                        SEGUNDOS_PREPARACION, TimeUnit.SECONDS);
            }
        }
    }
}
