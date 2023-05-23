package app.chessgame.Models.Events;

import app.chessgame.Models.Move;

public interface MoveMadeEventListener {
    void moveMade(Move move);
}
