module dms.c482 {
    requires javafx.controls;
    requires javafx.fxml;


    opens dms.c482 to javafx.fxml;
    exports dms.c482;
    exports controller;
    opens controller to javafx.fxml;
    opens model to javafx.graphics, javafx.fxml, javafx.base;
}