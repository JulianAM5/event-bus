package uabc.julian.bus;

import uabc.julian.eventos.Evento;

/** Receptor de eventos de tipo T. Al ser interfaz funcional puede ser una lambda o un objeto. */
@FunctionalInterface
public interface Suscriptor<T extends Evento> {
    void alRecibir(T evento);
}
