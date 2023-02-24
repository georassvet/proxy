package my_proxy.service;

import lombok.SneakyThrows;
import my_proxy.dao.ProxyDAO;
import my_proxy.form.DelayForm;
import my_proxy.model.DelayTask;
import my_proxy.model.ProxyModel;
import my_proxy.model.ProxyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class ProxyService {
    ExecutorService service = Executors.newFixedThreadPool(10);

    @Autowired
    private ProxyDAO dao;
    Map<Integer, ProxyTask> proxyTasks = new HashMap<>();
    private static int counter = 0;
    List<DelayTask> delayTasks = new ArrayList<>();

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
   void runAll(){
        List<ProxyModel> items = dao.getAll();
        for (ProxyModel item: items) {
            service.submit(new ProxyTask(item));
        }
    }

    public List<DelayTask> getDelayTasks(int groupId){
        return delayTasks.stream().filter(x->x.getGroupId() == groupId).sorted(Comparator.comparingInt(DelayTask::getId)).collect(Collectors.toList());
    }

    public void createTasks(DelayForm form) {
        int[] proxies = form.getProxyId();
        for(int proxyId : proxies){

            long startDelay = form.getStartDelay();
            LocalDateTime startTime = form.getStartAt()==null?LocalDateTime.now():form.getStartAt();
            do{
                DelayTask delayTask = new DelayTask(++counter, proxyId, form.getGroupId(), startDelay, startTime);
                delayTask.setTask(scheduler.schedule(delayTask, Date.from(startTime.toInstant(ZoneOffset.UTC))));
                delayTasks.add(delayTask);
                startDelay = startDelay + form.getStepDelay();
                startTime = startTime.plusMinutes(form.getTimeStep());

            }
            while(startDelay < form.getStopDelay());
        }
    }
}
