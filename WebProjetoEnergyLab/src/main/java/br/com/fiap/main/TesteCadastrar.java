package br.com.fiap.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import br.com.fiap.beans.Usuario;
import br.com.fiap.dao.UsuarioDAO;

public class TesteCadastrar {

    // Método para obter texto do JOptionPane
    static String texto(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    // Método para validar a data
    static String solicitarDataValida() {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog("Data de nascimento (dd-MM-yyyy):");
                DateTimeFormatter formatoOriginal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate.parse(input, formatoOriginal); // Testa o formato
                return input; // Retorna a data válida
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Formato de data inválido! Tente novamente no formato dd-MM-yyyy.");
            }
        }
    }

    public static void main(String[] args) {
        try {
            Usuario user = new Usuario();
            UsuarioDAO userDAO = new UsuarioDAO();

            user.setNomeCompleto(texto("Nome completo"));
            user.setDataNascimento(solicitarDataValida());
            user.setEmail(texto("E-mail"));
            user.setNumeroTelefone(texto("Número de telefone"));
            user.setSenha(texto("Digite sua senha:"));
            user.setGenero(texto("Gênero"));

            userDAO.inserir(user);
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar o usuário: " + e.getMessage());
        }
    }
}

