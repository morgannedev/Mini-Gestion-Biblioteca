package service;

import dao.LibroDAO;
import dao.PrestamoDAO;
import model.Libro;

import java.sql.SQLException;
import java.util.List;

public class LibroService {
    private LibroDAO libroDAO = new LibroDAO();

    public List<Libro> listarLibros() throws SQLException{
        return libroDAO.listarLibros();
    }
    public boolean estaDisponible(int idLibro) throws SQLException{
        return libroDAO.obtenerEstado(idLibro).equals("DISPONIBLE");
    }
}
