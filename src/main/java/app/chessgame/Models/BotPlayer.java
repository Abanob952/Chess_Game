package app.chessgame.Models;

import app.chessgame.Models.Events.TurnChangeListener;
import javafx.scene.paint.Color;

public class BotPlayer extends Player{
    private final Engine engine;
    Match match;
    public BotPlayer(Color color, Match match) {
        super("Bot", color);
        this.engine = new Engine(match);
        this.match = match;
    }

    @Override
    public void getPlay() {
        if(this.match.getTurn().getColor() == this.getColor()){
            var res = this.engine.generateBestMove(3);
            if(res == null)
                return; // check mate
            this.match.play(res
                    .getSource(), res.getTarget());

        }
    }

}
