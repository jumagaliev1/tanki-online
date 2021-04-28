package tankgame;


import javafx.geometry.Pos;

public interface Player {
    void moveRight();
    void moveLeft();
    void moveUp();
    void moveDown();
    void setMap(Map m);
    Position getPosition();
}
