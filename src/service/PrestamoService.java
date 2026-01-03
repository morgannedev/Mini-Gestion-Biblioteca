package service;

import dao.LibroDAO;
import dao.PrestamoDAO;
import model.Prestamo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PrestamoService {
    private PrestamoDAO prestamoDAO = new PrestamoDAO();
    private LibroDAO libroDAO = new LibroDAO();

    public void prestarLibro(int idUsuario, int idLibro) throws SQLException {
        String estado = libroDAO.obtenerEstado(idLibro);

        if(!estado.equals("DISPONIBLE")){
            throw new IllegalStateException("El libro no está disponible.");
        }
        Prestamo prestamo = new Prestamo();
        prestamo.setIdUsuario(idUsuario);
        prestamo.setIdLibro(idLibro);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucion(null);

        prestamoDAO.insertarPrestamo(prestamo);
        libroDAO.actualizarEstado(idLibro, "PRESTADO");
    }
    public void devolverLibro(int idPrestamo, int idLibro) throws SQLException {
        if (prestamoDAO.estaDevuelto(idPrestamo)) {
            throw new IllegalStateException("El préstamo ya está devuelto");
        }

        prestamoDAO.devolverPrestamo(idPrestamo, LocalDate.now());
        libroDAO.actualizarEstado(idLibro, "DISPONIBLE");
    }
    public List<Prestamo> listarPrestamosActivos(int idUsuario) throws SQLException {
        return prestamoDAO.listarPrestamos(idUsuario);
    }
}
