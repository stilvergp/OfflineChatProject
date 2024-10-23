module com.github.stilvergp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires java.desktop;
    requires jdk.compiler;
    requires jdk.jfr;

    opens com.github.stilvergp to javafx.fxml;
    opens com.github.stilvergp.model.manager to java.xml.bind;
    opens com.github.stilvergp.model.entity to java.xml.bind;

    exports com.github.stilvergp;
    exports com.github.stilvergp.view;
    exports com.github.stilvergp.model.entity;
    opens com.github.stilvergp.view to javafx.fxml;
    exports com.github.stilvergp.controller;
    opens com.github.stilvergp.controller to javafx.fxml;
    exports com.github.stilvergp.utils;
    opens com.github.stilvergp.utils to javafx.fxml;
}
