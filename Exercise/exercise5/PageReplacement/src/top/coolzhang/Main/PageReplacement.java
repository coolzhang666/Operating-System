package top.coolzhang.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PageReplacement {

    public static  void main(String[] args) {
        Random random = new Random();
        int num = 50;
//        int[] a = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1};
        int[] a = new int[num];
        for (int i = 0; i < num; i++) {
            a[i] = random.nextInt(5);
        }
        FIFO f = new FIFO(3, a);
        f.fifo();
    }

}


class FIFO {
    private int size;
    private int[] numberList;
    private List<Iteam> pageList = new ArrayList<>();

    public FIFO(int size, int[] numberList) {
        this.size = size;
        this.numberList = numberList;
    }

    public void fifo() {
        System.out.print("页面序列：\t");
        for (int i = 0; i < numberList.length; i++) {
            System.out.print(numberList[i] + "\t\t");
        }
        System.out.println();
        System.out.print("置换情况：\t");
        int count = 0;
        for (int i = 0; i < numberList.length; i++) {
            Iteam it = new Iteam(numberList[i]);
            boolean has = false;
            for (Iteam iteam : pageList) {
                if (iteam.getPageNumber() == it.getPageNumber()) {
                    has = true;
                    break;
                }
            }
            if (!has) {
                if (pageList.size() < 3) {
                    pageList.add(it);
                } else {
                    Iteam flag = pageList.get(0);
                    for (Iteam iteam : pageList) {
                        if (iteam.getLiveTime() > flag.getLiveTime()) {
                            flag = iteam;
                        }
                    }
                    pageList.set(pageList.indexOf(flag), it);
                }

                for (int j = 0; j < pageList.size(); j++) {
                    System.out.print(pageList.get(j).getPageNumber());
                }
                System.out.print("\t\t");
                count++;
            }else {
                System.out.print("\t\t");
            }

            for (Iteam iteam : pageList) {
                iteam.setLiveTime(iteam.getLiveTime() + 1);
            }
        }

        System.out.println("\n缺页次数： " + count + "次");
        System.out.println("缺页率： " + count + "/" + numberList.length);
    }
}


// Iteam类，用于存放页面信息
class Iteam {
    private int pageNumber;
    private int liveTime;

    public Iteam(int pageNumber) {
        this.pageNumber = pageNumber;
        this.liveTime = 0;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setLiveTime(int liveTime) {
        this.liveTime = liveTime;
    }

    public int getLiveTime() {
        return this.liveTime;
    }
}