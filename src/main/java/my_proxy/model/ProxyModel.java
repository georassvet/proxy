package my_proxy.model;

import lombok.Data;

@Data
public class ProxyModel {
    private int id;
    private String name;
    private String remoteHost;
    private int remotePort;
    private int localPort;
    private GroupModel group;
    private long defaultDelay;

    public ProxyModel(int id, String name, String remoteHost, int remotePort, int groupId, String groupName, long defaultDelay) {
        this.id = id;
        this.name = name;
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.localPort = id;
        this.group = new GroupModel(groupId, groupName);
        this.defaultDelay = defaultDelay;
    }
}
