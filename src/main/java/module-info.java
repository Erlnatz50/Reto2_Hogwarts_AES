module es.cryptowarts {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires jdk.compiler;
    requires java.desktop;
    requires javafx.graphics;

    opens es.cryptowarts.controladores to javafx.fxml;

    opens es.cryptowarts to javafx.fxml;
    exports es.cryptowarts;
}
