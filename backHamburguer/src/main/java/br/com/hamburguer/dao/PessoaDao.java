package br.com.hamburguer.dao;

import br.com.hamburguer.entidades.Pessoa;
import br.com.hamburguer.interfaces.ChecarDuplicidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class PessoaDao {
    String NomeEntidade = "Pessoa";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PessoaDao(){}

    public Object conInserir(Pessoa pessoa) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            Object obj = inserir(con, pessoa);
            if(obj.getClass().getSimpleName().equals("Retorno")){
                return obj;
            }

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao inserir" + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID " + pessoa.getId() + " inserido com sucesso");
        return pessoa;
    }

    public Object inserir(Connection con, Pessoa pessoa) throws Exception {

        Object duplicidade = ChecarDuplicidade.pessoa(con, pessoa);
        if(!duplicidade.equals("cadastrar")){
            return duplicidade;
        }

        String sql = "INSERT INTO PESSOA (NOME) VALUES (?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);

            ps.setString(1, pessoa.getNome());
            ps.execute();

            try(ResultSet tableKeys = ps.getGeneratedKeys()){
                tableKeys.next();
                pessoa.setId(tableKeys.getInt(1));
            } catch (Exception ex) {
                String erro = "ResultSet - Erro ao inserir " + NomeEntidade + " no banco: " + ex;
                System.err.println(erro);
                throw new Exception(erro);
            }

        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao inserir " + NomeEntidade + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return pessoa;
    }

    public List<Pessoa> conListar() throws Exception {

        List<Pessoa> lista = new ArrayList<>();

        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {

            lista = listar(con);

        } catch (Exception ex) {
            String erro = "Connection - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return lista;
    }

    public List<Pessoa> listar(Connection con) throws Exception {

        String sql = "SELECT * FROM PESSOA\n";

        Map<Integer, Pessoa> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    Pessoa pessoa = map.get(id);
                    if(pessoa == null){
                        pessoa = new Pessoa();

                        pessoa.setId(rs.getInt("ID"));
                        pessoa.setNome(rs.getString("NOME"));

                        map.put(pessoa.getId(), pessoa);
                    }
                }
            } catch (Exception ex) {
                String erro = "ResultSet - Erro ao listar " + NomeEntidade + ": " + ex;
                System.err.println(erro);
                throw new Exception(erro);
            }
        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return new ArrayList<>(map.values());
    }

    public Object conEditar(Pessoa pessoa) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            editar(con, pessoa);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao editar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID" + pessoa.getId() + " editado com sucesso.");
        return pessoa;
    }

    public Pessoa editar(Connection con, Pessoa pessoa) throws Exception {

        String sql = "UPDATE PESSOA SET NOME=? WHERE ID=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            ps.setString(1, pessoa.getNome());
            ps.setInt(2, pessoa.getId());
            ps.execute();

        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao editar " + NomeEntidade + " de ID" + pessoa.getId() + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return pessoa;
    }

    public boolean excluir(int id) throws Exception {

        String sql = "DELETE FROM PESSOA WHERE ID = ?";

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            try(PreparedStatement ps = con.prepareStatement(sql)){
                con.setAutoCommit(false);

                ps.setInt(1, id);
                ps.executeUpdate();

                con.commit();
            } catch (Exception ex) {
                String erro = "PreparedStatement - Erro ao excluir " + NomeEntidade + " de ID" + id + " no banco: " + ex;
                System.err.println(erro);
                throw new Exception(erro);
            }
        } catch (Exception ex) {
            String erro = "Connection - Erro ao excluir " + NomeEntidade + " de ID" + id + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de id. " + id + " excluido com sucesso.");
        return true;
    }
}
