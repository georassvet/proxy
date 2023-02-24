package my_proxy.dao;

import my_proxy.form.ProxyForm;
import my_proxy.helper.DbUtils;
import my_proxy.mapper.ProxyMapper;
import my_proxy.model.ProxyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProxyDAO {

    @Autowired
    ProxyMapper mapper;

    public List<ProxyModel> getAll() {
        List<ProxyModel> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DbUtils.getUrl());
             PreparedStatement statement = connection.prepareStatement(mapper.query_all)
        ) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                do {
                    result.add(mapper.mapRow(rs));
                }while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

   public int add(ProxyForm form){
        int res = 0;
        String sql = "insert into proxy(name, remote_host, remote_port, delay, group_id) values (?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(DbUtils.getUrl());
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1,form.getName());
            statement.setString(2,form.getRemoteHost());
            statement.setInt(3,form.getRemotePort());
            statement.setLong(4,form.getDelay());
            statement.setInt(5,form.getGroupId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    public int update(ProxyForm form) {
        int res = 0;
        String sql = "update proxy set name=?, remote_host=?, remote_port=?, delay=?, group_id=? where id=?";
        try (Connection connection = DriverManager.getConnection(DbUtils.getUrl());
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1,form.getName());
            statement.setString(2,form.getRemoteHost());
            statement.setInt(3,form.getRemotePort());
            statement.setLong(4,form.getDelay());
            statement.setInt(5,form.getGroupId());
            statement.setInt(6,form.getId());
            res = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public int delete(int id) {
        int res = 0;
        String sql = "delete from proxy where id=?";
        try (Connection connection = DriverManager.getConnection(DbUtils.getUrl());
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);
            res = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    public ProxyModel getById(int id) {
        try (Connection connection = DriverManager.getConnection(DbUtils.getUrl());
             PreparedStatement statement = connection.prepareStatement(mapper.query_id)
        ) {
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return  mapper.mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProxyModel> getByGroupId(int id) {
        List<ProxyModel> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DbUtils.getUrl());
             PreparedStatement statement = connection.prepareStatement(mapper.query_group_id)
        ) {
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                do {
                    result.add(mapper.mapRow(rs));
                }while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
