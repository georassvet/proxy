package my_proxy.model.test;

public  abstract class Test {
    private int id;
    private String name;
    private int timeBefore;
    private int timeBetween;
    private int timeAfter;

    public abstract void start();
    public abstract void stop();
}
