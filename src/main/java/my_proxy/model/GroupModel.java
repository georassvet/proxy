package my_proxy.model;

import lombok.Data;

@Data
public class GroupModel {
    private int id;
    private String name;

    public GroupModel(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
