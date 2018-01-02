package com.example.my2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import java.util.ArrayList;
import java.util.List;


public class GameView extends GridLayout {
	private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();
	
	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardWidth = (Math.min(w, h) - 10) / 4;
        
        addCards(cardWidth, cardWidth);
        
        startGame();

    }
	
	private void startGame() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }
	
	private void checkComplete() {
        boolean complete = true;
        ALL: for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() == 0
                        || (x > 0 && cardsMap[x][y].equals(cardsMap[x - 1][y]))
                        || (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y]))
                        || (y > 0 && cardsMap[x][y].equals(cardsMap[x][y - 1]))
                        || (y < 3 && cardsMap[x][y].equals(cardsMap[x][y + 1]))) {

                    complete = false;
                    break ALL;
                }
            }
        }
        if (complete) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Oh")
                    .setMessage("Game Over!")
                    .setPositiveButton("restart",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    startGame();

                                }
                            }).show();
        }

    }

    private void addRandomNum() {
        emptyPoints.clear();// 清空
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));
                }

            }
        }
        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);

    }
    
    private void addCards(int cardWith, int cardHeight){
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
            	cardsMap[x][y]=new Card(getContext());
            	cardsMap[x][y].setNum(0);
                addView(cardsMap[x][y], cardWith, cardHeight);
            }
        }
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
        super(context);
        initGameView();
    }
    private void swipeLeft() { // 往左滑动手指
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                
                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            
                            x--;
                            break;
                        } else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            break;
                        }
                    }
                }
            }
        }

    }

    private void swipeRight() {
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >=0; x--) {
                
                for (int x1 = x - 1; x1 >=0; x1--) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            
                            x++;
                            break;
                        } else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            break;
                        }
                    }
                }
            }
        }


    }

    private void swipeUp() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            
                            y--;
                            break;
                        } else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            break;
                        }
                    }
                }
            }
        }

    }

    private void swipeDown() {

        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >=0; y--) {
                
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            
                            y++;
                            break;
                        } else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            break;
                        }
                    }
                }
            }
        }
    }

    private void initGameView() {
    	setColumnCount(4);
    	//setBackgroundColor(0xffbbada0); // 设置整体背景
    	
        setOnTouchListener(new OnTouchListener() {
            //记录起始位置和偏移坐标
            private float startX, startY, offsetX, offsetY;
            
            

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: //监听手指按下的初始位置坐标
                    startX = event.getX();
                    startY = event.getY();
                    break;

                case MotionEvent.ACTION_UP: //监听手指离开时的位置坐标
                    offsetX = event.getX() - startX;
                    offsetY = event.getY() - startY;

                    if (Math.abs(offsetX) > Math.abs(offsetY)) {
                        if (offsetX < -5) {
                            System.out.println("left");
                            swipeLeft();
                        } else if (offsetX > 5) {
                            System.out.println("right");
                            swipeRight();
                        }
                    } else {
                        if (offsetY < -5) {
                            System.out.println("up");
                            swipeUp();
                        } else if (offsetY > 5) {
                            System.out.println("down");
                            swipeDown();
                        }
                    }
                    addRandomNum();
                    checkComplete();
          
                    break;
                default:
                    break;
                }

                return true;
            }
        });

    }
    
    

}