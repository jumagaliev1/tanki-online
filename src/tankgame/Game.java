package tankgame;

import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.Scanner;

public class Game extends Application {
    Scene gameScene;
    Map map;
    StackPane gamePane;
    Canvas canvas;
    Tank tank;
    Bot vrag;
    Bot vrag1;
    Button btnStart;
    Pane pane;
    Text welText;


    Bullet bullet;
    private void handleInputEn (KeyCode code)  {

        switch (code) {
            case ENTER:
                bullet = new Bullet(tank.bulX, tank.bulY, tank.posTank,map);
                gamePane.getChildren().add(0, bullet);
                break;
            case D:
                if(tank.posTank=="RIGHT")
                    vrag.moveRight();
                tank.setPosition("RIGHT");
                tank.img = new Image("images/green-tank-right.gif");
                tank.bulImg = new Image("images/missile-right.gif");

                break;
            case A:
                if(tank.posTank=="LEFT")
                    vrag.moveLeft();
                tank.setPosition("LEFT");
                tank.img = new Image("images/green-tank-left.gif");
                tank.bulImg = new Image("images/missile-left.gif");

                break;
            case W:
                if(tank.posTank=="UP")
                    vrag.moveUp();
                tank.setPosition("UP");
                tank.img = new Image("images/green-tank-up.gif");
                tank.bulImg = new Image("images/missile-up.gif");
                break;
            case S:
                if(tank.posTank=="DOWN")
                    vrag.moveDown();
                tank.setPosition("DOWN");
                tank.img = new Image("images/green-tank-down.gif");
                tank.bulImg = new Image("images/missile-down.gif");
                break;
            default:
                break;

        }
    }


    public static void main(String[] args) {
        launch(args);
    }
    public void init(){
        map = new Map(); // map for game
        gamePane = new StackPane();
        tank = new Tank(map);
        vrag = new Bot(map,5,5);
        vrag1 = new Bot(map, 7, 5);

    }
    void initStartScene(){

        btnStart = new Button();
        Image startImg = new Image("images/btnStart.jpg");
        ImageView imgSv = new ImageView(startImg);
        imgSv.setFitHeight(90);
        imgSv.setFitWidth(340);
        btnStart.setGraphic(imgSv);

        btnStart.setPrefSize(340,90);
        btnStart.setLayoutX(85);
        btnStart.setLayoutY(185);


        pane = new Pane();
        pane.setStyle("-fx-background-color: green");
        btnStart.setStyle("-fx-background-color: yellow");
        btnStart.setAlignment(Pos.CENTER);

        welText = new Text("World of Tanks");
        welText.setLayoutX(200);
        welText.setLayoutY(300);
        pane.getChildren().addAll(btnStart,welText);
        Scene startScene = new Scene(pane,520,520);
    }

    @Override
    public void start(Stage stage) throws Exception {
        File map_file = new File("src/maps/map1.txt");
        Scanner input = new Scanner(map_file);

        init(); //Inititalization all nodes

        setStylePane(); //set all styles for game Pane(Stackpane)

        initStartScene(); //initalaization start scene

        gamePane.getChildren().addAll(vrag,vrag1);


        gamePane.getChildren().addAll(tank,map.mapCanvas);
        gameScene = new Scene(gamePane,520,520);
        btnStart.setOnMousePressed(e -> stage.setScene(gameScene));
        welText.setOnMousePressed(e -> stage.setScene(gameScene));

        stage.setTitle("Tank game for SDU");
        stage.setScene(gameScene);
        stage.show();


        //vrag.setOnKeyPressed(e -> handleInputEn(e.getCode()));

        tank.requestFocus();
        tank.setOnKeyPressed(e -> handleKeyInput(e.getCode()));


    }
    void setStylePane(){
        gamePane.setStyle("-fx-background-color: black");
    }
    private void handleKeyInput(KeyCode code)  {

        switch (code) {
            case SPACE:

                switch (tank.posTank){
                    case "RIGHT":
//                        if((tank.y == vrag.y && tank.x<vrag.x) || (tank.y == vrag1.y && tank.x<vrag1.x)){
//                            bullet = new Bullet(tank.x*40,tank.y*40, tank.posTank, map, vrag.x*40,vrag.y*40);
//                            bullet = new Bullet(tank.x*40,tank.y*40, tank.posTank, map, vrag1.x*40,vrag1.y*40);
//                            vrag.health--;
//                            vrag1.health--;
//                            System.out.println(vrag.health);
//                        }
//                        else{
//                            bullet = new Bullet(tank.bulX, tank.bulY, tank.posTank,map);
//                        }
                        bullet = new Bullet(tank.bulX, tank.bulY, tank.posTank,map);
                }
//                if((bullet.lastX/40 < vrag.x && tank.bulY/40 == vrag.y) || (bullet.lastY/40 < vrag.y && tank.bulX/40== vrag.x)){
//
//                    //stack.getChildren().remove(vrag);
//
//                }
                bullet = new Bullet(tank.bulX, tank.bulY, tank.posTank,map);

                gamePane.getChildren().add(0, bullet);
                break;
            case RIGHT:
                if(tank.posTank=="RIGHT")
                    tank.moveRight();
                tank.setPosition("RIGHT");
                tank.img = new Image("images/green-tank-right.gif");
                tank.bulImg = new Image("images/missile-right.gif");

                break;
            case LEFT:
                if(tank.posTank=="LEFT")
                    tank.moveLeft();
                tank.setPosition("LEFT");
                tank.img = new Image("images/green-tank-left.gif");
                tank.bulImg = new Image("images/missile-left.gif");

                break;
            case UP:
                if(tank.posTank=="UP")
                    tank.moveUp();
                tank.setPosition("UP");
                tank.img = new Image("images/green-tank-up.gif");
                tank.bulImg = new Image("images/missile-up.gif");
                break;
            case DOWN:
                if(tank.posTank=="DOWN")
                    tank.moveDown();
                tank.setPosition("DOWN");
                tank.img = new Image("images/green-tank-down.gif");
                tank.bulImg = new Image("images/missile-down.gif");
                break;
            default:
                break;

        }
    }
    void addPlayer(Player player){
        gamePane.getChildren().add((Node) player);
    }

}
