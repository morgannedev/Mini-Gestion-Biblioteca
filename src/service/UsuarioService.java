package service;

import dao.UsuarioDAO;
import model.Usuario;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void crearUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.insertarUsuario(usuario);
    }
    public List<Usuario> listarUsuarios() throws SQLException {
        return usuarioDAO.listarUsuarios();
    }
    public void eliminarUsuario(int id) throws SQLException {
        usuarioDAO.eliminarUsuario(id);
    }
    public Usuario login(int idUsuario) throws SQLException {
        return usuarioDAO.obtenerUsuarioPorId(idUsuario);
    }
}
