package my_proxy.mapper;

import my_proxy.model.ProxyModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProxyMapper {

    public String query_all = "select p.id, p.name, p.remote_host,p. remote_port, g.id group_id, g.name group_name, p.delay from proxy p join proxy_group g on p.group_id=g.id";
    public String query_id = query_all + " where p.id =?";
    public String query_group_id = query_all + " where g.id =?";

    public ProxyModel mapRow(@NotNull ResultSet rs) throws SQLException {
        return new ProxyModel(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("remote_host"),
                rs.getInt("remote_port"),
                rs.getInt("group_id"),
                rs.getString("group_name"),
                rs.getLong("delay")
        );
    }
}
