package my_proxy.model;

import lombok.Data;
import my_proxy.form.DelayForm;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

@Data
public class DelayTask implements Runnable {
    private int id;
    private int groupId;
    private long delay;
    private int proxyId;
    private LocalDateTime time;

    ScheduledFuture<?> task;

    public DelayTask(int id, int proxyId, int groupId, long delay, LocalDateTime time) {
       this.id = id;
        this.proxyId = proxyId;
        this.groupId = groupId;
        this.delay = delay;
        this.time = time;

    }

    @Override
    public void run() {

    }
}
