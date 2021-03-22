package com.cjt.test.util.schedule;

public abstract class Task {

    String  cronExp;
    public String getCronExp() {
        return cronExp;
    }

    public void setCronExp(String cronExp) {
        this.cronExp = cronExp;
    }

    abstract public void execute() throws Exception;

}
