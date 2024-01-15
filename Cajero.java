// CajeroV1.java
import java.util.Scanner;

public class Cajero {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double saldo = 1000.0;

        while (true) {
            System.out.println("1- Retirar");
            System.out.println("2- Ingresar");
            System.out.println("0- Salir");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la cantidad a retirar: ");
                    double cantidadRetiro = scanner.nextDouble();
                    if (saldo >= cantidadRetiro) {
                        saldo -= cantidadRetiro;
                        System.out.println("Retiro exitoso. Saldo actual: " + saldo);
                    } else {
                        System.out.println("Fondos insuficientes. Saldo actual: " + saldo);
                    }
                    break;

                case 2:
                    System.out.print("Ingrese la cantidad a ingresar: ");
                    double cantidadIngreso = scanner.nextDouble();
                    saldo += cantidadIngreso;
                    System.out.println("Ingreso exitoso. Saldo actual: " + saldo);
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
}