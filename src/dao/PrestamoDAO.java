package dao;

import model.Prestamo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    public void insertarPrestamo(Prestamo prestamo) throws SQLException {
        String sql = "INSERT INTO Prestamo (id_usuario, id_libro, fecha_prestamo, fecha_devolucion) VALUES (?,?,?,?)";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, prestamo.getIdUsuario());
            ps.setInt(2, prestamo.getIdLibro());
            ps.setDate(3, Date.valueOf(prestamo.getFechaPrestamo()));

            if(prestamo.getFechaDevolucion() != null){
                ps.setDate(4, Date.valueOf(prestamo.getFechaDevolucion()));
            }else{
                ps.setNull(4, Types.DATE);
            }
            ps.executeUpdate();
        }
    }
    public List<Prestamo> listarPrestamos(int idUsuario) throws SQLException {
        String sql = "SELECT * FROM Prestamo WHERE id_usuario = ? AND fecha_devolucion IS NULL";
        List<Prestamo> lista = new ArrayList<>();
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Prestamo prestamo = new Prestamo();
                prestamo.setId(rs.getInt("id_prestamo"));
                prestamo.setIdUsuario(rs.getInt("id_Usuario"));
                prestamo.setIdLibro(rs.getInt("id_Libro"));
                Date fechaPrestamo = rs.getDate("fecha_prestamo");
                if(fechaPrestamo != null) {
                    prestamo.setFechaPrestamo(fechaPrestamo.toLocalDate());
                }
                Date fechaDevolucion = rs.getDate("fecha_devolucion");
                if(fechaDevolucion != null) {
                    prestamo.setFechaDevolucion(fechaDevolucion.toLocalDate());
                } else {
                    prestamo.setFechaDevolucion(null);
                }
                lista.add(prestamo);
            }
        }
        return lista;
    }
    public void devolverPrestamo(int id, LocalDate fechaDevolucion) throws SQLException {
        String sql = "UPDATE Prestamo SET fecha_devolucion = ? WHERE id_Prestamo = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(fechaDevolucion));
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
    public void actualizarEstado(int id, String estado) throws SQLException {
        String sql = "UPDATE Prestamo SET estado = ? WHERE id_Prestamo = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public boolean estaDevuelto(int idPrestamo) throws SQLException {
        String sql = "SELECT fecha_devolucion FROM Prestamo WHERE id_Prestamo = ?";
        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, idPrestamo);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getDate("fecha_devolucion") != null;
            }else{
                throw new SQLException("Préstamo no encontrado.");
            }
        }
    }
}
