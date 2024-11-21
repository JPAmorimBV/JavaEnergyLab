package br.com.fiap.main;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import br.com.fiap.dao.UsuarioDAO;

public class TesteDeletar {

	static String texto(String mensagem) {
		return JOptionPane.showInputDialog(mensagem);
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		UsuarioDAO userDAO = new UsuarioDAO();

		String email = texto("Digite o email do usuário que deseja deletar:");

		try {
			userDAO.deletarPorEmail(email);
			System.out.println("Usuário deletado com sucesso!");
		} catch (SQLException e) {
			System.out.println("Erro ao deletar usuário: " + e.getMessage());
		}
	}
}
