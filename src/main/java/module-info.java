module com.example.dijkstra {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dijkstra to javafx.fxml;
    exports com.example.dijkstra;
}