package my_proxy.helper;

import lombok.Data;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Data
@Service
public class DbUtils {

    private static String url;

    public static String getUrl(){
        return url;
    }

    @EventListener(ApplicationReadyEvent.class)
    void init() throws SQLException, IOException {
        String path= "";
        if(System.getProperty("os.name").contains("Windows")) {
            path = "C:/sqlite/db/proxy";
        }else {
            path = "./sqlite/db/proxy";
        }
        Path dbPath = Paths.get(path);
        if(Files.notExists(dbPath)) {
            Files.createDirectories(dbPath);
            System.out.println("dir created");
        }

        url = "jdbc:sqlite:" + path+"/sqlite.db";

        updateDb(drop_table_proxy_test);
        updateDb(drop_table_proxy);
        updateDb(drop_table_group);
        updateDb(drop_table_test);

        updateDb(table_temp);
        updateDb(table_temp_drop);
        updateDb(table_group);
        updateDb(table_proxy);
        updateDb(table_test);
        updateDb(table_proxy_test);
        updateDb(table_proxy_set_seq);
     //   updateDb(table_proxy_group_insert);
     //   updateDb(table_proxy_insert);

    }

    private String drop_table_proxy = "drop table if exists proxy;";
    private String drop_table_group = "drop table if exists proxy_group;";
    private String drop_table_test = "drop table if exists test;";
    private String drop_table_proxy_test = "drop table proxy_test;";
    private String table_temp = "CREATE TABLE IF NOT EXISTS temp (\n" +
            "    id                INTEGER PRIMARY KEY AUTOINCREMENT )";

    private String table_temp_drop = "DROP TABLE if exists temp";

    private String table_proxy = "CREATE TABLE IF NOT EXISTS proxy (\n" +
            "    id                INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    name              CHAR,\n" +
            "    remote_host       CHAR,\n" +
            "    remote_port       INTEGER,\n" +
            "    delay             INTEGER,\n" +
            "    enable            BOOLEAN,\n" +
            "    group_id          INTEGER REFERENCES proxy_group (id)\n" +
            ")";


    private String table_group = "CREATE TABLE IF NOT EXISTS proxy_group (\n" +
            "    id                INTEGER PRIMARY KEY,\n" +
            "    name              CHAR\n" +
            ")";

    private String table_proxy_group_insert = "INSERT INTO proxy_group (id, name) VALUES (1, 'starter'); COMMIT; ";

    private String table_test = "CREATE TABLE IF NOT EXISTS test (\n" +
            "    id                INTEGER PRIMARY KEY,\n" +
            "    name              CHAR\n" +
            "    time_before       INTEGER,\n" +
            "    time_between      INTEGER,\n" +
            "    time_after        INTEGER,\n" +
            "    type              CHAR,   \n" +
            "    test_order        INTEGER \n" +
            ")";
    private String table_proxy_test = "CREATE TABLE IF NOT EXISTS proxy_test (\n" +
            "    id                INTEGER PRIMARY KEY,\n" +
            "    test_id           INTEGER REFERENCES test (id), \n" +
            "    proxy_id          INTEGER REFERENCES proxy (id), \n" +
            "    delay             INTEGER\n " +
            ")";

    private String table_proxy_set_seq =
                    " insert into sqlite_sequence(name,seq) values ('proxy', 10000) ;";

    private void updateDb(String query){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement()){
            statement.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
