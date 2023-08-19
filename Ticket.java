
import java.util.Random;
import java.util.Scanner;


class Localidad {
    private String nombre;
    private int precio;
    private int espaciosDisponibles;
    private int espaciosIniciales;

    public Localidad(String nombre, int precio, int espaciosIniciales) {
        this.nombre = nombre;
        this.precio = precio;
        this.espaciosIniciales = espaciosIniciales;
        this.espaciosDisponibles = espaciosIniciales;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public int getEspaciosDisponibles() {
        return espaciosDisponibles;
    }

    public int getEspaciosIniciales() {
        return espaciosIniciales;
    }

    public void venderBoletos(int cantidad) {
        espaciosDisponibles -= cantidad;
    }
}

public class Ticket {
    private String nombre;
    private String dpi;
    private int cantidad;
    private double presupuesto;
    private int numero;

    public Ticket(String nombre, String dpi, int cantidad, double presupuesto) {
        this.nombre = nombre;
        this.dpi = dpi;
        this.cantidad = cantidad;
        this.presupuesto = presupuesto;

        Random r = new Random();
        this.numero = r.nextInt(28000) + 1;
    }

    public boolean esApto() {
        Random r = new Random();
        int a = r.nextInt(15000) + 1;
        int b = r.nextInt(15000) + 1;
        int suma = this.numero + a + b;

        return suma % 2 != 0;
    }

    public void mostrar() {
        System.out.println("Nombre: " + this.nombre);
        System.out.println("DPI: " + this.dpi);
        System.out.println("Cantidad: " + this.cantidad);
        System.out.println("Presupuesto: " + this.presupuesto);
        System.out.println("Número: " + this.numero);

        if (this.esApto()) {
            System.out.println("Este ticket es apto para comprar boletos.");
        } else {
            System.out.println("Este ticket no es apto para comprar boletos.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ticket t = null;
        Localidad[] localidades = {
            new Localidad("Localidad 1", 300, 20),
            new Localidad("Localidad 5", 565, 20),
            new Localidad("Localidad 10", 1495, 20)
        };

        while (true) {
            System.out.println("Menú:");
            System.out.println("1. Nuevo comprador");
            System.out.println("2. Nueva solicitud de boletos");
            System.out.println("3. Consultar disponibilidad total");
            System.out.println("4. Consultar disponibilidad individual");
            System.out.println("5. Reporte de caja");
            System.out.println("6. Salir");
            System.out.print("Ingrese la opción deseada: ");
            int opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese su nombre:");
                    String nombre = sc.nextLine();
                    System.out.println("Ingrese su DPI:");
                    String dpi = sc.nextLine();
                    System.out.println("Ingrese la cantidad de boletos a comprar:");
                    int cantidad = sc.nextInt();
                    System.out.println("Ingrese su presupuesto máximo:");
                    double presupuesto = sc.nextDouble();
                    t = new Ticket(nombre, dpi, cantidad, presupuesto);
                    break;

                case 2:
                    if (t != null) {
                        t.mostrar();
                        if (t.esApto()) {
                            Localidad localidad = seleccionarLocalidadAleatoria(localidades);
                            if (localidad != null) {
                                venderBoletos(t, localidad);
                            }
                        }
                    } else {
                        System.out.println("No se ha registrado un nuevo comprador.");
                    }
                    break;

                case 3:
                    consultarDisponibilidadTotal(localidades);
                    break;

                case 4:
                    if (t != null) {
                        System.out.println("Seleccione una localidad:");
                        for (int i = 0; i < localidades.length; i++) {
                            System.out.println((i + 1) + ". " + localidades[i].getNombre());
                        }
                        int seleccion = sc.nextInt();
                        if (seleccion >= 1 && seleccion <= localidades.length) {
                            consultarDisponibilidadIndividual(localidades[seleccion - 1]);
                        } else {
                            System.out.println("Opción inválida.");
                        }
                    } else {
                        System.out.println("No se ha registrado un nuevo comprador.");
                    }
                    break;

                case 5:
                    reporteDeCaja(localidades);
                    break;

                case 6:
                    System.out.println("¡Hasta luego!");
                    return;

                default:
                    System.out.println("Opción inválida. Por favor, elija una opción del menú.");
                    break;
            }
        }
    }

    private static Localidad seleccionarLocalidadAleatoria(Localidad[] localidades) {
        Random r = new Random();
        int index = r.nextInt(localidades.length);
        return localidades[index];
    }

    private static void venderBoletos(Ticket ticket, Localidad localidad) {
        if (localidad.getEspaciosDisponibles() == 0) {
            System.out.println("Lo sentimos, la localidad está llena.");
            return;
        }

        int boletosDisponibles = Math.min(ticket.cantidad, localidad.getEspaciosDisponibles());
        if (boletosDisponibles == 0) {
            System.out.println("No hay boletos disponibles para la cantidad deseada.");
            return;
        }

        double costoTotal = boletosDisponibles * localidad.getPrecio();
        if (costoTotal > ticket.presupuesto) {
            System.out.println("El costo total excede el presupuesto máximo.");
            return;
        }

        localidad.venderBoletos(boletosDisponibles);
        System.out.println("Boletos vendidos: " + boletosDisponibles);
    }

    private static void consultarDisponibilidadTotal(Localidad[] localidades) {
        for (Localidad loc : localidades) {
            System.out.println("Localidad: " + loc.getNombre());
            System.out.println("Boletos vendidos: " + (loc.getEspaciosIniciales() - loc.getEspaciosDisponibles()));
            System.out.println("Boletos disponibles: " + loc.getEspaciosDisponibles());
        }
    }

    private static void consultarDisponibilidadIndividual(Localidad localidad) {
        System.out.println("Localidad: " + localidad.getNombre());
        System.out.println("Boletos disponibles: " + localidad.getEspaciosDisponibles());
    }

    private static void reporteDeCaja(Localidad[] localidades) {
        double totalCaja = 0;
        for (Localidad loc : localidades) {
            int boletosVendidos = loc.getEspaciosIniciales() - loc.getEspaciosDisponibles();
            totalCaja += boletosVendidos * loc.getPrecio();
        }
        System.out.println("Total de dinero generado: " + totalCaja);
    }
}

