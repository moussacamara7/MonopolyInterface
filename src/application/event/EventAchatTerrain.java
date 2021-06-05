package application.event;

import application.Monopoly;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EventAchatTerrain implements EventHandler<ActionEvent> {

    private final Monopoly monopoly;

    public EventAchatTerrain(Monopoly monopoly) {
        this.monopoly = monopoly;
    }

    @Override
    public void handle(ActionEvent e) {

        monopoly.DialogAction("Faut gérer cette affaire....", true);
    }

}

