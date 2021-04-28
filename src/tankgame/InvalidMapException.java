package tankgame;

public class InvalidMapException  extends Exception{
    InvalidMapException(){
        super();
    }
    InvalidMapException(String message){
        super(message);
    }
}
