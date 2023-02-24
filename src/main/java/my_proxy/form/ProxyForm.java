package my_proxy.form;

import lombok.Data;
import my_proxy.model.ProxyModel;

@Data
public class ProxyForm {
    private int id;
    private String name;
    private String remoteHost;
    private int remotePort;
    private long delay;
    private int groupId;

    public ProxyForm(int id) {
        this.groupId = id;
    }
    public ProxyForm() {
    }
    public ProxyForm(ProxyModel proxyModel) {
        this.id = proxyModel.getId();
        this.name = proxyModel.getName();
        this.groupId = proxyModel.getGroup().getId();
        this.remoteHost = proxyModel.getRemoteHost();
        this.remotePort = proxyModel.getRemotePort();
        this.delay = proxyModel.getDefaultDelay();
    }
}
