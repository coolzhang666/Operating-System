package top.coolzhang.table;

import java.util.ArrayList;
import java.util.List;

public class BusyTable {
    private List<Iteam> busyList = new ArrayList<>();
    private final int MAX_SIZE = 1000;
    private final int MEM_SIZE = 1000;

    public BusyTable() {
        this.busyList.clear();
    }

    public List<Iteam> getBusyList() {
        return this.busyList;
    }

    public void addIteam(Iteam a) {
        if (this.busyList.size() < MAX_SIZE) {
            this.busyList.add(a);
        } else {
            System.out.println("已分配分区表已满，插入失败");
        }
    }

    public void delIteam(Iteam a) {
        if(this.busyList.size() <= 0) {
            System.out.println("已分区分区表为空，删除失败");
        }else {
            this.busyList.remove(a);
        }
    }
}
