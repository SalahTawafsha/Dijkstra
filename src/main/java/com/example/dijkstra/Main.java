package com.example.dijkstra;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class Main extends Application {
    private final Alert error = new Alert(Alert.AlertType.ERROR);
    private final static HashMap<Country, LinkedList<Node>> graph = new HashMap<>();
    static Scene scene;
    static BorderPane root;

    @Override
    public void start(Stage stage) {
        FileChooser file = new FileChooser();
        file.setInitialDirectory(new File("."));
        file.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("TXT files", ".txt"));
        file.setTitle("Select the file Of Countries.");
        Button select = new Button("Select the file of countries.");

        select.setMinSize(200, 50);

        select.setOnAction(e -> {
            try {
                File in = file.showOpenDialog(stage);
                Scanner scan;
                if (in != null)
                    scan = new Scanner(in);
                else
                    return;

                double countries = scan.nextDouble();
                double edges = scan.nextDouble();
                scan.nextLine();
                while (countries-- != 0) {
                    String[] tokens = scan.nextLine().split(",");
                    graph.put(new Country(tokens[0], Double.parseDouble(tokens[2]), Double.parseDouble(tokens[1])), new LinkedList<>());
                }

                while (edges-- != 0) {
                    String[] tokens = scan.nextLine().split(",");
                    Country c1 = get(new Country(tokens[0]), graph.keySet());
                    Country c2 = get(new Country(tokens[1]), graph.keySet());

                    if (c1 == null || c2 == null) {
                        System.out.println("you have error in edges");
                        return;
                    }
                    double cost = findCost(c1, c2);
                    graph.get(c1).add(new Node(c2, cost));
                }

                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("map.fxml"));
                stage.getScene().setRoot(fxmlLoader.load());
            } catch (IOException ex) {
                error.setContentText(ex.getMessage());
                error.show();
            }
        });

        select.setStyle("""
                -fx-background-color: pink;
                    -fx-background-radius: 40;
                    -fx-border-color: black;
                    -fx-border-radius: 40;""");
        BorderPane pane = new BorderPane(select);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(171, 225, 243), null, null)));
        Scene scene = new Scene(pane);
        Main.scene = scene;
        root = pane;
        stage.setMaximized(true);
        stage.getIcons().add(new Image("icon.png"));
        stage.setTitle("Map Application");
        stage.setScene(scene);

        stage.show();
    }

    public static HashMap<Country, LinkedList<Node>> getGraph() {
        return graph;
    }


    private static double findCost(Country c1, Country c2) {
        double a =
                Math.sin(deg2rad(c2.getY() - c1.getY()) / 2) * Math.sin(deg2rad(c2.getY() - c1.getY()) / 2) +
                        Math.cos(deg2rad(c1.getY())) * Math.cos(deg2rad(c2.getY())) *
                                Math.sin(deg2rad(c2.getX() - c1.getX()) / 2) * Math.sin(deg2rad(c2.getX() - c1.getX()) / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371 * c;  // Distance in km (R is Radius of the earth in km)
    }

    static double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }

    static Country get(Country sample, Set<Country> all) {
        for (Country one : all) {
            if (one.equals(sample)) {
                return one;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        launch();
    }
}