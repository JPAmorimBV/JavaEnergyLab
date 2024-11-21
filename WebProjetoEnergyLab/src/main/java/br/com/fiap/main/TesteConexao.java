package br.com.fiap.main;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.fiap.conexoes.ConexaoBDD;

public class TesteConexao {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Connection c = null;

		c = new ConexaoBDD().conexao();
		System.out.println("Conectado com o Banco de Dados");
		c.close();

	}
}
