package com.example.dijkstra;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

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

    private final Alert error = new Alert(Alert.AlertType.ERROR);
    ArrayList<String> items = new ArrayList<>();


    private final Table[] table = new Table[Main.getGraph().size()];

    @FXML
    void calculate() {
        if (source.getValue() != null && target.getValue() != null) {
            dijkstra(get(new Country(source.getValue())));

            System.out.println("done");
            String s = target.getValue();

            int i = indexOf(new Country(target.getValue()));
            double distance = table[i].getDistance();
            StringBuilder path = new StringBuilder(table[i].getHeader().getName());
            while (!s.equals(source.getValue())) {
                i = table[i].getPrev();
                path.insert(0, table[i].getHeader().getName() + "\n⬇\n");
                s = table[i].getHeader().getName();
            }
            this.path.setText(path.toString());
            this.distance.setText(distance + "");
        } else {
            error.setContentText("You must fill source and target before calculate");
            error.show();
        }
    }

    @FXML
    void back() {
        Main.main(new String[0]);
    }

    public void dijkstra(Country from) {
        HashMap<Country, LinkedList<Node>> graph = Main.getGraph();

        fillTable(table, graph.keySet(), from);
        Country v;

        while (true) {
            int index = smallest(table);
            v = (index != -1) ? table[index].getHeader() : null;

            if (v == null)
                break;
            table[index].setKnown(true);

            LinkedList<Node> list = graph.get(v);
            for (Node node : list) {
                int j = find(node, table);
                if (table[j].notKnown()) {
                    if (table[index].getDistance() + node.cost() < table[j].getDistance()) {
                        table[j].setDistance(table[index].getDistance() + node.cost());
                        table[j].setPrev(indexOf(v));
                    }
                }
            }

        }

    }

    private int indexOf(Country v) {
        for (int i = 0; i < table.length; i++)
            if (table[i].getHeader().getName().equals(v.getName()))
                return i;

        return -1;
    }

    @FXML
    void fillTarget() {
        target.setItems(FXCollections.observableArrayList(items));
        target.getItems().remove(source.getValue());
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

    private static void fillTable(Table[] table, Set<Country> all, Country from) {
        int i = 0;
        for (Country one : all) {
            table[i++] = new Table(one);
            if (from.equals(one))
                table[i - 1].setDistance(0);
        }
    }


    private static int smallest(Table[] graph) {
        double d = Double.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < graph.length; i++) {
            if (graph[i].notKnown() && graph[i].getDistance() < d) {
                d = graph[i].getDistance();
                index = i;
            }
        }
        return index;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (Country one : Main.getGraph().keySet())
            items.add(one.getName());

        Collections.sort(items);

        source.setItems(FXCollections.observableArrayList(items));

//        b.setLayoutX((x + 180.0) / 360 * 1248 - 50);
//        b.setLayoutY(921 - ((y + 90.0) / 180 * 889 + 37));

    }
}
