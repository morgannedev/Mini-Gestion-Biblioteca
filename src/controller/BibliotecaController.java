package controller;

import model.Libro;
import model.Prestamo;
import model.Usuario;
import service.LibroService;
import service.PrestamoService;
import service.UsuarioService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BibliotecaController {
    private static final Scanner sc = new Scanner(System.in);
    private final LibroService libroService = new LibroService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final PrestamoService prestamoService = new PrestamoService();

    public void init(){
        System.out.println("--- ¡Hola de nuevo! ---");
        System.out.println("1) Login");
        System.out.println("2) Registrarse");
        System.out.println("Introduce una opción");
        int input =  sc.nextInt();
        sc.nextLine();
        switch(input){
            case 1:
                login();
                break;
            case 2:
                register();
                break;
        }
    }
    public void login(){
        System.out.println("--- LOGIN ---");
        System.out.println("Introduce tu ID:");
        int id = sc.nextInt();
        sc.nextLine();
        try{
            Usuario usuario = usuarioService.login(id);

            if(usuario != null){
                System.out.println("Bienvenido, " + usuario.getNombre() + "!");
                menuUsuario();
            }else{
                System.out.println("ID no válido.");
            }
        }catch(Exception e){
            System.out.println("Error:" + e.getMessage());
        }
    }
    public void register(){
        try{
            System.out.println("--- REGISTRO ---");
            System.out.println("Introduce tu nombre: ");
            String nombre = sc.nextLine();
            System.out.println("Introduce tu primer apellido:");
            String apellido1 = sc.nextLine();
            System.out.println("Introduce tu segundo apellido:");
            String apellido2 = sc.nextLine();
            System.out.println("Introduce tu día de nacimiento: ");
            int dia =  sc.nextInt();
            sc.nextLine();
            System.out.println("Introduce tu mes de nacimiento: ");
            int mes = sc.nextInt();
            sc.nextLine();
            System.out.println("Introduce tu año de nacimiento: ");
            int ano = sc.nextInt();
            sc.nextLine();

            LocalDate fecha = LocalDate.of(ano,mes,dia);
            Usuario usuario = new Usuario(nombre, apellido1, apellido2, fecha);

            usuarioService.crearUsuario(usuario);

            System.out.println("Usuario registrado correctamente");
            System.out.println("Tu ID de usuario es: " + usuario.getId());

            login();
        }catch(SQLException e){
            System.out.println("Error al intentar registrar el usuario: "+ e.getMessage());
        }
    }
    public void menuUsuario(){
        System.out.println("--- MENÚ BIBLIOTECA ---");
        System.out.println("1) Ver libros");
        System.out.println("2) Tomar prestado un libro");
        System.out.println("3) Devolver un libro");
        System.out.println("4) Salir");
        System.out.println("Selecciona una opción:");
        int input = sc.nextInt();
        try{
            switch(input){
                case 1:
                    listarLibros();
                    break;
                case 2:
                    prestarLibro();
                    break;
                case 3:
                    devolverLibro();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Input incorrecto.");
            }
        }catch(SQLException e){
            System.out.println("Error de la base de datos: " + e.getMessage());
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void listarLibros() throws SQLException {
        List<Libro> libros = libroService.listarLibros();
        System.out.println("--- LIBROS ---");
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        for (Libro libro : libros) {
            System.out.println(libro);
        }
        System.out.println();
        menuUsuario();
    }
    public void prestarLibro() throws SQLException {
        System.out.println("--- PRÉSTAMOS ---");
        System.out.println("Introduce tu ID:");
        int idUsuario = sc.nextInt();
        sc.nextLine();
        System.out.println("Introduce el código / ID del libro:");
        int idLibro = sc.nextInt();
        sc.nextLine();
        prestamoService.prestarLibro(idUsuario, idLibro);
        System.out.println("Libro prestado con éxito.");
        menuUsuario();
    }
    public void devolverLibro() throws SQLException {
        System.out.println("--- DEVOLUCIONES ---");
        System.out.println("Introduce tu ID:");
        int idUsuario = sc.nextInt();
        List<Prestamo> activos = prestamoService.listarPrestamosActivos(idUsuario);
        if(activos.isEmpty()){
            System.out.println("No tienes préstamos activos.");
            return;
        }
        System.out.println("Tus préstamos activos:");
        for (Prestamo prestamo : activos) {
            System.out.println("ID Préstamo: " + prestamo.getId() + " | Libro ID: " + prestamo.getIdLibro());
        }

        System.out.println("Introduce el código / ID del préstamo:");
        int idPrestamo =  sc.nextInt();
        sc.nextLine();

        prestamoService.devolverLibro(idPrestamo, activos.stream()
                .filter(p -> p.getId() == idPrestamo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"))
                .getIdLibro());

        System.out.println("Libro devuelto con éxito.");
        menuUsuario();
    }
}
