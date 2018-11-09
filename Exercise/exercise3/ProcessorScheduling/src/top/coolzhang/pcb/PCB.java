package top.coolzhang.pcb;

public class PCB {
    int p_id;
    String p_name;
    String p_status;
    int arrive_time;
    int require_time;
    int priority;
    int end_time;

    public PCB(String p_name, int require_time) {
        this.p_id = 0;
        this.p_name = p_name;
        this.p_status = "ready";
        this.arrive_time = 0;
        this.require_time = require_time;
    }

    public PCB(String p_name, int require_time, int priority) {
        this.p_id = 0;
        this.p_name = p_name;
        this.p_status = "ready";
        this.arrive_time = 0;
        this.priority = priority;
        this.require_time = require_time;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_status() {
        return p_status;
    }

    public void setP_status(String p_status) {
        this.p_status = p_status;
    }

    public int getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(int arrive_time) {
        this.arrive_time = arrive_time;
    }

    public int getRequire_time() {
        return require_time;
    }

    public void setRequire_time(int require_time) {
        this.require_time = require_time;
    }

    public int getPriority() {
        return priority;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }
}
