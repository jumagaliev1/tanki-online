package tankgame;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;


public class Tank2d extends Application {
    Button submit;
    Button exit;
    @Override
    public void start(Stage stage){
        Pane pane = new Pane();
        pane.setId("backgroundImage");
        submit = new Button("Start");
        exit = new Button("Quit");
        pane.getChildren().addAll(submit, exit);
        submit.setLayoutX(500);
        submit.setLayoutY(300);
        exit.setLayoutX(500);
        exit.setLayoutY(350);
        submit.setFont(Font.font(20));
        exit.setFont(Font.font(20));
        exit.setOnAction(e->{quit(stage);});
        Scene scene = new Scene(pane, 600, 400);
        submit.setOnAction(e->{game(stage,scene);});
        scene.getStylesheets().add("Project/format.css");
        stage.setScene(scene);
        stage.show();

    }
    public void game(Stage stage, Scene scene){
        GameScreen game = new GameScreen(stage, scene);
    }
    public void quit(Stage stage){
        stage.close();
    }
    public static void main(String []args){
        launch(args);
    }
}






class GameScreen {
    Pane pane;
    int score = 0;
    double speed = 1;
    Tank tank[] = new Tank[1];
    int ufoAmount = 30;
    ArrayList<UFO> ufo = new ArrayList();
    Timeline t = new Timeline();
    Timeline ti = new Timeline();
    boolean speedInc;
    Text label;
    private Stage stage;
    private Scene scene;

    public GameScreen(Stage stage, Scene scene) {
        this.scene = scene;
        this.stage = stage;
        pane = new Pane();
        label = new Text("0");
        pane.setStyle("-fx-background-color: BLACK");
        tank[0] = new Tank(pane);

        for (int i = 0; i < ufoAmount; i++) {

            int x = i % 5;
            int y = i % 7;
            ufo.add(new UFO(x, y, pane));
        }
        label.setLayoutX(550);
        label.setLayoutY(10);
        label.setFill(Color.LIGHTGRAY);
        pane.getChildren().addAll(label);
        scene = new Scene(pane, 600, 400);
        scene.setOnKeyPressed(e -> {
            tankMove(e);
        });
        //scene.setOnKeyPressed(e -> {tankShot(e);});
        EventHandler<ActionEvent> event = e -> {
            hit();
        };
        t = new Timeline(new KeyFrame(Duration.millis(1), event));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
        stage.setScene(scene);
        stage.show();
    }

    public void tankMove(KeyEvent e) {
        tank[0].movement(pane, e);
    }

    public void hit() {
        for (int i = 0; i < ufo.size(); i++) {
            if (ufo.size() == 0 || tank[0].HitTank(pane, ufo.get(i))) {
                for (int m = 0; m < ufo.size(); m++) {
                    ufo.remove(m);
                    EndScreen end = new EndScreen(score, scene, stage);
                }
            }
        }
        for (int i = 0; i < ufo.size(); i++) {
            speedInc = tank[0].Hit(pane, ufo.get(i));
            if (speedInc == true) {
                score += 100 * tank[0].getIncre();
                label.setText(String.valueOf(score));
                speed *= 1.05;
                ufo.remove(i);
                for (int l = 0; l < ufo.size(); l++) {
                    ufo.get(l).t.setRate(speed);
                }
            }
        }
    }

    class Tank {

        private Image img, img2;
        private ImageView iv, iv2;
        private Rectangle2D r2d, bullet;
        private boolean fire = false;
        private Timeline t;
        int incre = 1;

        public Tank(Pane pane) {
            //img = new Image(Tank.class.getResourceAsStream("../Project/Pictures/Tank.png"));
            img = new Image(Tank.class.getResourceAsStream("images/green-tank-up.gif"));
            iv = new ImageView(img);

            iv.setLayoutX(250);
            iv.setLayoutY(350);
            pane.getChildren().addAll(iv);
        }

        public int getIncre() {
            return incre;
        }

        public void movement(Pane pane, KeyEvent e) {

            switch (e.getCode()) {
                case LEFT:

                    if (iv.getLayoutX() > 25) {
                        iv.setLayoutX(iv.getLayoutX() - 20);
                    }
                    break;
                case RIGHT:
                    if (iv.getTranslateX() < 550) {
                        iv.setLayoutX(iv.getLayoutX() + 20);
                    }
                    break;
                case SPACE:
                    shoot(pane);
                    break;
            }
        }

