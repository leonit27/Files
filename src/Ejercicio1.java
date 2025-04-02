import java.io.File;
import java.util.Scanner;

public class Ejercicio1 {
    public static void main(String[] args) {
        File directorioActual = File.listRoots()[0];
        int opcionSeleccionada;
        Scanner s = new Scanner(System.in);
        
        do {
            //Indicamos el directorio que está mostrando
            System.out.println("Lista de ficheros y direcotrios del directorio: " + directorioActual.getAbsolutePath());

            //Opción 0 para mostrar el directorio padre
            File padre = directorioActual.getParentFile();

            if (padre != null) {
                System.out.println("0. Directorio padre");
            } else {
                System.out.println("0. Directorio padre (no existente)");
            }

            //Listar contenido del directorio
            File[] contenido = directorioActual.listFiles();
            if (contenido == null) {
                System.out.println("No se puede leer el contenido de este directorio.");
            } else {
                for (int i = 0; i < contenido.length; i++) {
                    System.out.print((i + 1) + ". " + contenido[i].getName());
                    if (contenido[i].isDirectory()) {
                        System.out.println(" <Directorio>");
                    } else {
                        System.out.println(" " + contenido[i].length() + " bytes");
                    }
                }
            }

            //Manejamos las ociones seleccionadas
            System.out.print("Introduce una opción (-1 para salir): ");
            opcionSeleccionada = s.nextInt();
            s.nextLine();

            if (opcionSeleccionada == 0) {
                if (padre != null && padre.canRead()) directorioActual = padre;
            } else if (opcionSeleccionada > 0 && contenido != null && opcionSeleccionada <= contenido.length) {
                File seleccionado = contenido[opcionSeleccionada - 1];
                if (seleccionado.isDirectory() && seleccionado.canRead()) directorioActual = seleccionado;
            } else if (opcionSeleccionada == -1) System.out.println("Saliendo...");
            else System.out.println("Opción no válida");

        } while (opcionSeleccionada != -1);

        s.close();
        System.out.println("Programa finalizado.");
    }
}