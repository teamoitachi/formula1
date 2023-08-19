import java.util.Random;
import java.util.Scanner;

class Localidad {
    private String nombre;
    private int precio;
    private int espaciosDisponibles;

    public Localidad(String nombre, int precio, int espaciosDisponibles) {
        this.nombre = nombre;
        this.precio = precio;
        this.espaciosDisponibles = espaciosDisponibles;
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

        // 2. número aleatorio de 1 a 28,000
        Random r = new Random();
        this.numero = r.nextInt(28000) + 1;
    }

    public boolean esApto() {
        Random r = new Random();
        int a = r.nextInt(15000) + 1;
        int b = r.nextInt(15000) + 1;
        int suma = this.numero + a + b;

        // 3. Validar que la suma sea impar
        return suma % 2 != 0;
    }

    public void mostrar() {
        System.out.println("Nombre: " + this.nombre);
        System.out.println("DPI: " + this.dpi);
        System.out.println("Cantidad: " + this.cantidad);
        System.out.println("Presupuesto: " + this.presupuesto);
        System.out.println("Número: " + this.numero);

        // 1. Solicitar compra de boleto / mostrar si es apto
        if (this.esApto()) {
            System.out.println("Este ticket es apto para comprar boletos. ");
        } else {
            System.out.println("Este ticket no es apto para comprar boletos.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese su nombre:");
        String nombre = sc.nextLine();
        System.out.println("Ingrese su DPI:");
        String dpi = sc.nextLine();
        System.out.println("Ingrese la cantidad de boletos a comprar:");
        int cantidad = sc.nextInt();
        System.out.println("Ingrese su presupuesto máximo:");
        double presupuesto = sc.nextDouble();

        Ticket t = new Ticket(nombre, dpi, cantidad, presupuesto);
        t.mostrar();

        // 5. Vender boletos y seleccionar localidad aleatoria
        if (t.esApto()) {
            Localidad localidad = seleccionarLocalidadAleatoria();
            if (localidad != null) {
                venderBoletos(t, localidad);
            }
        }
    }

    // 4. seleccionar una localidad aleatoria
    private static Localidad seleccionarLocalidadAleatoria() {
        Localidad[] localidades = {
                new Localidad("Localidad 1", 300, 20),
                new Localidad("Localidad 5", 565, 20),
                new Localidad("Localidad 10", 1495, 20)
        };
        Random r = new Random();
        int index = r.nextInt(localidades.length);
        return localidades[index];
    }

    // 6. vender boletos y realizar validaciones
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

        // 7. si pasa validación
        localidad.venderBoletos(boletosDisponibles);
        System.out.println("Boletos vendidos: " + boletosDisponibles);
    }
}
