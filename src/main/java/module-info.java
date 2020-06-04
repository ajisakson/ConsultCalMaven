module austinisakson.consultcalmaven {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens austinisakson.consultcalmaven to javafx.fxml;
    exports austinisakson.consultcalmaven;
}