package main;

import java.util.ArrayList;

//-------------------------------
class Piece {
    public final String type;
    public final String color;
    public int numberOfTimesMoved=0;
    public int x;
    public int y;
    //--------------------------------
    Piece(String type,String color,int xPos,int yPos){
        this.type=type;
        this.color=color;
        this.x=xPos;
        this.y=yPos;
    }
    @Override
    public String toString() {
        return type;
    }
}
//-------------------------------

public class Board {
    private final Piece[][]board;
    //------------------------
    Board(){
        board=new Piece[8][8];
        initialize();
    }
    //-------------------------
    public void initialize(){
        //pawns
        for(int i=0;i<8;i++) {
            board[1][i] = new Piece("Pawn", "Black",1,i);
            board[6][i]=new Piece("Pawn","White",6,i);
        }
        //Bishops
        board[0][2]=new Piece("Bishop","Black",0,2);
        board[0][5]=new Piece("Bishop","Black",0,5);
        board[7][2]=new Piece("Bishop","White",7,2);
        board[7][5]=new Piece("Bishop","White",7,5);
        //Knights
        board[0][1]=new Piece("Knight","Black",0,1);
        board[0][6]=new Piece("Knight","Black",0,6);
        board[7][1]= new Piece("Knight","White",7,1);
        board[7][6]=new Piece("Knight","White",7,6);
        //Rooks
        board[0][0]=new Piece("Rook","Black",0,0);
        board[0][7]=new Piece("Rook","Black",0,7);
        board[7][0]=new Piece("Rook","White",7,0);
        board[7][7]=new Piece("Rook","White",7,7);
        //Kings and Queens
        board[0][3]=new Piece("Queen","Black",0,3);
        board[7][3]=new Piece("Queen","White",7,3);
        board[0][4]=new Piece("King","Black",0,4);
        board[7][4]=new Piece("King","White",7,4);
    }
    //-------------------------
    public ArrayList<Integer[]> getAllowedMoves(int x, int y){
        ArrayList<Integer[]>pieceMoves=new ArrayList<>();
        switch(board[x][y].type){
            case "Pawn":pieceMoves.addAll(pawnMoves(x,y));break;
            case "Bishop":pieceMoves.addAll(bishopMoves(x,y));break;
            case "Knight":pieceMoves.addAll(knightMoves(x,y));break;
            case "Rook":pieceMoves.addAll(rookMoves(x,y));break;
            case "King":pieceMoves.addAll(kingMoves(x,y));break;
            case "Queen": pieceMoves.addAll(queenMoves(x,y));break;
        }
        return pieceMoves;
    }
    //-------------------------
    public ArrayList<Integer[]> pawnMoves(int x,int y){
        ArrayList<Integer[]> list=new ArrayList<>();
        if(board[x][y].color.equals("White")){
                if(board[x-1][y]==null){
                    list.add(new Integer[]{x-1,y});
                    if(board[x][y].numberOfTimesMoved==0&&board[x-2][y]==null)
                        list.add(new Integer[]{x-2,y});
                }
                if((y<7) && board[x-1][y+1]!=null && board[x-1][y+1].color.equals("Black"))
                    list.add(new Integer[]{x-1,y+1});
                if((y>0) && board[x-1][y-1]!=null && board[x-1][y-1].color.equals("Black"))
                    list.add(new Integer[]{x-1,y-1});
        }
        else {
            if(board[x+1][y]==null){
                list.add(new Integer[]{x+1,y});
                if(board[x][y].numberOfTimesMoved==0&&board[x+2][y]==null)
                    list.add(new Integer[]{x+2,y});
            }
            if((y<7) && board[x+1][y+1]!=null &&! board[x+1][y+1].color.equals("Black"))
                list.add(new Integer[]{x+1,y+1});
            if((y>0) && board[x+1][y-1]!=null &&! board[x+1][y-1].color.equals("Black"))
                list.add(new Integer[]{x+1,y-1});
        }
        return list;
    }
    //--------------------------
    public ArrayList<Integer[]> rookMoves(int x,int y){
        ArrayList<Integer[]> list=new ArrayList<>();
        boolean up=true,down=true,left=true,right=true;
        int a,b;
        a=b=x;
        for(int i=0;i<8;i++){
            if(a<7 && down){
                if(board[a+1][y]==null)
                    list.add(new Integer[]{a+1,y});
                else {
                    if(!board[a+1][y].color.equals(board[x][y].color))
                        list.add(new Integer[]{a+1,y});
                    down=false;
                }
                a++;
            }
            if(b>0 && up){
                if(board[b-1][y]==null)
                    list.add(new Integer[]{b-1,y});
                else {
                    if(!board[b-1][y].color.equals(board[x][y].color))
                        list.add(new Integer[]{b-1,y});
                    up=false;
                }
                b--;
            }
        }
        a=b=y;
        for(int i=0;i<8;i++){
            if(a<7 && left){
                if(board[x][a+1]==null)
                    list.add(new Integer[]{x,a+1});
                else {
                    if(!board[x][a+1].color.equals(board[x][y].color))
                        list.add(new Integer[]{x,a+1});
                    left=false;
                }
                a++;
            }
            if(b>0 && right){
                if(board[x][b-1]==null)
                    list.add(new Integer[]{x,b-1});
                else {
                    if(!board[x][b-1].color.equals(board[x][y].color))
                        list.add(new Integer[]{x,b-1});
                    right=false;
                }
                b--;
            }
        }
        return list;
    }
    //-------------------------
    public ArrayList<Integer[]> queenMoves(int x,int y){
        ArrayList<Integer[]> list=new ArrayList<>();
        list.addAll(rookMoves(x,y));
        list.addAll(bishopMoves(x,y));
        return list;
    }
    //------------------------
    public ArrayList<Integer[]> bishopMoves(int x,int y){
        ArrayList<Integer[]> list=new ArrayList<>();
        boolean up=true,down=true,left=true,right=true;
        int a,b,c,d;
        a=c=x;
        b=d=y;
        for(int i=0;i<8;i++){
            if((a<7&&b<7) && down){
                if(board[a+1][b+1]==null)
                    list.add(new Integer[]{a+1,b+1});
                else {
                    if(!board[a+1][b+1].color.equals(board[x][y].color))
                        list.add(new Integer[]{a+1,b+1});
                    down=false;
                }
                a++;b++;
            }
            if((c>0&&d>0) && up){
                if(board[c-1][d-1]==null)
                    list.add(new Integer[]{c-1,d-1});
                else {
                    if(!board[c-1][d-1].color.equals(board[x][y].color))
                        list.add(new Integer[]{c-1,d-1});
                    up=false;
                }
                c--;d--;
            }
        }
        a=c=x;
        b=d=y;
        for(int i=0;i<8;i++){
            if((a>0&&b<7) && left){
                if(board[a-1][b+1]==null)
                    list.add(new Integer[]{a-1,b+1});
                else {
                    if(!board[a-1][b+1].color.equals(board[x][y].color))
                        list.add(new Integer[]{a-1,b+1});
                    left=false;
                }
                a--;b++;
            }
            if((c<7&&d>0) && right){
                if(board[c+1][d-1]==null)
                    list.add(new Integer[]{c+1,d-1});
                else {
                    if(!board[c+1][d-1].color.equals(board[x][y].color))
                        list.add(new Integer[]{c+1,d-1});
                    right=false;
                }
                c++;d--;
            }
        }
        return list;
    }
    //-----------------------
    public ArrayList<Integer[]> kingMoves(int x,int y){
        ArrayList<Integer[]> list=new ArrayList<>();
        int[][]locations={{x-1,y-1},{x-1,y},{x-1,y+1},{x,y-1},{x,y+1},{x+1,y-1},{x+1,y},{x+1,y+1}};
        return fillLocations(x, y, list, locations);
    }
    //-----------------------
    public ArrayList<Integer[]> knightMoves(int x,int y){
        ArrayList<Integer[]> list=new ArrayList<>();
        int[][]locations={{x-2,y-1},{x+2,y+1},{x-1,y-2},{x+1,y+2},{x-2,y+1},{x-1,y+2},{x+1,y-2},{x+2,y-1}};
        return fillLocations(x, y, list, locations);
    }
    //-----------------------
    private ArrayList<Integer[]> fillLocations(int x, int y, ArrayList<Integer[]> list, int[][] locations) {
        for(int[] loc :locations){
            if(loc[0]>-1&&loc[1]>-1&&loc[0]<8&&loc[1]<8)
                if(board[loc[0]][loc[1]]==null|| !board[loc[0]][loc[1]].color.equals(board[x][y].color))
                    list.add(new Integer[]{loc[0],loc[1]});
        }
        return list;
    }
    //------------------------
    public Piece[][] getBoard() {
        return board;
    }
    //----------------------------
}