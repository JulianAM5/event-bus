package uabc.julian;

import uabc.julian.bus.EventBus;
import uabc.julian.componentes.*;
import uabc.julian.extensiones.SistemaDeHumo;

import java.util.List;

/** "El Gólem Alquímico": demostración del flujo completo sobre un Event Bus. */
public class App {
    public static void main(String[] args) throws InterruptedException {
        EventBus bus = new EventBus();

        PanelLED panel = new PanelLED(bus);
        SistemaDeSonido sonido = new SistemaDeSonido(bus);
        Barra barra = new Barra(bus);
        Cocina cocina = new Cocina(bus);
        SistemaDePedidos pedidos = new SistemaDePedidos(bus);
        Banda banda = new Banda(bus, "Los Seguidores de Dijkstra",
                List.of("El Algoritmo del Amor", "Through the Fire and Flames", "Bubble Sort Blues"),
                4, 180);

        panel.iniciar();
        sonido.iniciar();
        barra.iniciar();
        cocina.iniciar();

        System.out.println("=== La taberna abre sus puertas ===");
        pedidos.realizarPedido("mesa_5", List.of("cerveza", "hamburguesa"));
        banda.iniciar();

        Thread.sleep(3000);
        // Extensibilidad: se agrega un suscriptor en caliente, sin tocar Banda ni EventBus.
        System.out.println("=== Se instala el SistemaDeHumo (sin modificar Banda/EventBus) ===");
        new SistemaDeHumo(bus).iniciar();
        pedidos.realizarPedido("mesa_2", List.of("vino", "pizza", "nachos"));

        Thread.sleep(10000);
        System.out.println("=== La taberna cierra ===");
        banda.detener();
        barra.detener();
        cocina.detener();
    }
}
