package my_proxy.mapper;

import my_proxy.model.GroupModel;
import my_proxy.model.ProxyModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GroupMapper {

    public String query_all = "select g.id, g.name from proxy_group g";
    public String query_id = query_all + " where id =?";

    public GroupModel mapRow(@NotNull ResultSet rs) throws SQLException {
        return new GroupModel(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}
