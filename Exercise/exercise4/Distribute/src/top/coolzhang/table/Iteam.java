package top.coolzhang.table;

public class Iteam implements Comparable<Iteam>{
    private String name;
    private int start;
    private int size;

    public Iteam(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(Iteam o) {
        return getStart() - o.getStart();
    }
}
