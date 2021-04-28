package tankgame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class Bot extends Tank{
    public int x;
    public int y;
    Map m;
    private ImageView imgV;
    Image img;
    String pos;
    int health =2;
    char[][] table =     {{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
            {'0', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', '0'},
            {'0', 's', '0', '0', '0', '0', '0', '0', '0', '0', '0', 's', '0'},
            {'0', 's', '0', '0', '0', '0', '0', '0', '0', '0', '0', 's', '0'},
            {'0', 's', '0', '0', '0', '0', '0', '0', '0', '0', '0', 's', '0'},
            {'w', 's', '0', '0', '0', '0', '0', '0', '0', '0', '0', 's', '0'},
            {'0', 's', 's', 's', 's', '0', '0', 's', 's', 's', 's', 's', '0'},
            {'0', 'w', '0', '0', 'v', 'w', '0', '0', 'w', 's', '0', '0', '0'},
            {'0', '0', '0', '0', '0', 'w', '0', '0', 'v', 'v', '0', 'v', '0'},
            {'k', 's', '0', 's', '0', 'k', '0', '0', '0', 's', '0', 'w', '0'},
            {'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', 'v', '0', '0'},
            {'w', '0', 'w', '0', '0', 'k', 'k', '0', 'k', 'k', '0', '0', '0'},
            {'0', '0', '0', '0', '0', 'k', 'k', '0', 'k', '0', 'k', '0', '0'}};
    Timeline animation;
    int maxNumOfbots;
    Map map;
    Pane botPane;
    Player player;
    int size;
    Position botPosition;
    Label life = new Label("5");
    Bot(final Map map, final Player player){
        this.maxNumOfbots = 10;
        this.map = map;
        this.botPane = new Pane();
        this.map.getChildren().add((Node) this.botPane);
        this.player = player;
        this.size = this.map.getSize();
        new Thread(() -> {
            while (this.maxNumOfbots > 0) {
                this.createFood();
                Platform.runLater(() -> this.botPane.getChildren().addAll(new Node[] { (Node)this.imgV }));
                //this.time = 50;
                while (this.health > 0) {
                    Platform.runLater(() -> this.life.setText("" + this.health));
                    if (this.player.getPosition().equals(this.botPosition)) {
                        //this.points += this.time/10;
                        break;
                    }
                    else {
                        try {
                            Thread.sleep(100L);
                        }
                        catch (InterruptedException ex) {}
                        --this.health;
                    }
                }
                try {
                    Thread.sleep(10L);
                }
                catch (InterruptedException ex2) {}
                Platform.runLater(() -> this.botPane.getChildren().clear());
                --this.maxNumOfbots;
            }
            //System.out.println(this.getPoints());
            System.out.println("the end");
        }).start();
    }

    private void createFood() {
        final Random random = new Random();
        //final double n = this.map.getUnit();
        final double n = 40;
        Position botPosition;
        int y;
        int x;
        do {
            x = random.nextInt(this.size);
            y = random.nextInt(this.size);
            botPosition = new Position(x, y);
        } while (this.player.getPosition().equals(botPosition) || this.map.getValue(y, x) == 's');
        //(this.circle = new Circle(x * n + n / 2.0, y * n + n / 2.0, n / 4.0)).setFill((Paint) Color.GREEN);
        //getChildren().clear();
        imgV = new ImageView(img);
        imgV.setX(x*40);
        imgV.setY(y*40);
        this.botPosition = botPosition;
        //(this.seconds = new Label("5")).setTranslateX(x * n);
        //this.seconds.setTranslateY(y * n);
    }
    Bot(Map map, int x, int y){
        this.x= x;
        this.y = y;
        img = new Image("images/red-tank-right.gif");
        m = map;

        drawTank();

        animation = new Timeline(
                new KeyFrame(Duration.millis(1800),
                        e -> update()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();




    }
    void update(){
        drawTank();
        changeRandomDirection();
        if(health==0){
            animation.stop();
            imgV.setVisible(false);
            getChildren().clear();
        }
    }
    void setPos(String pos){
        this.pos = pos;
    }
    public void drawTank() {
        getChildren().clear();

        imgV = new ImageView(img);
        imgV.setX(x*40);
        imgV.setY(y*40);
        bulX = x*40;
        bulY = y*40;
        getChildren().addAll(imgV);
    }
    private void changeRandomDirection() {
        int dir = (int)(Math.random() * 4);
        switch (dir) {
            case 1:
                if(pos=="RIGHT")
                    moveRight();
                setPos("RIGHT");
                img = new Image("images/red-tank-right.gif");
                break;
            case 2:
                if(pos=="LEFT")
                    moveLeft();
                setPos("LEFT");
                img = new Image("images/red-tank-left.gif");
                break;
            case 3:
                if(pos=="UP")
                    moveUp();
                setPos("UP");
                img = new Image("images/red-tank-up.gif");

                break;
            case 4:
                if(pos=="DOWN")
                    moveDown();
                setPos("DOWN");
                img = new Image("images/red-tank-down.gif");
                break;
            default:
                break;
        }
    }
    private void moveRandom(){
        int move = (int) (Math.random()*4);
        switch (move) {
            case 0:
                moveUp();
                System.out.println("UP");
                break;
            case 1:
                moveRight();
                System.out.println("RIGHT");
                break;
            case 2:
                moveLeft();
                System.out.println("LEFT");
                break;
            case 3:
                moveDown();
                System.out.println("DOWN");
                break;
            default:
                break;
        }

    }
    public void moveRight() {
        if(x+1<13 && (table[y][x+1]=='0' || table[y][x+1]=='w') && x+1>=0 ){

            x++;
        }
    }

    public void moveLeft() {
        if(x-1<13 && (table[y][x-1]=='0' || table[y][x-1]=='w') && x-1>=0){

            x--;
        }
    }

    public void moveUp() {
        if(y-1<13 && (table[y-1][x]=='0' || table[y-1][x]=='w') && y-1>=0 ){

            y--;

        }
    }

    public void moveDown() {
        if(y+1<13 && (table[y+1][x]=='0' || table[y+1][x]=='w') && y+1>=0 ){

            y++;
        }
    }



}
