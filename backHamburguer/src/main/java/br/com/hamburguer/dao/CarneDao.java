package br.com.hamburguer.dao;

import br.com.hamburguer.entidades.Carne;
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
public class CarneDao {
    String NomeEntidade = "Carne";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CarneDao(){}

    public Object conInserir(Carne carne) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            Object obj = inserir(con, carne);
            if(obj.getClass().getSimpleName().equals("Retorno")){
                return obj;
            }

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao inserir" + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID " + carne.getId() + " inserido com sucesso");
        return carne;
    }

    public Object inserir(Connection con, Carne carne) throws Exception {

        Object duplicidade = ChecarDuplicidade.carne(con, carne);
        if(!duplicidade.equals("cadastrar")){
            return duplicidade;
        }

        String sql = "INSERT INTO CARNE (PESOGRAMAS, ESTOQUE) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);

            ps.setInt(1, carne.getPesoGramas());
            ps.setInt(2, carne.getEstoque());
            ps.execute();

            try(ResultSet tableKeys = ps.getGeneratedKeys()){
                tableKeys.next();
                carne.setId(tableKeys.getInt(1));
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

        return carne;
    }

    public List<Carne> conListar() throws Exception {

        List<Carne> lista = new ArrayList<>();

        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {

            lista = listar(con);

        } catch (Exception ex) {
            String erro = "Connection - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return lista;
    }

    public List<Carne> listar(Connection con) throws Exception {

        String sql = "SELECT * FROM Carne\n";

        Map<Integer, Carne> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    Carne carne = map.get(id);
                    if(carne == null){
                        carne = new Carne();

                        carne.setId(rs.getInt("ID"));
                        carne.setPesoGramas(rs.getInt("PESOGRAMAS"));
                        carne.setEstoque(rs.getInt("ESTOQUE"));

                        map.put(carne.getId(), carne);
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

    public Carne obterPorId(Connection con, int idBusca) throws Exception {

        String sql = "SELECT * FROM Carne\n" +
                     "WHERE ID = " + idBusca;

        Carne carne = new Carne();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {

                    carne.setId(rs.getInt("ID"));
                    carne.setPesoGramas(rs.getInt("PESOGRAMAS"));
                    carne.setEstoque(rs.getInt("ESTOQUE"));

                }
            }
        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return carne;
    }

    public Object conEditar(Carne carne) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            editar(con, carne);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao editar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID" + carne.getId() + " editado com sucesso.");
        return carne;
    }

    public Carne editar(Connection con, Carne carne) throws Exception {

        String sql = "UPDATE CARNE SET PESOGRAMAS=?, ESTOQUE=? WHERE ID=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            ps.setInt(1, carne.getPesoGramas());
            ps.setInt(2, carne.getEstoque());
            ps.setInt(3, carne.getId());
            ps.execute();

        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao editar " + NomeEntidade + " de ID" + carne.getId() + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return carne;
    }

    public boolean excluir(int id) throws Exception {

        String sql = "DELETE FROM CARNE WHERE ID = ?";

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
