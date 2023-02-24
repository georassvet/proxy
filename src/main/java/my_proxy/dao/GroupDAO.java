package my_proxy.dao;

import my_proxy.form.GroupForm;
import my_proxy.form.ProxyForm;
import my_proxy.helper.DbUtils;
import my_proxy.mapper.GroupMapper;
import my_proxy.mapper.ProxyMapper;
import my_proxy.model.GroupModel;
import my_proxy.model.ProxyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GroupDAO {

    @Autowired
    GroupMapper mapper;

    public List<GroupModel> getAll() {
        List<GroupModel> result = new ArrayList<>();
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

   public int add(GroupForm form){
        int res = 0;
        String sql = "insert into proxy_group(name) values (?)";
        try (Connection connection = DriverManager.getConnection(DbUtils.getUrl());
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1,form.getName());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    public int update(GroupForm form) {
        int res = 0;
        String sql = "update proxy_group set name=? where id=?";
        try (Connection connection = DriverManager.getConnection(DbUtils.getUrl());
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1,form.getName());
            statement.setInt(2,form.getId());
            res = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public GroupModel getById(int id) {
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
}
