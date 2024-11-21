package br.com.fiap.bo;

import java.sql.SQLException;

import br.com.fiap.beans.Usuario;
import br.com.fiap.dao.UsuarioDAO;

public class UsuarioBO {
	private UsuarioDAO usuarioDAO;

	public UsuarioBO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public void cadastrarUsuario(Usuario usuario, String confirmarSenha) throws IllegalArgumentException, SQLException {
		if (usuario.getSenha() == null || !usuario.getSenha().equals(confirmarSenha)) {
			throw new IllegalArgumentException("As senhas não coincidem.");
		}
		if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
			throw new IllegalArgumentException("O email é obrigatório.");
		}

		usuarioDAO.inserir(usuario);
	}

	public Usuario validarLogin(String email, String senha) throws SQLException {
		Usuario usuario = usuarioDAO.buscarPorEmailESenha(email, senha);
		if (usuario == null) {
			throw new IllegalArgumentException("Email ou senha inválidos.");
		}
		return usuario;
	}
}
