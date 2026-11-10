package uabc.julian.extensiones;

import uabc.julian.bus.EventBus;
import uabc.julian.eventos.BandaTocandoEvent;

/**
 * Demostración de extensibilidad: nuevo suscriptor añadido SIN modificar Banda ni EventBus.
 */
public class SistemaDeHumo {
    private static final String CANCION_EPICA = "Through the Fire and Flames";

    private final EventBus bus;

    public SistemaDeHumo(EventBus bus) {
        this.bus = bus;
    }

    public void iniciar() {
        bus.suscribir(BandaTocandoEvent.class, e -> {
            if (CANCION_EPICA.equalsIgnoreCase(e.nombreCancion())) {
                System.out.println("[HUMO] ¡Canción épica! Activando máquinas de humo");
            }
        });
    }
}
