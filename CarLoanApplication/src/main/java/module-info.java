module org.example.carloanapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.carloanapplication to javafx.fxml;
    exports org.example.carloanapplication;
}