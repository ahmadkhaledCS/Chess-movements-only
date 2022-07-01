package main;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Game {

    public GridPane mainBoard;
    Board b=new Board();
    public Piece[][]pieces=b.getBoard();
    public Piece selected;
    boolean turn=false;
    //-------------------------
    public void initialize(){
        update();
    }
    public void update(){
        mainBoard.getChildren().removeIf(b -> b.getStyleClass().toString().equals("button"));
        mainBoard.getChildren().removeIf(b -> (b.getClass().equals(ImageView.class)));
        for(int i=0;i<pieces.length;i++)
            for (int j=0;j<pieces.length;j++)
                if(pieces[i][j]!=null) {
                    int i1=i;
                    int j1=j;
                    Button btn=new Button();
                    btn.setGraphic(getImage(pieces[i][j]));
                    btn.setOnAction(event -> buttonClicked(i1, j1));
                    mainBoard.add(btn, j, i);
                }
    }
    //-------------------------
    public void buttonClicked(int x, int y){
        selected=pieces[x][y];
        mainBoard.getChildren().removeIf(b -> (b.getClass().equals(ImageView.class)));
        if(turn&&selected.color.equals("White")||!turn&&selected.color.equals("Black"))
            return;
        for(Integer[] a:b.getAllowedMoves(x,y)){
            ImageView redImage =new ImageView(new Image("main\\assets\\red.png"));
            redImage.setFitHeight(50);
            redImage.setFitWidth(65);
            redImage.setOpacity(0.25);
            redImage.setOnMouseClicked(event -> move(a[0],a[1]));
            mainBoard.add(redImage,a[1],a[0]);
        }
    }
    //------------------------
    public void move(int x,int y){
        turn=!turn;
        Piece temp=new Piece(selected.type,selected.color,x,y);
        temp.numberOfTimesMoved++;
        pieces[x][y]=temp;
        pieces[selected.x][selected.y]=null;
        update();
    }
    //-----------------------
    public ImageView getImage(Piece piece) {
        String im = String.format("main\\assets\\%s%s.png", (piece.color.equals("Black")) ? "b" : "w", piece.type);
        ImageView imageView = new ImageView(new Image(im));
        imageView.setFitHeight(40);
        imageView.setFitWidth(45);
        return  imageView;
    }
}
