package br.com.fiap.main;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import br.com.fiap.beans.Usuario;
import br.com.fiap.dao.UsuarioDAO;

public class TesteAtualizar {

    static String texto(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    static String solicitarDataValida() {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog("Nova data de nascimento (dd-MM-yyyy):");
                DateTimeFormatter formatoOriginal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate.parse(input, formatoOriginal);
                return input;
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Formato de data inválido! Tente novamente.");
            }
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UsuarioDAO userDAO = new UsuarioDAO();

        String email = texto("Digite o email do usuário que deseja atualizar:");
        Usuario usuario = new Usuario();

        usuario.setNomeCompleto(texto("Novo nome completo:"));
        usuario.setDataNascimento(solicitarDataValida());
        usuario.setNumeroTelefone(texto("Novo número de telefone:"));
        usuario.setSenha(texto("Nova senha:"));
        usuario.setGenero(texto("Novo gênero:"));

        try {
            userDAO.atualizarUsuario(email, usuario);
            System.out.println("Usuário atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }
}
