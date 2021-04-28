package tankgame;

import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Bullet extends Pane {



    int durationTime;

    int lastX;
    int lastY;
    PathTransition pathTransition;
    int health=2;

    ImageView images;


    Bullet(int posTanX, int posTanY, String pos, Map map){


        pathTransition = new PathTransition();
        pathTransition.setRate(0.5);
        ScaleTransition scaleTransition = new ScaleTransition();
        Image exp = new Image("images/explosion1.gif");
        ImageView expV = new ImageView(exp);
        expV.setVisible(false);


        switch (pos) {
            case "UP" -> {
                images = new ImageView("images/missile-up.gif");
                for (int i = posTanY / 40; i > 0; i--) {
                    char ch = map.map[i][posTanX / 40];
                    if (ch == 's' || ch == 'k' || ch == 'h' || ch== 'j' || ch == 'i' || ch== 'g') {
                        lastY = (i * 40);
                        break;
                    } else {
                        lastY = -45;
                    }
                }
                expV.setX(posTanX + 15);
                expV.setY(lastY + 45);
                if((lastY - posTanY)<0){
                    durationTime = 0-((lastY - posTanY) * 5);
                }
                else{
                    durationTime = ((lastY - posTanY) * 5);
                }
                pathTransition.setDuration(Duration.millis(durationTime));
                pathTransition.setNode(images);
                pathTransition.setPath(new Line(posTanX + 20, posTanY + 20, posTanX + 20, lastY + 35));
            }
            case "DOWN" -> {
                images = new ImageView("images/missile-down.gif");
                for (int i = posTanY / 40; i < map.map.length; i++) {
                    char ch = map.map[i][posTanX / 40];
                    if (ch == 's' || ch == 'k' || ch == 'h' || ch == 'j' || ch == 'i' || ch == 'g') {
                        lastY = (i * 40);
                        break;
                    } else {
                        lastY = 530;

                    }
                }
                expV.setX(posTanX + 15);
                expV.setY(lastY - 20);
                if((lastY - posTanY)<0){
                    durationTime = 0-((lastY - posTanY) * 5);
                }
                else{
                    durationTime = ((lastY - posTanY) * 5);
                }
                pathTransition.setDuration(Duration.millis(durationTime));
                pathTransition.setNode(images);
                pathTransition.setPath(new Line(posTanX + 20, posTanY + 20, posTanX + 20, lastY + 5));
            }
            case "LEFT" -> {
                images = new ImageView("images/missile-left.gif");
                for (int i = posTanX / 40; i >= 0; i--) {
                    char ch = map.map[posTanY / 40][i];
                    if (ch == 's' || ch== 'k' || ch== 'h' || ch == 'j' || ch == 'i' || ch== 'g') {
                        lastX = (i * 40);
                        break;
                    } else {
                        lastX = -5;
                    }
                }
                expV.setX(lastX + 40);
                expV.setY(posTanY + 15);
                if((lastX - posTanX)<0){
                    durationTime = 0-((lastX - posTanX) * 5);
                }
                else{
                    durationTime = ((lastX - posTanX) * 5);
                }
                pathTransition.setDuration(Duration.millis(durationTime));
                pathTransition.setNode(images);
                pathTransition.setPath(new Line(posTanX + 20, posTanY + 20, lastX + 15, posTanY + 20));
            }
            case "RIGHT" -> {
                images = new ImageView("images/missile-right.gif");
                pathTransition.setNode(images);

                for (int i = posTanX / 40; i < map.map.length; i++) {
                    char ch = map.map[posTanY / 40][i];
                    if (ch == 's' || ch== 'k' || ch == 'h' || ch == 'j' || ch == 'i' || ch == 'g') {
                        lastX = (i * 40);
                        break;
                    } else {
                        lastX = 525;
                    }
                }
                expV.setX(lastX - 18);
                expV.setY(posTanY + 20);
                if((lastX - posTanX)<0){
                    durationTime = 0-((lastX - posTanX) * 5);
                }
                else{
                    durationTime = ((lastX - posTanX) * 5);
                }
                pathTransition.setDuration(Duration.millis(durationTime));
                pathTransition.setPath(new Line(posTanX + 20, posTanY + 20, lastX + 20, posTanY + 20));
            }
        }

        pathTransition.play();

        pathTransition.setCycleCount(1);


        pathTransition.setOnFinished(e -> {
            images.setVisible(false);
            expV.setVisible(true);
            scaleTransition.setNode(expV);

            scaleTransition.setDuration(Duration.millis(100));
            scaleTransition.setByX(2);
            //Set how much Y should
            scaleTransition.setByY(2);
            scaleTransition.play();
            scaleTransition.setOnFinished(event -> expV.setVisible(false) );
            /*if(map.map[lastY /40][posTanX /40]=='k' || map.map[lastY /40][posTanX /40]== 'h' || map.map[lastY /40][posTanX /40]== 'j' || map.map[lastY /40][posTanX /40]== 'i' || map.map[lastY /40][posTanX /40]== 'g' ){
                int val = map.map[lastY /40][posTanX /40];
                if(val == 104){
                    map.map[lastY /40][posTanX /40]=  '0';
                    map.graphicsContext.clearRect(posTanX, lastY, 40, 40);
                }
                else {
                    map.map[lastY /40][posTanX /40]= (char) (map.map[lastY /40][posTanX /40]-1);
                }
            }
           else if( map.map[posTanY /40][lastX /40]=='k' || map.map[posTanY /40][lastX /40]== 'h' || map.map[posTanY /40][lastX /40]== 'j' || map.map[posTanY /40][lastX /40]== 'i' || map.map[posTanY /40][lastX /40]== 'g'){

                int val = map.map[posTanY /40][lastX /40];
                if(val == 104){
                    map.map[posTanY /40][lastX /40]='0';
                    map.graphicsContext.clearRect(lastX, posTanY, 40, 40);
                }
                else {
                    map.map[posTanY /40][lastX /40]= (char) (map.map[posTanY /40][lastX /40]-1);
                }

            }*/

        });


        getChildren().addAll(images,expV);

    }
    Bullet(int posTanX, int posTanY, String pos, Map map, int posBotX, int posBotY){

        pathTransition = new PathTransition();
        pathTransition.setRate(0.5);
        ScaleTransition scaleTransition = new ScaleTransition();
        Image exp = new Image("images/explosion1.gif");
        ImageView expV = new ImageView(exp);
        expV.setVisible(false);
        //posBotX = posBotX*40;
        //posBotY = posBotY*40;
        //posTanY = posTanY*40;
        //posTanX = posTanX*40;



        switch (pos) {
            case "UP" -> {
                images = new ImageView("images/missile-up.gif");
                for (int i = posTanY / 40; i > 0; i--) {
                    char ch = map.map[i][posTanX / 40];
                    if (ch == 's' || ch == 'k' || ch == 'h' || ch== 'j' || ch == 'i' || ch== 'g') {
                        lastY = (i * 40);
                        break;
                    } else {
                        lastY = posBotY;
                    }
                }
                expV.setX(posTanX + 15);
                expV.setY(lastY + 45);
                if((lastY - posTanY)<0){
                    durationTime = 0-((lastY - posTanY) * 5);
                }
                else{
                    durationTime = ((lastY - posTanY) * 5);
                }
                pathTransition.setDuration(Duration.millis(durationTime));
                pathTransition.setNode(images);
                pathTransition.setPath(new Line(posTanX + 20, posTanY + 20, posTanX + 20, lastY + 35));
            }
            case "DOWN" -> {
                images = new ImageView("images/missile-down.gif");
                for (int i = posTanY / 40; i < map.map.length; i++) {
                    char ch = map.map[i][posTanX / 40];
                    if (ch == 's' || ch == 'k' || ch == 'h' || ch == 'j' || ch == 'i' || ch == 'g') {
                        lastY = (i * 40);
                        break;
                    } else {
                        lastY = posBotY;

                    }
                }
                expV.setX(posTanX + 15);
                expV.setY(lastY - 20);
                if((lastY - posTanY)<0){
                    durationTime = 0-((lastY - posTanY) * 5);
                }
                else{
                    durationTime = ((lastY - posTanY) * 5);
                }
                pathTransition.setDuration(Duration.millis(durationTime));
                pathTransition.setNode(images);
                pathTransition.setPath(new Line(posTanX + 20, posTanY + 20, posTanX + 20, lastY + 5));
            }
            case "LEFT" -> {
                images = new ImageView("images/missile-left.gif");
                for (int i = posTanX / 40; i >= 0; i--) {
                    char ch = map.map[posTanY / 40][i];
                    if (ch == 's' || ch== 'k' || ch== 'h' || ch == 'j' || ch == 'i' || ch== 'g') {
                        lastX = (i * 40);
                        break;
                    } else {
                        lastX = posBotX;
                    }
                }
                expV.setX(lastX + 40);
                expV.setY(posTanY + 15);
                if((lastX - posTanX)<0){
                    durationTime = 0-((lastX - posTanX) * 5);
                }
                else{
                    durationTime = ((lastX - posTanX) * 5);
                }
                pathTransition.setDuration(Duration.millis(durationTime));
                pathTransition.setNode(images);
                pathTransition.setPath(new Line(posTanX + 20, posTanY + 20, lastX + 15, posTanY + 20));
            }
            case "RIGHT" -> {
                images = new ImageView("images/missile-right.gif");
                pathTransition.setNode(images);

                for (int i = posTanX / 40; i < map.map.length; i++) {
                    char ch = map.map[posTanY / 40][i];
                    if (ch == 's' || ch== 'k' || ch == 'h' || ch == 'j' || ch == 'i' || ch == 'g') {
                        //lastX = (i * 40);
                        lastX = posBotX;
                        break;
                    } else {
                        lastX = posBotX;
                    }
                }
                //expV.setX(lastX - 18);
                //expV.setY(posTanY + 20);
                expV.setX(lastX);
                expV.setY(posTanY + 20);
                if((lastX - posTanX)<0){
                    durationTime = 0-((lastX - posTanX) * 5);
                }
                else{
                    durationTime = ((lastX - posTanX) * 5);
                }
                pathTransition.setDuration(Duration.millis(durationTime));
                pathTransition.setPath(new Line(posTanX + 20, posTanY + 20, lastX + 20, posTanY + 20));
            }
        }

        pathTransition.play();

        pathTransition.setCycleCount(1);


        pathTransition.setOnFinished(e -> {
            images.setVisible(false);
            expV.setVisible(true);
            scaleTransition.setNode(expV);


            scaleTransition.setDuration(Duration.millis(100));
            scaleTransition.setByX(2);
            //Set how much Y should
            scaleTransition.setByY(2);
            scaleTransition.play();
            scaleTransition.setOnFinished(event -> {
                expV.setVisible(false);
                health--;
            } );
            if(map.map[lastY /40][posTanX /40]=='k' || map.map[lastY /40][posTanX /40]== 'h' || map.map[lastY /40][posTanX /40]== 'j' || map.map[lastY /40][posTanX /40]== 'i' || map.map[lastY /40][posTanX /40]== 'g' ){
                int val = map.map[lastY /40][posTanX /40];
                if(val == 104){
                    map.map[lastY /40][posTanX /40]=  '0';
                    map.graphicsContext.clearRect(posTanX, lastY, 40, 40);
                }
                else {
                    map.map[lastY /40][posTanX /40]= (char) (map.map[lastY /40][posTanX /40]-1);
                }
            }
            else if( map.map[posTanY /40][lastX /40]=='k' || map.map[posTanY /40][lastX /40]== 'h' || map.map[posTanY /40][lastX /40]== 'j' || map.map[posTanY /40][lastX /40]== 'i' || map.map[posTanY /40][lastX /40]== 'g'){

                int val = map.map[posTanY /40][lastX /40];
                if(val == 104){
                    map.map[posTanY /40][lastX /40]='0';
                    map.graphicsContext.clearRect(lastX, posTanY, 40, 40);
                }
                else {
                    map.map[posTanY /40][lastX /40]= (char) (map.map[posTanY /40][lastX /40]-1);
                }

            }

        });


        getChildren().addAll(images,expV);

    }
}
