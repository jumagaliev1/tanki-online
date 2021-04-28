package tankgame;

public class Position {
    int x;
    int y;
    Position(int y, int x){
        setX(x);
        setY(y);
    }
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public boolean equals(Position p){
        if(p.getX()==x && p.getY()==y){
            return true;
        }
        else
            return false;
    }
    public String toString(){
        String str = "(" + x + "," + y + ")";
        return str;
    }
}
