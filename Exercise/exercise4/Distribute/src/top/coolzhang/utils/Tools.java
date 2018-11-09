package top.coolzhang.utils;

import top.coolzhang.table.BusyTable;
import top.coolzhang.table.FreeTable;
import top.coolzhang.table.Iteam;

import java.util.List;

public class Tools {
    public void printTable(FreeTable freeTable, BusyTable busyTable) {
        List<Iteam> freeList = freeTable.getFreeList();
        List<Iteam> busyList = busyTable.getBusyList();

        // 打印空闲分区表
        System.out.println("空闲分区表：");
        for (Iteam i : freeList) {
            System.out.println("起始地址：" + i.getStart() + " 占用大小：" + i.getSize() + " 分区状态：" + i.getName());
        }

        // 打印已分配分区表
        System.out.println("已分配分区表：");
        for (Iteam i : busyList) {
            System.out.println("进程名：" + i.getName() + " 起始地址：" + i.getStart() + " 占用大小：" + i.getSize());
        }
    }
}
