module com.russel.komandoui {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.russel.komandoui to javafx.fxml;
    exports com.russel.komandoui;
}