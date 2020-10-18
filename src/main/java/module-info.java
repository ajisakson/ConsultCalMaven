module austinisakson.consultcalmaven {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens austinisakson.consultcalmaven to javafx.fxml;
    exports austinisakson.consultcalmaven;
}