package my_proxy.mapper;

import my_proxy.model.ProxyModel;
import my_proxy.model.test.ProxyTest;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProxyTestMapper {

    public String query_all = "select * from test t join proxy_test pt on t.id = pt.test_id";
    public String query_id = query_all + " where t.id = ?";
    public String query_group_id = query_all + " join proxy p on pt.proxy_id = p.id" +
            " where p.group_id = ?";

    public ProxyTest mapRow(@NotNull ResultSet rs) throws SQLException {
        return new ProxyTest();
    }
}
