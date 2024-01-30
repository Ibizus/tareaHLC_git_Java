// CajeroV3.java
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CajeroV3 {
    private static final String MOVIMIENTOS_FILE = "movimientos.txt";
    private static final String CUENTAS_FILE = "cuentas.txt";
    private static final String CLIENTES_FILE = "clientes.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Double> saldos = cargarSaldos();
        Map<String, String> clientes = cargarClientes();

        while (true) {
            System.out.println("1- Retirar fondos");
            System.out.println("2- Ingresar fondos");
            System.out.println("3- Consulta de movimientos");
            System.out.println("4- Listar todas las cuentas de un cliente (por DNI)");
            System.out.println("5- Consulta de cuentas con saldo menor a una cantidad");
            System.out.println("0- Salir");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese su DNI: ");
                    String dniRetiro = scanner.next();
                    System.out.print("Ingrese la cantidad a retirar: ");
                    double cantidadRetiro = scanner.nextDouble();
                    if (validarRetiro(dniRetiro, cantidadRetiro, saldos)) {
                        realizarRetiro(dniRetiro, cantidadRetiro, saldos);
                        registrarMovimiento(dniRetiro, "Retiro", cantidadRetiro);
                        System.out.println("Retiro exitoso. Saldo actual: " + saldos.get(dniRetiro));
                    } else {
                        System.out.println("Operación no permitida. Saldo insuficiente o cantidad inválida.");
                    }
                    break;

                case 2:
                    System.out.print("Ingrese su DNI: ");
                    String dniIngreso = scanner.next();
                    System.out.print("Ingrese la cantidad a ingresar: ");
                    double cantidadIngreso = scanner.nextDouble();
                    realizarIngreso(dniIngreso, cantidadIngreso, saldos);
                    registrarMovimiento(dniIngreso, "Ingreso", cantidadIngreso);
                    System.out.println("Ingreso exitoso. Saldo actual: " + saldos.get(dniIngreso));
                    break;

                case 3:
                    // Implementar la lógica de consulta de movimientos
                    break;

                case 4:
                    System.out.print("Ingrese el DNI del cliente: ");
                    String dniConsulta = scanner.next();
                    listarCuentasCliente(dniConsulta, saldos, clientes);
                    break;

                case 5:
                    System.out.print("Ingrese la cantidad límite: ");
                    double limiteConsulta = scanner.nextDouble();
                    consultarCuentasSaldoMenor(limiteConsulta, saldos, clientes);
                    break;

                case 0:
                    System.out.println("¡Hasta luego!");
                    guardarSaldos(saldos);
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static Map<String, Double> cargarSaldos() {
        Map<String, Double> saldos = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CUENTAS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                saldos.put(parts[0], Double.parseDouble(parts[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saldos;
    }

    private static Map<String, String> cargarClientes() {
        Map<String, String> clientes = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CLIENTES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                clientes.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    private static void guardarSaldos(Map<String, Double> saldos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUENTAS_FILE))) {
            for (Map.Entry<String, Double> entry : saldos.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void registrarMovimiento(String dni, String tipo, double cantidad) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MOVIMIENTOS_FILE, true))) {
            writer.println(dni + "," + tipo + "," + cantidad);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean validarRetiro(String dni, double cantidad, Map<String, Double> saldos) {
        return saldos.containsKey(dni) && saldos.get(dni) >= cantidad && cantidad > 0;
    }

    private static void realizarRetiro(String dni, double cantidad, Map<String, Double> saldos) {
        double saldoActual = saldos.get(dni);
        saldos.put(dni, saldoActual - cantidad);
    }

    private static void realizarIngreso(String dni, double cantidad, Map<String, Double> saldos) {
        double saldoActual = saldos.getOrDefault(dni, 0.0);
        saldos.put(dni, saldoActual + cantidad);
    }

    private static void listarCuentasCliente(String dniConsulta, Map<String, Double> saldos, Map<String, String> clientes) {
        if (clientes.containsKey(dniConsulta)) {
            System.out.println("Cuentas de " + clientes.get(dniConsulta) + ":");
            saldos.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith(dniConsulta))
                    .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private static void consultarCuentasSaldoMenor(double limite, Map<String, Double> saldos, Map<String, String> clientes) {
        System.out.println("Cuentas con saldo menor a " + limite + ":");
        saldos.entrySet().stream()
                .filter(entry -> entry.getValue() < limite)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}
