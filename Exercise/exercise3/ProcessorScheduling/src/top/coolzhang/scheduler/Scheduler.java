package top.coolzhang.scheduler;

import top.coolzhang.pcb.PCB;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Scheduler {
    // 进程队列
    public Queue<PCB> list = new LinkedList<>();
    // 就绪队列
    public Queue<PCB> ready_list = new LinkedList<>();
    // 完成列表
    public Queue<PCB> end_list = new LinkedList<>();

    Date date = new Date();
    long start;

    // 时间戳
    int time = 0;

    public void start() {
        start = System.currentTimeMillis();
        while (true) {
            System.out.println("0----退出");
            System.out.println("1----先来先服务");
            System.out.println("2----优先级调度");
            System.out.print("请选择：");
            int choose;
            choose = new Scanner(System.in).nextInt();
            if (choose == 0) {
                ready_list.clear();
                end_list.clear();
                break;
            }
            switch (choose) {
                case 1:
                    while (true) {
                        System.out.println("0----返回");
                        System.out.println("1----添加进程");
                        System.out.println("2----执行进程");
                        System.out.println("3----打印就绪列表");
                        System.out.println("4----打印各进程周转时间");
                        System.out.println("5----打印平均周转时间");
                        System.out.print("请选择：");
                        int flag;
                        flag = new Scanner(System.in).nextInt();
                        if (flag == 0) {
                            break;
                        }
                        switch (flag) {
                            case 1:
                                add_process();
                                break;
                            case 2:
                                run1();
                                break;
                            case 3:
                                print_ready();
                                break;
                            case 4:
                                time();
                                break;
                            case 5:
                                a_time();
                                break;
                        }
                    }
                    break;
                case 2:
                    while (true) {
                        System.out.println("0----返回");
                        System.out.println("1----添加进程");
                        System.out.println("2----执行进程");
                        System.out.println("3----打印就绪列表");
                        System.out.println("4----打印各进程周转时间");
                        System.out.println("5----打印平均周转时间");
                        System.out.print("请选择：");
                        int flag;
                        flag = new Scanner(System.in).nextInt();
                        if (flag == 0) {
                            ready_list.clear();
                            end_list.clear();
                            break;
                        }
                        switch (flag) {
                            case 1:
                                add_process1();
                                break;
                            case 2:
                                run2();
                                break;
                            case 3:
                                print_ready1();
                                break;
                            case 4:
                                time();
                                break;
                            case 5:
                                a_time();
                                break;
                        }
                    }
                    break;
            }

        }
    }

    // 先来先服务算法
    public void run1() {
        System.out.println("CPU开始运行......");
        while (true) {
            if (ready_list.size() <= 0) {
                System.out.println("当前没有进程可以执行, CPU空闲");
                break;
            } else {
                PCB process = get_process();
                System.out.println("正在执行进程 " + process.getP_name() + " 开始时间：" + ((System.currentTimeMillis() - start) / 1000));
                try {
                    Thread.sleep(process.getRequire_time() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                set_time((int)(System.currentTimeMillis() - start) / 1000);
                process.setEnd_time(get_time());
                System.out.println("进程 " + process.getP_name() + " 执行完毕,结束时间：" + process.getEnd_time());
                process.setP_status("end");
                end_list.add(process);
            }
        }
    }

    // 优先级调度
    public void run2() {
        System.out.println("Cpu开始运行......");
        while (true) {
            if (ready_list.size() <= 0) {
                System.out.println("当前没有进程可以执行, CPU空闲");
                break;
            } else {
                PCB[] a = ready_list.toArray(new PCB[ready_list.size()]);
                PCB temp = a[0];
                for (int i = 0; i < a.length; i++) {
                    if (a[i].getPriority() > temp.getPriority()) {
                        temp = a[i];
                    }
                }
                System.out.println("正在执行进程 " + temp.getP_name() + " 开始时间：" + ((System.currentTimeMillis() - start) / 1000));
                try {
                    Thread.sleep(temp.getRequire_time() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                set_time((int)(System.currentTimeMillis() - start) / 1000);
                temp.setEnd_time(get_time());
                System.out.println("进程 " + temp.getP_name() + " 执行完毕,结束时间：" + temp.getEnd_time());
                ready_list.remove(temp);
                temp.setP_status("end");
                end_list.add(temp);
            }
        }
    }

    // 添加进程
    synchronized public void add_process() {
        System.out.print("请输入进程名：");
        String p_name = new Scanner(System.in).nextLine();
        System.out.print("请输入进程运行所需时间：");
        int require_time = new Scanner(System.in).nextInt();
        PCB process = new PCB(p_name, require_time);
        int t = (int) (System.currentTimeMillis() - start) / 1000;
        process.setArrive_time(t);
        time = t;
        list.offer(process);
        process.setP_id(list.size());
        ready_list.offer(process);
    }

    public void add_process1() {
        System.out.print("请输入进程名：");
        String p_name = new Scanner(System.in).nextLine();
        System.out.print("请输入进程运行所需时间：");
        int require_time = new Scanner(System.in).nextInt();
        System.out.print("请输入进程优先级：");
        int priority = new Scanner(System.in).nextInt();
        PCB process = new PCB(p_name, require_time, priority);
        int t = (int) (System.currentTimeMillis() - start) / 1000;
        process.setArrive_time(t);
        time = t;
        list.offer(process);
        process.setP_id(list.size());
        ready_list.offer(process);
    }

    public void print_ready() {
        for (PCB p : ready_list) {
            System.out.println("进程id：" + p.getP_id() + " 进程名：" + p.getP_name() + " 进程状态：" + p.getP_status() + " 进程到达时间：" + p.getArrive_time() + " 进程需要时间" + p.getRequire_time());
        }
    }

    public void print_ready1() {
        for (PCB p : ready_list) {
            System.out.println("进程id：" + p.getP_id() + " 进程名：" + p.getP_name() + " 进程状态：" + p.getP_status() + " 进程到达时间：" + p.getArrive_time() + " 进程需要时间" + p.getRequire_time() + " 进程优先级：" + p.getPriority());
        }
    }

    public void time() {
        for (PCB p : end_list) {
            System.out.println("进程id：" + p.getP_id() + " 进程名：" + p.getP_name() + " 周转时间：" + (p.getEnd_time() - p.getArrive_time()));
        }
    }

    public void a_time() {
        int sum = 0;
        for (PCB p : end_list) {
            sum += p.getEnd_time() - p.getArrive_time();
        }
        System.out.println("平均周转时间为：" + (sum / end_list.size()));
    }

    synchronized public PCB get_process() {
        return ready_list.poll();
    }

    synchronized public Queue<PCB> getEndProcess() {
        return end_list;
    }

    synchronized public void addEndProcess(PCB p) {
        end_list.offer(p);
    }

    synchronized public void set_time(int time) {
        this.time = time;
    }

    synchronized public int get_time() {
        return time;
    }
}
