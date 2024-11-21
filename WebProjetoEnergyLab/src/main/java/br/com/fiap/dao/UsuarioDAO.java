package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import br.com.fiap.beans.Usuario;
import br.com.fiap.conexoes.ConexaoBDD;

public class UsuarioDAO {

	private Connection minhaConexao;

	public UsuarioDAO() throws ClassNotFoundException, SQLException {
		super();
		this.minhaConexao = new ConexaoBDD().conexao();
	}

	public void inserir(Usuario usuario) throws SQLException {
	    String sql = "INSERT INTO USUARIO (nome_completo, data_nascimento, email, numero_telefone, senha, genero) VALUES (?, ?, ?, ?, ?, ?)";
	    System.out.println("Iniciando inserção do usuário...");

	    try (PreparedStatement stmt = minhaConexao.prepareStatement(sql)) {
	        // Formatar e converter a data
	        DateTimeFormatter formatoOriginal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        LocalDate dataFormatada = LocalDate.parse(usuario.getDataNascimento(), formatoOriginal);
	        Date dataSQL = Date.valueOf(dataFormatada);

	        // Configurar os parâmetros do PreparedStatement
	        stmt.setString(1, usuario.getNomeCompleto());
	        stmt.setDate(2, dataSQL);
	        stmt.setString(3, usuario.getEmail());
	        stmt.setString(4, usuario.getNumeroTelefone());
	        stmt.setString(5, usuario.getSenha());
	        stmt.setString(6, usuario.getGenero());

	        // Executar a inserção
	        int linhasAfetadas = stmt.executeUpdate();
	        if (linhasAfetadas > 0) {
	            System.out.println("Usuário inserido com sucesso!");
	        } else {
	            System.err.println("Falha ao inserir o usuário.");
	        }
	    } catch (DateTimeParseException e) {
	        System.err.println("Erro ao formatar a data de nascimento: " + e.getMessage());
	        throw new SQLException("Formato inválido para a data de nascimento.", e);
	    }
	}


	
	
	public Usuario buscarPorEmailESenha(String email, String senha) throws SQLException {
		String sql = "SELECT * FROM USUARIO WHERE email = ? AND senha = ?";
		try (PreparedStatement stmt = minhaConexao.prepareStatement(sql)) {
			stmt.setString(1, email);
			stmt.setString(2, senha);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Usuario(rs.getString("nome_completo"), rs.getString("data_nascimento"),
							rs.getString("email"), rs.getString("numero_telefone"), rs.getString("senha"),
							rs.getString("genero"));
				}
			}
		}
		return null;
	}
	
	
	
	
	public void deletarPorEmail(String email) throws SQLException {
	    String sql = "DELETE FROM USUARIO WHERE email = ?";
	    try (PreparedStatement stmt = minhaConexao.prepareStatement(sql)) {
	        stmt.setString(1, email);
	        int linhasAfetadas = stmt.executeUpdate();
	        if (linhasAfetadas == 0) {
	            throw new SQLException("Nenhum usuário encontrado com o email fornecido.");
	        }
	    }
	}
	
	
	
	
	
	public void atualizarUsuario(String email, Usuario usuario) throws SQLException {
	    String sql = "UPDATE USUARIO SET nome_completo = ?, data_nascimento = ?, numero_telefone = ?, senha = ?, genero = ? WHERE email = ?";
	    try (PreparedStatement stmt = minhaConexao.prepareStatement(sql)) {
	        DateTimeFormatter formatoOriginal = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        LocalDate dataFormatada = LocalDate.parse(usuario.getDataNascimento(), formatoOriginal);
	        Date dataSQL = Date.valueOf(dataFormatada);

	        stmt.setString(1, usuario.getNomeCompleto());
	        stmt.setDate(2, dataSQL);
	        stmt.setString(3, usuario.getNumeroTelefone());
	        stmt.setString(4, usuario.getSenha());
	        stmt.setString(5, usuario.getGenero());
	        stmt.setString(6, email);

	        int linhasAfetadas = stmt.executeUpdate();
	        if (linhasAfetadas == 0) {
	            throw new SQLException("Nenhum usuário encontrado com o email fornecido.");
	        }
	    }
	}

	
	
}
