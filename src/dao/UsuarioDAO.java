package dao;

import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public void insertarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuario (nombre, apellido1, apellido2, fecha_nacimiento) VALUES (?,?,?,?)";
        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido1());
            ps.setString(3, usuario.getApellido2());
            ps.setDate(4, Date.valueOf(usuario.getFechaNacimiento()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                usuario.setId(rs.getInt(1));
            }
        }
    }
    public List<Usuario> listarUsuarios() throws SQLException {
        String sql = "SELECT * FROM Usuario";
        List<Usuario> lista = new ArrayList<>();
        try(Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)){
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido1(rs.getString("apellido1"));
                usuario.setApellido2(rs.getString("apellido2"));
                usuario.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
            }
        }
        return lista;
    }
    public void eliminarUsuario(int id) throws SQLException {
        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    public Usuario obtenerUsuarioPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE id_usuario = ?";
        try (Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido1(rs.getString("apellido1"));
                usuario.setApellido2(rs.getString("apellido2"));
                Date fecha = rs.getDate("fecha_nacimiento");
                if(fecha != null){
                    usuario.setFechaNacimiento(fecha.toLocalDate());
                }
                return usuario;
            }else{
                return null;
            }
        }
    }
}
