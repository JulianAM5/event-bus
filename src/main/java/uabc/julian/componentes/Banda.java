package uabc.julian.componentes;

import uabc.julian.bus.EventBus;
import uabc.julian.eventos.BandaTocandoEvent;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** Publicador: cada X segundos "toca" la siguiente canción de su repertorio. */
public class Banda {
    private final EventBus bus;
    private final String nombre;
    private final List<String> repertorio;
    private final int cadaSegundos;
    private final int duracionCancion;
    private final ScheduledExecutorService hilo = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r, "banda");
        t.setDaemon(true);
        return t;
    });
    private int indice = 0;

    public Banda(EventBus bus, String nombre, List<String> repertorio, int cadaSegundos, int duracionCancion) {
        this.bus = bus;
        this.nombre = nombre;
        this.repertorio = List.copyOf(repertorio);
        this.cadaSegundos = cadaSegundos;
        this.duracionCancion = duracionCancion;
    }

    public void iniciar() {
        hilo.scheduleAtFixedRate(this::tocar, 1, cadaSegundos, TimeUnit.SECONDS);
    }

    public void detener() {
        hilo.shutdownNow();
    }

    private void tocar() {
        String cancion = repertorio.get(indice++ % repertorio.size());
        bus.publicar(new BandaTocandoEvent(nombre, cancion, duracionCancion));
    }
}
