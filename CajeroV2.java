// CajeroV2.java
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CajeroV2 {
    private static final String MOVIMIENTOS_FILE = "movimientos.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double saldo = 1000.0;

        while (true) {
            System.out.println("1- Retirar fondos");
            System.out.println("2- Ingresar fondos");
            System.out.println("3- Consulta de movimientos");
            System.out.println("0- Salir");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la cantidad a retirar: ");
                    double cantidadRetiro = scanner.nextDouble();
                    if (saldo >= cantidadRetiro) {
                        saldo -= cantidadRetiro;
                        registrarMovimiento("Retiro", cantidadRetiro);
                        System.out.println("Retiro exitoso. Saldo actual: " + saldo);
                    } else {
                        System.out.println("Fondos insuficientes. Saldo actual: " + saldo);
                    }
                    break;

                case 2:
                    System.out.print("Ingrese la cantidad a ingresar: ");
                    double cantidadIngreso = scanner.nextDouble();
                    saldo += cantidadIngreso;
                    registrarMovimiento("Ingreso", cantidadIngreso);
                    System.out.println("Ingreso exitoso. Saldo actual: " + saldo);
                    break;

                case 3:
                    // Implementar la lógica de consulta de movimientos
                    break;

                case 0:
                    System.out.println("¡Hasta luego!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void registrarMovimiento(String tipo, double cantidad) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MOVIMIENTOS_FILE, true))) {
            writer.println(tipo + ": " + cantidad);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}