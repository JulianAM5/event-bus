package uabc.julian.componentes;

import uabc.julian.bus.EventBus;
import uabc.julian.eventos.BandaTocandoEvent;

/** Suscriptor de BandaTocandoEvent. */
public class SistemaDeSonido {
    private final EventBus bus;

    public SistemaDeSonido(EventBus bus) {
        this.bus = bus;
    }

    public void iniciar() {
        bus.suscribir(BandaTocandoEvent.class,
                e -> System.out.println("[SONIDO] Ajustando ecualizador para: " + e.nombreCancion()));
    }
}
