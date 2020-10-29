module austinisakson.consultcalmaven {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.sql;
    requires java.base;

    opens austinisakson.consultcalmaven to javafx.fxml;
    exports austinisakson.consultcalmaven;

}