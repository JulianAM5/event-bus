package uabc.julian.bus;

import uabc.julian.eventos.Evento;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Event Bus básico y seguro para hilos.
 * - suscribir(tipo, suscriptor)
 * - desuscribir(tipo, suscriptor)
 * - publicar(evento)
 *
 * Un suscriptor registrado a un tipo también recibe sus subtipos; por eso suscribirse a
 * {@code Evento.class} equivale a escuchar TODO.
 * La entrega es síncrona; si un suscriptor lanza una excepción, no afecta a los demás.
 */
public class EventBus {

    private final Map<Class<? extends Evento>, List<Suscriptor<? extends Evento>>> suscriptores =
            new ConcurrentHashMap<>();

    public <T extends Evento> void suscribir(Class<T> tipoDeEvento, Suscriptor<T> suscriptor) {
        suscriptores.computeIfAbsent(tipoDeEvento, k -> new CopyOnWriteArrayList<>()).add(suscriptor);
    }

    /** Se debe pasar la misma referencia que se usó al suscribir. */
    public <T extends Evento> void desuscribir(Class<T> tipoDeEvento, Suscriptor<T> suscriptor) {
        List<Suscriptor<? extends Evento>> lista = suscriptores.get(tipoDeEvento);
        if (lista != null) {
            lista.remove(suscriptor);
        }
    }

    public void publicar(Evento evento) {
        for (Map.Entry<Class<? extends Evento>, List<Suscriptor<? extends Evento>>> entrada : suscriptores.entrySet()) {
            if (entrada.getKey().isAssignableFrom(evento.getClass())) {
                for (Suscriptor<? extends Evento> s : entrada.getValue()) {
                    try {
                        ((Suscriptor<Evento>) s).alRecibir(evento);
                    } catch (RuntimeException ex) {
                        System.err.println("[EventBus] Error en suscriptor: " + ex);
                    }
                }
            }
        }
    }
}
