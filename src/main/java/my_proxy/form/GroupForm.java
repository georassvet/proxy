package my_proxy.form;

import lombok.Data;
import my_proxy.model.GroupModel;

@Data
public class GroupForm {
    private int id;
    private String name;

    public GroupForm() {

    }

    public GroupForm(GroupModel model) {
        this.id = model.getId();
        this.name = model.getName();
    }
}
