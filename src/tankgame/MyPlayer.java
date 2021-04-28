package tankgame;


import javafx.scene.layout.Pane;

public class MyPlayer extends Pane implements Player{
    Map m;
    char table[][] = m.map;


    @Override
    public void moveRight() {
        if(m.y+1<m.size && m.getValueAt(m.x,m.y+1)!='1' && m.y+1>=0 ){
            m.y++;
        }
    }

    @Override
    public void moveLeft() {
        if(m.y-1<m.size && m.getValueAt(m.x,m.y-1)!='1' && m.y-1>=0){
            m.y--;
        }
    }

    @Override
    public void moveUp() {
        if(m.x-1<m.size && m.getValueAt(m.x-1,m.y)!='1' && m.x-1>=0 ){
            m.x--;
        }
    }

    @Override
    public void moveDown() {
        if(m.x+1<m.size && m.getValueAt(m.x+1,m.y)!='1' && m.x+1>=0 ){
            m.x++;
        }
    }



    @Override
    public void setMap(Map m) {
        this.m=m;
    }

    @Override
    public Position getPosition() {
        return null;
    }

}
