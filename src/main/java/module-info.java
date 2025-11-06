module es.erlantzg {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires jdk.compiler;
    requires java.desktop;

    opens es.erlantzg.controladores to javafx.fxml;
    opens es.erlantzg.modelos to javafx.base;

    opens es.erlantzg to javafx.fxml;
    exports es.erlantzg;
}
