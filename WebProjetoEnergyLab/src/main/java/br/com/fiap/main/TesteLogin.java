package br.com.fiap.main;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.beans.Usuario;

public class TesteLogin {

    // Método para obter texto do JOptionPane
    static String texto(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Instanciar DAO
        UsuarioDAO userDAO = new UsuarioDAO();
        
        // Coletar email e senha do usuário
        String email = texto("E-mail");
        String senha = texto("Senha");

        // Buscar usuário no banco
        Usuario usuario = userDAO.buscarPorEmailESenha(email, senha);

        // Verificar o resultado da busca
        if (usuario != null) {
            System.out.println("Login realizado com sucesso!");
            System.out.println("Bem-vindo(a), " + usuario.getNomeCompleto());
        } else {
            System.out.println("Login falhou! Usuário ou senha incorretos.");
        }
    }
}
