package tankgame;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class Map extends Pane {


    int size;
    int x;
    int y;
    StackPane mapStack;
    Canvas mapCanvas;
    GraphicsContext graphicsContext;
    char[][] map =     {{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'},
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

    int getSize(){
        return map.length;
    }
    char getValue(int y, int x){
        return map[y][x];
    }
    Map(){

        mapCanvas = new Canvas(520,520);
        graphicsContext = mapCanvas.getGraphicsContext2D();
        mapStack = new StackPane();

        mapStack.setStyle("-fx-background-color: black");

        for(int i=0; i<map.length; i++){
            for(int j=0; j<map.length; j++){
                if(map[i][j] == 's'){
                    Image img = new Image("images/stone.gif");
                    drawMap(img,j,i);

                }
                else if(map[i][j] == 'w'){
                    Image img = new Image("images/grass.gif");
                    drawMap(img,j,i);

                }
                else if(map[i][j] == 'k' ||
                        map[i][j] =='j' ||
                        map[i][j] =='i' ||
                        map[i][j] == 'h'){
                    Image img = new Image("images/brick.gif");
                    drawMap(img,j,i);

                }
                else if(map[i][j] == 'v'){
                    Image img = new Image("images/water.gif");
                    drawMap(img,j,i);

                }
                else if(map[i][j] == 'd'){
                    Image img = new Image("images/home.gif");
                    drawMap(img,j,i);

                }

            }


        }
        mapStack.getChildren().add(mapCanvas);

    }
    char getValueAt(int i1, int i2){
        try{
            return map[i1][i2];

        }catch (Exception e){
            return '1';
        }
    }
    void drawMap(Image img, int j, int i){
        graphicsContext.drawImage(img,j*40+20,i*40+20);
        graphicsContext.drawImage(img, j*40+20,i*40);
        graphicsContext.drawImage(img, j*40,i*40+20);
        graphicsContext.drawImage(img, j*40,i*40);
    }

}
