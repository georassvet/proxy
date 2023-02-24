package my_proxy.form;

import lombok.Data;
import my_proxy.model.GroupModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class DelayForm {
    private int groupId;
    private int[] proxyId;
    private long startDelay;
    private long stopDelay;
    private long stepDelay;
    private long timeStep;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startAt;

    public DelayForm() {
    }

    public DelayForm(GroupModel item) {
        this.groupId = item.getId();
    }

    public DelayForm(int groupId) {
        this.groupId = groupId;
    }
}
