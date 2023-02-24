package my_proxy.service;

import my_proxy.model.ProxyModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MetricService {
    Map<ProxyModel, AtomicLong> map = new HashMap<>();


}
