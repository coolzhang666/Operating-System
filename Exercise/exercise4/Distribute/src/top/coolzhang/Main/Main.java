package top.coolzhang.Main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import top.coolzhang.algorithm.Algorithm;
import top.coolzhang.table.BusyTable;
import top.coolzhang.table.FreeTable;
import top.coolzhang.table.Iteam;
import top.coolzhang.utils.Tools;

import java.util.List;

public class Main extends Application {
    private FreeTable freeTable = new FreeTable();
    private BusyTable busyTable = new BusyTable();
    private Tools tools = new Tools();
    private final ToggleGroup group = new ToggleGroup();
    private Algorithm algorithm = new Algorithm();
    private Rectangle rectangle = new Rectangle(80, 0, 250, 720);
    private Text low = new Text(340, 6, "0");
    private Text high = new Text(340, 720, "120");

    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());


        // 控件定义区---------------------------------------------
        // 顶部 HBox
        HBox top = new HBox();
        top.setId("top");
        top.setAlignment(Pos.CENTER);
        Text title = new Text("内存空间动态分区分配与回收实验可视化程序");
        top.getChildren().add(title);

        // 底部 HBox
        HBox bottom = new HBox();

        // 右方 VBox
        VBox right = new VBox();
        right.setId("right");
        right.setAlignment(Pos.CENTER);

        VBox vBox1 = new VBox();
        HBox hBox1 = new HBox();
        Text text1 = new Text("请选择分配算法:");
        hBox1.getChildren().add(text1);

        HBox hBox2 = new HBox();
        RadioButton r1 = new RadioButton("首次适应算法");
        RadioButton r2 = new RadioButton("循环首次适应算法");
        r1.setSelected(true);
        r1.setToggleGroup(group);
        r2.setToggleGroup(group);
        hBox2.getChildren().addAll(r1, r2);

        HBox hBox3 = new HBox();
        Text t1 = new Text("输入进程名");
        TextField name = new TextField();
        hBox3.getChildren().addAll(t1, name);

        HBox hBox4 = new HBox();
        Text t2 = new Text("输入进程大小");
        TextField size = new TextField();
        hBox4.getChildren().addAll(t2, size);

        HBox hBox5 = new HBox();
        Button button = new Button("添加进程");
        hBox5.getChildren().add(button);

        vBox1.getChildren().addAll(hBox1, hBox2, hBox3, hBox4, hBox5);


        VBox vBox2 = new VBox();
        HBox hBox6 = new HBox();
        Text t3 = new Text("输入进程名");
        TextField name1 = new TextField();
        hBox6.getChildren().addAll(t3, name1);

        HBox hBox7 = new HBox();
        Button button1 = new Button("删除进程");
        hBox7.getChildren().add(button1);

        vBox2.getChildren().addAll(hBox6, hBox7);


        right.getChildren().addAll(vBox1, vBox2);


        // 左方部分-------------------------------------------
        VBox left = new VBox();
        left.setId("left");


        // 中心部分-------------------------------------------
        Pane pane1 = new Pane();
        pane1.setId("center");
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        pane1.getChildren().addAll(rectangle, low, high);

        pane.setTop(top);
        pane.setRight(right);
        pane.setLeft(left);
        pane.setCenter(pane1);


        // 控件事件区----------------------------------------------
        button.setOnMouseClicked(event -> {
            String p_name = name.getText();
            int p_size = Integer.parseInt(size.getText());
            Iteam iteam = new Iteam(p_name, p_size);
            if (r1.isSelected()) {
                algorithm.FF(freeTable, busyTable, iteam);
            }else {
                algorithm.RFF(freeTable, busyTable, iteam);
            }
            tools.printTable(freeTable, busyTable);
            draw(pane1);
        });

        button1.setOnMouseClicked(event -> {
            String p_name = name1.getText();
            algorithm.recycle(freeTable, busyTable, p_name);
            tools.printTable(freeTable, busyTable);
            draw(pane1);
        });

        primaryStage.setTitle("内存空间动态分区分配与回收实验可视化程序");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void draw(Pane pane) {
        pane.getChildren().clear();
        pane.getChildren().add(rectangle);
        pane.getChildren().addAll(low, high);
        List<Iteam> busyList = busyTable.getBusyList();

        // 遍历空闲分区列表
        for (int i = 0; i < busyList.size(); i++) {
            Iteam a = busyList.get(i);
            int s = a.getStart() * 6;
            int e = s + a.getSize() * 6;
            Rectangle r = new Rectangle(80, s, 250, a.getSize() * 6);
            r.setFill(Color.GRAY);
            r.setStroke(Color.BLACK);
            Text t1 = new Text(85, s + 10, a.getStart() + "");
            Text t2 = new Text(85, e - 5, a.getStart() + a.getSize() + "");
            Text t3 = new Text(200, (s + e) / 2, a.getName());
            pane.getChildren().addAll(r, t1, t2, t3);
        }
    }
}
