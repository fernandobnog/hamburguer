package aniversario.davi.hamburguer.dao;

import aniversario.davi.hamburguer.entidades.Carne;
import aniversario.davi.hamburguer.entidades.Pao;
import aniversario.davi.hamburguer.interfaces.ChecarDuplicidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class PaoDao {
    String NomeEntidade = "Pao";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PaoDao(){}

    public Object conInserir(Pao pao) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            Object obj = inserir(con, pao);
            if(obj.getClass().getSimpleName().equals("Retorno")){
                return obj;
            }

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao inserir" + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID " + pao.getId() + " inserido com sucesso");
        return pao;
    }

    public Object inserir(Connection con, Pao pao) throws Exception {

        Object duplicidade = ChecarDuplicidade.pao(con, pao);
        if(!duplicidade.equals("cadastrar")){
            return duplicidade;
        }

        String sql = "INSERT INTO PAO (TIPO, ESTOQUE) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);

            ps.setString(1, pao.getTipo());
            ps.setInt(2, pao.getEstoque());
            ps.execute();

            try(ResultSet tableKeys = ps.getGeneratedKeys()){
                tableKeys.next();
                pao.setId(tableKeys.getInt(1));
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

        return pao;
    }

    public List<Pao> conListar() throws Exception {

        List<Pao> lista = new ArrayList<>();

        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {

            lista = listar(con);

        } catch (Exception ex) {
            String erro = "Connection - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return lista;
    }

    public List<Pao> listar(Connection con) throws Exception {

        String sql = "SELECT * FROM PAO\n";

        Map<Integer, Pao> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    Pao pao = map.get(id);
                    if(pao == null){
                        pao = new Pao();

                        pao.setId(rs.getInt("ID"));
                        pao.setTipo(rs.getString("TIPO"));
                        pao.setEstoque(rs.getInt("ESTOQUE"));

                        map.put(pao.getId(), pao);
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


    public Pao obterPorId (Connection con, int idBusca) throws Exception {

        String sql = "SELECT * FROM PAO\n" +
                     "WHERE ID = " + idBusca;

        Pao pao = new Pao();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {

                    pao.setId(rs.getInt("ID"));
                    pao.setTipo(rs.getString("TIPO"));
                    pao.setEstoque(rs.getInt("ESTOQUE"));

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

        return pao;
    }

    public Object conEditar(Pao pao) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            editar(con, pao);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao editar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID" + pao.getId() + " editado com sucesso.");
        return pao;
    }

    public Pao editar(Connection con, Pao pao) throws Exception {

        String sql = "UPDATE PAO SET TIPO=?, ESTOQUE=? WHERE ID=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            ps.setString(1, pao.getTipo());
            ps.setInt(2, pao.getEstoque());
            ps.setInt(3, pao.getId());
            ps.execute();

        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao editar " + NomeEntidade + " de ID" + pao.getId() + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return pao;
    }

    public boolean excluir(int id) throws Exception {

        String sql = "DELETE FROM PAO WHERE ID = ?";

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
