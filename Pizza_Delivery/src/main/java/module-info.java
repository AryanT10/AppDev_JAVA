module org.example.pizzaordering {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.pizzaordering to javafx.fxml;
    exports org.example.pizzaordering;
}