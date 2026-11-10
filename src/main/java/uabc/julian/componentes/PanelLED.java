package uabc.julian.componentes;

import uabc.julian.bus.EventBus;
import uabc.julian.eventos.*;

/** Suscriptor de una selección de eventos; imprime notificaciones coloridas (ANSI) en consola. */
public class PanelLED {
    private static final String RESET = "\u001B[0m";
    private static final String VERDE = "\u001B[92m";
    private static final String AMARILLO = "\u001B[93m";
    private static final String CIAN = "\u001B[96m";
    private static final String MAGENTA = "\u001B[95m";

    private final EventBus bus;

    public PanelLED(EventBus bus) {
        this.bus = bus;
    }

    public void iniciar() {
        bus.suscribir(PedidoRealizadoEvent.class,
                e -> mostrar(CIAN, "Nuevo pedido " + e.pedidoId() + " en la Mesa " + e.mesaId() + ": " + e.items()));
        bus.suscribir(BebidaServidaEvent.class,
                e -> mostrar(AMARILLO, "¡Nueva bebida servida en la Mesa " + e.mesaId() + "! (" + e.bebida() + ")"));
        bus.suscribir(ComidaPreparadaEvent.class,
                e -> mostrar(VERDE, "¡Comida lista! " + e.plato() + " del " + e.pedidoId()));
        bus.suscribir(BandaTocandoEvent.class,
                e -> mostrar(MAGENTA, "[musica] " + e.nombreBanda() + " toca \"" + e.nombreCancion()
                        + "\" (" + e.duracion() + " s)"));
    }

    private void mostrar(String color, String mensaje) {
        System.out.println(color + "[PANEL LED] " + mensaje + RESET);
    }
}
