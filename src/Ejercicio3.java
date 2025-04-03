import java.io.File;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Scanner;

public class Ejercicio3 {
    public static void main(String[] args) {
        File directorioActual = File.listRoots()[0];
        int opcionSeleccionada = 0;
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

            DateFormat formatter;
            formatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.getDefault());

            //Listar contenido del directorio
            File[] contenido = directorioActual.listFiles();
            if (contenido == null) {
                System.out.println("No se puede leer el contenido de este directorio.");
            } else {
                for (int i = 0; i < contenido.length; i++) {
                    System.out.print((i + 1) + ". ");
                    //Comprobamos si es un directorio
                    if (contenido[i].isDirectory()) System.out.print(" \td");
                    else System.out.print(" \t-");

                    //Comprobamos si tiene permisos de lectura
                    if (contenido[i].canRead()) System.out.print("r");
                    else System.out.print("-");

                    //Comprobamos si tiene permisos de escritura
                    if (contenido[i].canWrite()) System.out.print("w");
                    else System.out.print("-");

                    //Comprobamos si tiene permisos de ejecución
                    if (contenido[i].canExecute()) System.out.print("x");
                    else System.out.print("-");

                    //Mostramos la longitud, la fecha de la última modificación y el nombre
                    System.out.println("\t" + contenido[i].length() + "\t\t" + formatter.format(contenido[i].lastModified()) + "\t" + contenido[i].getName());
                }
            }

            //Manejamos las ociones seleccionadas
            if (opcionSeleccionada != -2) {
                System.out.print("Opciones:\n-1 para salir\n-2 para cambiar permisos\n-3 para contar archivos por extensión\n" +
                        "-4 Buscar archivo por nombre\n-5 Crear un nuevo directorio\n-6 Eliminar un directorio\nIntroduce una opción: ");
                opcionSeleccionada = s.nextInt();
                s.nextLine();

                if (opcionSeleccionada == 0) {
                    if (padre != null && padre.canRead()) directorioActual = padre;
                } else if (opcionSeleccionada > 0 && contenido != null && opcionSeleccionada <= contenido.length) {
                    File seleccionado = contenido[opcionSeleccionada - 1];
                    if (seleccionado.isDirectory() && seleccionado.canRead()) directorioActual = seleccionado;
                } else if (opcionSeleccionada == -1) System.out.println("Saliendo...");
                else if (opcionSeleccionada == -3) {
                    int contadorExtension = 0;

                    System.out.print("Ingrese la extensión a buscar (ej: txt, java): ");
                    String extension = s.nextLine();

                    for (int i = 0; i < contenido.length; i++) {
                        if (contenido[i].getName().endsWith(extension)) contadorExtension++;
                    }

                    System.out.printf("Se encontraron %d archivos con la extension .%s\n", contadorExtension, extension);
                    opcionSeleccionada = 0;
                } else if (opcionSeleccionada == -4) {
                    System.out.print("Ingrese el nombre del archivo a buscar: ");
                    String nombreArchivo = s.nextLine();

                    for (int i = 0; i < contenido.length; i++) {
                        if (contenido[i].getName().equals(nombreArchivo) && contenido[i].exists()) System.out.println("Archivo encontrado: " + contenido[i].getAbsolutePath());
                    }

                    opcionSeleccionada = 0;
                } else if (opcionSeleccionada == -5) {
                    System.out.print("Introduce el nombre del nuevo directorio: ");
                    String nuevoDirectorio = s.nextLine();

                    File fileNuevoDirectorio = new File(directorioActual, nuevoDirectorio);

                    fileNuevoDirectorio.mkdir();
                    opcionSeleccionada = 0;
                } else if (opcionSeleccionada == -6) {
                    System.out.print("Qué directorio deseas eliminar?: ");
                    String directorioElimiado = s.nextLine();

                    File fileDirectorioEliminado = new File(directorioActual, directorioElimiado);

                    if (fileDirectorioEliminado.exists() && fileDirectorioEliminado.isDirectory()) fileDirectorioEliminado.delete();
                    else System.out.println("El directorio no existe.");

                    opcionSeleccionada = 0;
                }
            } else {
                System.out.print("Introduce el número del archivo que deseas cambiar los permisos: ");
                int permisos = s.nextInt();
                s.nextLine();

                //Modificamos los archivos de lectura
                System.out.print("Deseas darle permisos de lectura (D) o quitarselos (Q)?: ");
                String darOQuitarLectura = s.nextLine();

                if (darOQuitarLectura.equals("D")) contenido[permisos - 1].setReadable(true, false);
                else contenido[permisos - 1].setReadable(false, false);

                //Modificamos los archivos de lectura
                System.out.print("Deseas darle permisos de escritura (D) o quitarselos (Q)?: ");
                String darOQuitarEscritura = s.nextLine();

                if (darOQuitarEscritura.equals("D")) contenido[permisos - 1].setWritable(true, false);
                else contenido[permisos - 1].setWritable(false, false);

                //Modificamos los archivos de lectura
                System.out.print("Deseas darle permisos de ejecución (D) o quitarselos (Q)?: ");
                String darOQuitarEjecucion = s.nextLine();

                if (darOQuitarEjecucion.equals("D")) contenido[permisos - 1].setExecutable(true, false);
                else contenido[permisos - 1].setExecutable(false, false);

                opcionSeleccionada = 0;
            }

        } while (opcionSeleccionada != -1);

        s.close();
        System.out.println("Programa finalizado.");
    }
}