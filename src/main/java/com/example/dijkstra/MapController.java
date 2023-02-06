package com.example.dijkstra;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.*;

public class MapController implements Initializable {
    @FXML
    private TextField distance;

    @FXML
    private TextArea path;
    @FXML
    private Pane pane;

    @FXML
    private ComboBox<String> source;

    @FXML
    private ComboBox<String> target;

    @FXML
    private Pane lines;
    private String currSource;

    private final Table[] table = new Table[Main.getGraph().size()];

    private boolean isDone = false;

    private final Alert error = new Alert(Alert.AlertType.ERROR);

    private PriorityQueue<Table> queue = new PriorityQueue<>();

    @FXML
    void calculate() {
        if (!isDone) {
            error.setContentText("Please wait till dijkstra end");
            error.show();
            target.setValue(null);
            return;
        }
        lines.getChildren().clear();
        if (source.getValue() != null && target.getValue() != null && !source.getValue().isEmpty() && !target.getValue().isEmpty()) {

            String s = target.getValue();

            int i = indexOf(new Country(target.getValue()));
            double distance = table[i].getDistance();
            StringBuilder path = new StringBuilder(table[i].getHeader().getName());
            int m = 0;
            while (!s.equals(source.getValue()) && m++ < table.length) {
                int j = i;
                i = table[i].getPrev();
                path.insert(0, table[i].getHeader().getName() + "\n⬇\n");
                s = table[i].getHeader().getName();
                Line l = new Line((table[j].getHeader().getX() + 180.0) / 360 * 1248 - 30, 830 - ((table[j].getHeader().getY() + 90.0) / 180 * 750)
                        , (table[i].getHeader().getX() + 180.0) / 360 * 1248 - 30, 830 - ((table[i].getHeader().getY() + 90.0) / 180 * 750));
                l.setStrokeWidth(2);

                lines.getChildren().add(l);
            }
            this.path.setText(path.toString());
            this.distance.setText(distance + "");
            if (m > table.length) {
                error.setContentText("can't reach, please try again");
                error.show();
                clear();
            }
        } else {
            path.clear();
            distance.clear();
        }

    }

    @FXML
    void back() {
        Main.getGraph().clear();
        Main.scene.setRoot(Main.root);
    }

    public void dijkstra(Country from) {
        System.out.println(from);
        isDone = false;
        HashMap<Country, LinkedList<Node>> graph = Main.getGraph();

        fillTable(graph.keySet(), from);

        while (!queue.isEmpty()) {
            Table t = queue.remove();

            t.setKnown(true);

            LinkedList<Node> list = graph.get(t.getHeader());
            for (Node node : list) {
                int j = find(node, table);
                if (table[j].notKnown()) {
                    if (t.getDistance() + node.cost() < table[j].getDistance()) {
                        table[j].setDistance(t.getDistance() + node.cost());
                        table[j].setPrev(indexOf(t.getHeader()));
                        queue.add(table[j]);
                    }
                }
            }

        }

        isDone = true;


    }

    private int indexOf(Country v) {
        for (int i = 0; i < table.length; i++)
            if (table[i].getHeader().getName().equals(v.getName()))
                return i;

        return -1;
    }

    @FXML
    void fillTarget() {

        lines.getChildren().clear();
        if (source.getValue() != null && !source.getValue().equals(currSource))
            dijkstra(get(new Country(source.getValue())));
        currSource = source.getValue();
    }


    static Country get(Country sample) {
        Set<Country> all = Main.getGraph().keySet();
        for (Country one : all) {
            if (one.equals(sample)) {
                return one;
            }
        }
        return null;
    }

    private static int find(Node node, Table[] table) {
        for (int i = 0; i < table.length; i++) {
            if (table[i].getHeader().equals(node.country()))
                return i;
        }
        return 0;
    }


    private void fillTable(Set<Country> all, Country from) {
        int i = 0;
        for (Country one : all) {
            table[i++] = new Table(one);
            if (from.equals(one)) {
                table[i - 1].setDistance(0);
                queue.add(table[i - 1]);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> items = new ArrayList<>();

        for (Country one : Main.getGraph().keySet()) {
            Button b = new Button(one.getName());
            b.setStyle("-fx-font-size: 5; -fx-max-height: 10;-fx-max-width: 10");
            b.setLayoutX((one.getX() + 180.0) / 360 * 1248 - 40);
            b.setLayoutY(820 - (one.getY() + 90.0) / 180 * 750);
            b.hoverProperty().addListener(e -> {
                if (b.isHover()) {
                    Label l = new Label(b.getText());
                    l.setLayoutY(b.getLayoutY() - 10);
                    l.setLayoutX(b.getLayoutX());
                    l.setPadding(new Insets(2.5));
                    l.setStyle(
                            "-fx-background-color: pink;-fx-background-radius: 40;-fx-border-color: black;\n" +
                                    "-fx-border-radius: 40; -fx-alignment: center;-fx-font-size: 12;");


                    l.hoverProperty().addListener(e1 -> {
                        if (!l.isHover())
                            pane.getChildren().remove(l);
                    });

                    l.setOnMouseClicked(select(l));

                    pane.getChildren().add(l);

                }
            });

            b.setOnMouseClicked(select(b));

            pane.getChildren().add(b);
            items.add(one.getName());
        }

        Collections.sort(items);

        source.setItems(FXCollections.observableArrayList(items));
        target.setItems(FXCollections.observableArrayList(items));

    }

    private EventHandler<? super MouseEvent> select(Labeled l) {
        return (EventHandler<MouseEvent>) event -> {
            if (l.isHover())
                if (source.getValue() == null) {
                    source.setValue(l.getText());
                } else if (source.getValue() != null && target.getValue() != null) {
                    source.setValue(l.getText());
                    target.setValue(null);

                } else {
                    target.setValue(l.getText());
                }
        };
    }

    public void clear() {
        target.setValue(null);
        source.setValue(null);
        path.clear();
        distance.clear();
        lines.getChildren().clear();
    }
}
