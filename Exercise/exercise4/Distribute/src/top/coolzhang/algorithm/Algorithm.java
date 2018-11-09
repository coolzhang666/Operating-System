package top.coolzhang.algorithm;

import top.coolzhang.table.BusyTable;
import top.coolzhang.table.FreeTable;
import top.coolzhang.table.Iteam;

import java.util.Collections;
import java.util.List;

public class Algorithm {
    private int point = 0;

    // 首次适应算法
    public void FF(FreeTable freeTable, BusyTable busyTable, Iteam iteam) {
        List<Iteam> freeList = freeTable.getFreeList();
        List<Iteam> busyList = busyTable.getBusyList();
        for (int i = 0; i < freeList.size(); i++) {
            Iteam a = freeList.get(i);
            if (a.getSize() >= iteam.getSize()) {
                // 更新已分配分区表
                Iteam b = new Iteam(iteam.getName(), iteam.getSize());
                b.setStart(a.getStart());
                busyList.add(b);

                // 更新空闲分区表
                iteam.setStart(a.getStart() + b.getSize());
                iteam.setSize(a.getSize() - iteam.getSize());
                iteam.setName("empty");
                freeList.remove(a);
                freeList.add(iteam);
                Collections.sort(freeList);
                break;
            }
        }
    }

    // 循环首次适应算法
    public void RFF(FreeTable freeTable, BusyTable busyTable, Iteam iteam) {
        List<Iteam> freeList = freeTable.getFreeList();
        List<Iteam> busyList = busyTable.getBusyList();
        int demo = point;
        boolean found = false;
        for (int i = point; i < freeList.size(); i++) {
            Iteam a = freeList.get(i);
            if (a.getSize() >= iteam.getSize()) {
                // 更新已分配分区表
                Iteam b = new Iteam(iteam.getName(), iteam.getSize());
                b.setStart(a.getStart());
                busyList.add(b);

                // 更新空闲分区表
                iteam.setStart(a.getStart() + b.getSize());
                iteam.setSize(a.getSize() - iteam.getSize());
                iteam.setName("empty");
                freeList.remove(a);
                freeList.add(iteam);
                Collections.sort(freeList);
                point = i + 1;
                found = true;
                break;
            }
        }
        if (!found) {
            for (int i = 0; i < demo; i++) {
                Iteam a = freeList.get(i);
                if (a.getSize() >= iteam.getSize()) {
                    // 更新已分配分区表
                    Iteam b = new Iteam(iteam.getName(), iteam.getSize());
                    b.setStart(a.getStart());
                    busyList.add(b);

                    // 更新空闲分区表
                    iteam.setStart(a.getStart() + b.getSize());
                    iteam.setSize(a.getSize() - iteam.getSize());
                    iteam.setName("empty");
                    freeList.remove(a);
                    freeList.add(iteam);
                    Collections.sort(freeList);
                    point = i + 1;
                    break;
                }
            }
        }
    }

    public void recycle(FreeTable freeTable, BusyTable busyTable, String name) {
        List<Iteam> freeList = freeTable.getFreeList();
        List<Iteam> busyList = busyTable.getBusyList();

//        for (Iteam iteam : busyList) {
        for (int k = 0; k < busyList.size(); k++) {
            Iteam iteam = busyList.get(k);
            if (name.equals(iteam.getName())) {
                // 将进程 iteam 从已分配分区表中删除
                busyList.remove(iteam);

                // 向空闲分区表中加入已释放的空间，并合并相邻的空闲分区
                Iteam before = null;
                Iteam after = null;
                for (Iteam i : freeList) {
                    if (i.getStart() + i.getSize() == iteam.getStart()) {
                        before = i;
                    }
                    if (i.getStart() == iteam.getStart() + iteam.getSize()) {
                        after = i;
                    }
                }

                // 情况1：释放的分区的前后都相邻空闲分区
                if (before != null && after != null) {
                    freeList.remove(before);
                    freeList.remove(after);
                    Iteam new_one = new Iteam("empty", before.getSize() + iteam.getSize() + after.getSize());
                    new_one.setStart(before.getStart());
                    freeList.add(new_one);
                    Collections.sort(freeList);
                }

                // 情况2：释放的分区的前面相邻空闲分区
                if (before != null && after == null) {
                    freeList.remove(before);
                    Iteam new_one = new Iteam("empty", before.getSize() + iteam.getSize());
                    new_one.setStart(before.getStart());
                    freeList.add(new_one);
                    Collections.sort(freeList);
                }

                // 情况3：释放分区的后面相邻空闲分区
                if (before == null && after != null) {
                    freeList.remove(after);
                    Iteam new_one = new Iteam("empty", iteam.getSize() + after.getSize());
                    new_one.setStart(iteam.getStart());
                    freeList.add(new_one);
                    Collections.sort(freeList);
                }

                // 情况4：释放分区的前后都没有相邻的空闲分区
                if (before == null && after == null) {
                    Iteam new_one = new Iteam("empty", iteam.getSize());
                    new_one.setStart(iteam.getStart());
                    freeList.add(new_one);
                    Collections.sort(freeList);
                }
            }
        }
    }
}
