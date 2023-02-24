package my_proxy.form;

import lombok.Data;
import my_proxy.model.ProxyModel;

@Data
public class ProxyTestForm {
    private int id;
    private String name;
    private int[] proxies;


    public ProxyTestForm(int id) {
    }
    public ProxyTestForm() {
    }
    public ProxyTestForm(ProxyModel proxyModel) {
        this.id = proxyModel.getId();
        this.name = proxyModel.getName();
    }
}
