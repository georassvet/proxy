package my_proxy.model.test;

import lombok.Data;
import my_proxy.model.ProxyModel;

@Data
public class ProxyTest extends Test {
    private int id;
    private long timeOffset;
    private long setValue;
    private ProxyModel proxy;

    public ProxyTest() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
