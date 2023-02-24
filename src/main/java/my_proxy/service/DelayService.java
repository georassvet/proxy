package my_proxy.service;

import java.util.HashMap;
import java.util.Map;

public class DelayService {

    private static Map<Integer, Long> delays = new HashMap<>();


    public static void  setDelay(int port, long delay){
        delays.put(port, delay);
    }

    public static Long getDelay(int port) {
        return  delays.get(port);
    }


}
