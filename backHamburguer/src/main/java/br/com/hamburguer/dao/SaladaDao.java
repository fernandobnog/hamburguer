package br.com.hamburguer.dao;

import br.com.hamburguer.entidades.Salada;
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
public class SaladaDao {
    String NomeEntidade = "Salada";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SaladaDao(){}

    public Object conInserir(Salada salada) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            Object obj = inserir(con, salada);
            if(obj.getClass().getSimpleName().equals("Retorno")){
                return obj;
            }

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao inserir" + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID " + salada.getId() + " inserido com sucesso");
        return salada;
    }

    public Object inserir(Connection con, Salada salada) throws Exception {

        Object duplicidade = ChecarDuplicidade.salada(con, salada);
        if(!duplicidade.equals("cadastrar")){
            return duplicidade;
        }

        String sql = "INSERT INTO SALADA (TIPO) VALUES (?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);

            ps.setString(1, salada.getTipo());
            ps.execute();

            try(ResultSet tableKeys = ps.getGeneratedKeys()){
                tableKeys.next();
                salada.setId(tableKeys.getInt(1));
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

        return salada;
    }

    public List<Salada> conListar() throws Exception {

        List<Salada> lista = new ArrayList<>();

        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {

            lista = listar(con);

        } catch (Exception ex) {
            String erro = "Connection - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return lista;
    }

    public List<Salada> listar(Connection con) throws Exception {

        String sql = "SELECT * FROM SALADA\n";

        Map<Integer, Salada> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    Salada salada = map.get(id);
                    if(salada == null){
                        salada = new Salada();

                        salada.setId(rs.getInt("ID"));
                        salada.setTipo(rs.getString("TIPO"));

                        map.put(salada.getId(), salada);
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

    public Object conEditar(Salada salada) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            editar(con, salada);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao editar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID" + salada.getId() + " editado com sucesso.");
        return salada;
    }

    public Salada editar(Connection con, Salada salada) throws Exception {

        String sql = "UPDATE SALADA SET TIPO=? WHERE ID=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            ps.setString(1, salada.getTipo());
            ps.setInt(2, salada.getId());
            ps.execute();

        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao editar " + NomeEntidade + " de ID" + salada.getId() + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return salada;
    }

    public boolean excluir(int id) throws Exception {

        String sql = "DELETE FROM SALADA WHERE ID = ?";

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

        System.out.println(NomeEntidade + " de id. " + id + " excluida com sucesso.");
        return true;
    }
}
