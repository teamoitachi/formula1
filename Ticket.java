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
