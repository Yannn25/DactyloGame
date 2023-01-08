/**
 * Les
 */
module com.game.dactylogame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.fxmisc.richtext;
    requires com.almasb.fxgl.all;


    opens com.game.dactylogame.VueFX to javafx.fxml;
    exports com.game.dactylogame.Modele;
    exports com.game.dactylogame.VueFX;
}