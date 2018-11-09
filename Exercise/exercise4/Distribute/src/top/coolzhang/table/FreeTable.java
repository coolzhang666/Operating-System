package top.coolzhang.table;

import java.util.ArrayList;
import java.util.List;

public class FreeTable {
    private List<Iteam> freeList = new ArrayList<>();
    private final int MAX_SIZE = 1000;
    private final int MEM_SIZE = 120;

    public FreeTable() {
        this.freeList.clear();
        Iteam iteam = new Iteam("empty", MEM_SIZE);
        iteam.setStart(0);
        this.freeList.add(iteam);
    }

    public List<Iteam> getFreeList() {
        return this.freeList;
    }

    public void addIteam(Iteam a) {
        if (this.freeList.size() < MAX_SIZE) {
            this.freeList.add(a);
        } else {
            System.out.println("空闲分区表已满，插入失败");
        }
    }

    public void delIteam(Iteam a) {
        if (this.freeList.size() <= 0) {
            System.out.println("空闲分区表为空，删除失败");
        }else {
            this.freeList.remove(a);
        }
    }
}

