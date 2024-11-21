package br.com.fiap.conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBDD {
	public Connection conexao() throws ClassNotFoundException, SQLException {

		// conecta com o sql
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//rm558191   151105
		return DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl", "rm558191", "151105");

	}
}