        public void shoot(Pane pane) {

            if (fire == false) {
                fire = true;
                Timeline time = new Timeline();
                img2 = new Image(Tank.class.getResourceAsStream("images/missile-up.gif"));
                iv2 = new ImageView(img2);
                double y = iv.getLayoutY();
                iv2.relocate(iv.getLayoutX(), y);
                pane.getChildren().add(iv2);
                EventHandler<ActionEvent> event = e -> {
                    bullet(iv2, pane);
                };
                t = new Timeline(new KeyFrame(Duration.millis(4), event));
                t.setCycleCount(Timeline.INDEFINITE);
                t.play();
            }
        }

        public Bounds getBulletBounds() {
            return iv2.getLayoutBounds();
        }

        public boolean getFire() {
            return fire;
        }

        public void removeBullet(Pane pane) {
            pane.getChildren().remove(iv2);
        }

        public boolean Hit(Pane pane, UFO ufo) {
            if (fire == true) {
                if (iv2.getBoundsInParent().intersects(ufo.UFOBounds())) {
                    pane.getChildren().remove(iv2);
                    ufo.iv.setImage(null);
                    iv2.setImage(null);
                    fire = false;
                    ufo.t.stop();
                    t.stop();
                    pane.getChildren().remove(ufo.iv);
                    incre++;
                    return true;

                }
            }
            return false;
        }

        public boolean HitTank(Pane pane, UFO ufo) {
            if (iv.getBoundsInParent().intersects(ufo.UFOBounds())) {
                ufo.t.stop();
                return true;

            }
            return false;
        }

        public void bullet(ImageView iv2, Pane pane) {
            iv2.setLayoutY(iv2.getLayoutY() - 1);
            if (iv2.getLayoutY() < -50) {
                incre = 1;
                pane.getChildren().remove(iv2);
                fire = false;
                t.stop();
            }
        }
    }


    class UFO {

        Image img;
        ImageView iv;
        Rectangle2D r2d, bullet;
        double x, y;
        double dx = 1;
        double dy = 10;
        double speed = 1;
        int count = 0;
        double time;
        Timeline t;

        public UFO(int x, int y, Pane pane) {
            this.x = x;
            this.y = y;
            PathTransition pt = new PathTransition();
            this.img = new Image(UFO.class.getResourceAsStream("images/red-tank-up.gif"));
            this.iv = new ImageView(this.img);
            this.x = 75 * (x + 1);
            this.y = y * 15 + 10;
            iv.setLayoutX(this.x);
            iv.setLayoutY(this.y);
            EventHandler<ActionEvent> event = e -> {
                moveUFO(pane);
            };
            t = new Timeline(new KeyFrame(Duration.millis(15), event));
            t.setCycleCount(Timeline.INDEFINITE);
            t.play();
            pane.getChildren().addAll(iv);
        }

        public ImageView image() {
            return iv;
        }

        public void moveUFO(Pane pane) {
            iv.setLayoutX(iv.getLayoutX() + dx);
            if (iv.getLayoutX() < 25 || iv.getLayoutX() > 550) {
                dx *= -1;
                iv.setLayoutY(iv.getLayoutY() + dy);
            }
        }

        public Bounds UFOBounds() {
            return iv.getBoundsInParent();
        }


        public void animate() {
            count++;
            if (count == 0) {
                this.r2d = new Rectangle2D(36, 218, 25, 28);
            } else if (count == 1) {
                this.r2d = new Rectangle2D(72, 218, 25, 28);
            } else if (count == 2) {
                this.r2d = new Rectangle2D(178, 218, 25, 28);
            }
            iv.setViewport(r2d);
        }
    }

    class EndScreen {
        Pane pane;
        Button tryAgain, exit;
        Stage stage;
        Scene scene;

        public EndScreen(int score, Scene scene, Stage stage) {
            this.scene = scene;
            this.stage = stage;
            pane = new Pane();
            pane.setId("backgroundImage");
            Text text = new Text("Score: " + String.valueOf(score));
            text.setFont(Font.font("Bradley Hand ITC", FontWeight.THIN, 50));
            text.setFill(Color.LIGHTGRAY);
            text.setLayoutX(200);
            text.setLayoutY(150);
            tryAgain = new Button("Try Again");
            exit = new Button("Quit");
            tryAgain.setLayoutX(500);
            tryAgain.setLayoutY(300);
            exit.setLayoutX(500);
            exit.setLayoutY(350);
            tryAgain.setFont(Font.font(20));
            exit.setFont(Font.font(20));
            exit.setOnAction(e -> {
                quit(stage);
            });
            pane.getChildren().addAll(text, tryAgain, exit);
            scene = new Scene(pane, 600, 400);
            tryAgain.setOnAction(e -> {
                game();
            });
            scene.getStylesheets().add("Project/format.css");
            stage.setScene(scene);
            stage.show();

        }

        public void game() {
            GameScreen game = new GameScreen(stage, scene);
        }

        public void quit(Stage stage) {
            stage.close();
        }
    }
}
