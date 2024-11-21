package br.com.fiap.resource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;  // Import correto
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.fiap.beans.Usuario;
import br.com.fiap.bo.UsuarioBO;
import br.com.fiap.conexoes.ConexaoBDD;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.dto.UsuarioCadastroDTO;
import br.com.fiap.dto.UsuarioLoginDTO;

@Path("/usuarios")
public class UsuarioResource {

    private final UsuarioDAO usuarioDAO;
    private final ConexaoBDD conexaoBDD;

    public UsuarioResource() throws ClassNotFoundException, SQLException {
        this.usuarioDAO = new UsuarioDAO();
        this.conexaoBDD = new ConexaoBDD();
    }

    @POST
    @Path("/cadastro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(UsuarioCadastroDTO cadastroDTO) throws ClassNotFoundException {
        try {
            UsuarioBO usuarioBO = new UsuarioBO(usuarioDAO);

            Usuario usuario = new Usuario(
                cadastroDTO.getNomeCompleto(),
                cadastroDTO.getDataNascimento(),
                cadastroDTO.getEmail(),
                cadastroDTO.getNumeroTelefone(),
                cadastroDTO.getSenha(),
                cadastroDTO.getGenero()
            );

            usuarioBO.cadastrarUsuario(usuario, cadastroDTO.getConfirmarSenha());
            return Response.status(Response.Status.CREATED)
                .entity("Usuário cadastrado com sucesso!")
                .build();

        } catch (IllegalArgumentException e) {
            e.printStackTrace(); // Log para debug
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Erro de validação: " + e.getMessage())
                .build();
        } catch (SQLException e) {
            e.printStackTrace(); // Log para debug
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erro interno ao cadastrar usuário: " + e.getMessage())
                .build();
        }
    }

    @GET
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUsuario(UsuarioLoginDTO loginDTO) throws ClassNotFoundException {
        try {
            UsuarioBO usuarioBO = new UsuarioBO(usuarioDAO);

            Usuario usuario = usuarioBO.validarLogin(loginDTO.getEmail(), loginDTO.getSenha());
            if (usuario != null) {
                return Response.ok(usuario).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Email ou senha inválidos")
                .build();

        } catch (SQLException e) {
            e.printStackTrace(); // Log para debug
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erro ao realizar login: " + e.getMessage())
                .build();
        }
    }
    
    @DELETE
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletarUsuario(@PathParam("email") String email) {
        try (Connection connection = conexaoBDD.conexao()) {
            try {
                usuarioDAO.deletarPorEmail(email);
                return Response.ok()
                    .entity("Usuário deletado com sucesso!")
                    .build();
            } catch (SQLException e) {
                if (e.getMessage().contains("Nenhum usuário encontrado")) {
                    return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuário não encontrado")
                        .build();
                }
                throw e; // Re-lança a exceção para ser capturada pelo catch externo
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log para debug
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erro ao deletar usuário: " + e.getMessage())
                .build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Log para debug
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erro de configuração do servidor")
                .build();
        }
    }
    
    @PUT
    @Path("/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarUsuario(@PathParam("email") String email, Usuario usuario) {
        if (usuario == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Dados do usuário não fornecidos")
                .build();
        }
        
        try (Connection connection = conexaoBDD.conexao()) {
            try {
                usuarioDAO.atualizarUsuario(email, usuario);
                return Response.ok()
                    .entity("Usuário atualizado com sucesso!")
                    .build();
            } catch (SQLException e) {
                if (e.getMessage().contains("Nenhum usuário encontrado")) {
                    return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuário não encontrado")
                        .build();
                }
                throw e; // Re-lança a exceção para ser capturada pelo catch externo
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log para debug
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erro ao atualizar usuário: " + e.getMessage())
                .build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Log para debug
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erro de configuração do servidor")
                .build();
        }
    }
}