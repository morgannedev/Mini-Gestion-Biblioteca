package dao;

import model.Libro;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    public void insertarLibro(Libro libro) throws SQLException {
        String sql = "INSERT INTO Libro (titulo, isbn, autor, fecha_publicacion, estado) VALUES (?,?,?,?,?)";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getIsbn());
            ps.setString(3, libro.getAutor());
            if (libro.getFechaPublicacion() != null) {
                ps.setDate(4, Date.valueOf(libro.getFechaPublicacion()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, libro.getEstado().toString());
            ps.executeUpdate();
        }
    }
    public List<Libro> listarLibros() throws SQLException {
        String sql = "SELECT * FROM Libro";
        List<Libro> lista = new ArrayList<>();
        try(Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)){
            while(rs.next()){
                Libro libro = new Libro();
                libro.setId(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setAutor(rs.getString("autor"));

                Date fecha = rs.getDate("fecha_publicacion");
                if(fecha != null) {
                    libro.setFechaPublicacion(fecha.toLocalDate());
                }

                String estadoStr = rs.getString("estado");
                if(estadoStr != null) {
                    libro.setEstado(model.enums.Estado.valueOf(estadoStr));
                }

                lista.add(libro);
            }
        }
        return lista;
    }
    public void eliminarLibro(int id) throws SQLException {
        String sql = "DELETE FROM Libro WHERE id_libro = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    public void actualizarEstado(int id, String estado) throws SQLException {
        String sql = "UPDATE Libro SET estado = ? WHERE id_libro = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
    public String obtenerEstado(int id) throws SQLException {
        String sql = "SELECT * FROM Libro WHERE id_libro = ?";
        try (Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("estado");
            }else{
                throw new SQLException("Libro no encontrado.");
            }
        }
    }
}
