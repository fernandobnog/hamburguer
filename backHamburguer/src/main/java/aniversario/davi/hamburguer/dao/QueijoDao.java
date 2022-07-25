package aniversario.davi.hamburguer.dao;

import aniversario.davi.hamburguer.entidades.Pao;
import aniversario.davi.hamburguer.entidades.Queijo;
import aniversario.davi.hamburguer.interfaces.ChecarDuplicidade;
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
public class QueijoDao {
    String NomeEntidade = "Queijo";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public QueijoDao(){}

    public Object conInserir(Queijo queijo) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            Object obj = inserir(con, queijo);
            if(obj.getClass().getSimpleName().equals("Retorno")){
                return obj;
            }

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao inserir" + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID " + queijo.getId() + " inserido com sucesso");
        return queijo;
    }

    public Object inserir(Connection con, Queijo queijo) throws Exception {

        Object duplicidade = ChecarDuplicidade.queijo(con, queijo);
        if(!duplicidade.equals("cadastrar")){
            return duplicidade;
        }

        String sql = "INSERT INTO QUEIJO (TIPO, ESTOQUE) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);

            ps.setString(1, queijo.getTipo());
            ps.setInt(2, queijo.getEstoque());
            ps.execute();

            try(ResultSet tableKeys = ps.getGeneratedKeys()){
                tableKeys.next();
                queijo.setId(tableKeys.getInt(1));
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

        return queijo;
    }

    public List<Queijo> conListar() throws Exception {

        List<Queijo> lista = new ArrayList<>();

        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {

            lista = listar(con);

        } catch (Exception ex) {
            String erro = "Connection - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return lista;
    }

    public List<Queijo> listar(Connection con) throws Exception {

        String sql = "SELECT * FROM QUEIJO\n";

        Map<Integer, Queijo> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    Queijo queijo = map.get(id);
                    if(queijo == null){
                        queijo = new Queijo();

                        queijo.setId(rs.getInt("ID"));
                        queijo.setTipo(rs.getString("TIPO"));
                        queijo.setEstoque(rs.getInt("ESTOQUE"));

                        map.put(queijo.getId(), queijo);
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

    public Queijo obterPorId(Connection con, int idBusca) throws Exception {

        String sql = "SELECT * FROM QUEIJO\n" +
                     "WHERE ID = " + idBusca;

        Queijo queijo = new Queijo();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {

                    queijo.setId(rs.getInt("ID"));
                    queijo.setTipo(rs.getString("TIPO"));
                    queijo.setEstoque(rs.getInt("ESTOQUE"));

                }
            }
        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return queijo;
    }

    public Object conEditar(Queijo queijo) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            editar(con, queijo);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao editar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID" + queijo.getId() + " editado com sucesso.");
        return queijo;
    }

    public Queijo editar(Connection con, Queijo queijo) throws Exception {

        String sql = "UPDATE QUEIJO SET TIPO=?, ESTOQUE=? WHERE ID=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            ps.setString(1, queijo.getTipo());
            ps.setInt(2, queijo.getEstoque());
            ps.setInt(3, queijo.getId());
            ps.execute();

        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao editar " + NomeEntidade + " de ID" + queijo.getId() + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return queijo;
    }

    public boolean excluir(int id) throws Exception {

        String sql = "DELETE FROM QUEIJO WHERE ID = ?";

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
