package ru.geekbrains.hw4_1;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static final int SIZE=5;
    public static final int DOTS_TO_WIN=4;
    public static char[][] map = new char[SIZE][SIZE];
    public static Scanner scanner= new Scanner(System.in);
    public static Random random= new Random();
    public static int[] moveToWinPoint= new int[2];


    public static void main(String[] args) {
	initMap();
	printMap();
    do {
        playerTurn();
        System.out.println();
        if (isWin()) {
            System.out.println("You Win!");
            break;
        }
        pcTurn();
        if (isWin()) {
            System.out.println("PC Win!");
            break;
        }

    }while(!isWin());
    }

    public static void initMap()
    {
        for (int i = 0; i <SIZE ; i++) {
            for (int j = 0; j <SIZE ; j++) {
                map[i][j]='*';
            }
        }
    }
    public static void printMap()
    {
        for (int i = 0; i <SIZE ; i++) {
            for (int j = 0; j <SIZE ; j++) {
                System.out.print(map[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public static boolean isEmpty(int x, int y)
    {
        if (x>=0&&y>=0&&x<SIZE&&y<SIZE&&map[x][y]=='*')
        {
                return true;
        }
        else return false;
    }

    public static void playerTurn()
    {
        int x,y;
        do {
            System.out.println("Введите координаты желаемого нолика:");
             x = scanner.nextInt()-1;
             y = scanner.nextInt()-1;
        } while (!isEmpty(x,y));
        map[x][y]='0';
        printMap();
    }

    public static void pcTurn()
    {
        if (isMoveToWin())
        {
            //пытается выиграть или предотвратить выигрыш
            map[moveToWinPoint[0]][moveToWinPoint[1]]='X';
            printMap();
        }
        else if (bestPcMove()){
            //пытается поставить крестик рядом со своим
            map[moveToWinPoint[0]][moveToWinPoint[1]]='X';
            printMap();
        } else
            {
                //если крестики закрыты или их нет - рандом
                int x,y;
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isEmpty(x,y));
        map[x][y]='X';
        printMap();
            }
    }

    public static boolean isWin()
    {
        int k=0;
        //горизонтали
        for (int i = 0; i <SIZE ; i++) {
            for (int j = 0; j <SIZE-DOTS_TO_WIN+1 ; j++) {
                if(map[i][j]!='*')
                while(k<DOTS_TO_WIN)
                {
                    k++;
                    if (map[i][j]!=map[i][j+k])  {k =0; break;}
                    if (k==DOTS_TO_WIN-1) return true;
                }
            }
        }
        //вертикали
        for (int i = 0; i <SIZE-DOTS_TO_WIN+1; i++) {
            for (int j = 0; j <SIZE ; j++) {
                if(map[i][j]!='*')
                while(k<DOTS_TO_WIN)
                {
                    k++;
                    if (map[i][j]!=map[i+k][j])  {k =0; break;}
                    if (k==DOTS_TO_WIN-1) return true;
                }
            }
        }
        //главные диагонали
        for (int i = 0; i <SIZE-DOTS_TO_WIN+1 ; i++) {
            for (int j = 0; j <SIZE-DOTS_TO_WIN+1; j++) {
                if(map[i][j]!='*')
                while(k<DOTS_TO_WIN)
                {
                    k++;
                    if (map[i][j]!=map[i+k][j+k])  {k =0; break;}
                    if (k==DOTS_TO_WIN-1) return true;
                }
            }
        }
        //побочные диагонали
        for (int i = 0; i<SIZE-DOTS_TO_WIN+1 ; i++) {
            for (int j = DOTS_TO_WIN-1; j <SIZE ; j++) {
                if(map[i][j]!='*')
                while(k<DOTS_TO_WIN)
                {
                    k++;
                    if (map[i][j]!=map[i+k][j-k])  {k =0; break;}
                    if (k==DOTS_TO_WIN-1) return true;
                }
            }
        }
       return false;
    }


    public static boolean isMoveToWin()
    {
        //проверка, что ИИ может выиграть
        for (int i = 0; i <SIZE ; i++) {
            for (int j = 0; j <SIZE ; j++) {
                if (isEmpty(i,j))
                {
                    map[i][j]='X';
                    if (isWin())
                    {
                        map[i][j]='*';
                        moveToWinPoint[0]=i;
                        moveToWinPoint[1]=j;
                        return true;
                    }
                    else map[i][j]='*';
                }
            }
        }
        //проверка, что следующим ходом не выиграет игрок
        for (int i = 0; i <SIZE ; i++) {
            for (int j = 0; j <SIZE ; j++) {
                if (isEmpty(i,j))
                {
                    map[i][j]='0';
                    if (isWin())
                    {
                        map[i][j]='*';
                        moveToWinPoint[0]=i;
                        moveToWinPoint[1]=j;
                        return true;
                    }
                    else map[i][j]='*';
                }
            }
        }
        return false;
    }

    public static boolean bestPcMove()
    {
        for (int i = 0; i <SIZE ; i++) {
            for (int j = 0; j <SIZE ; j++) {
                if (map[i][j]=='X')
                {
                    for (int k = i-1; k <i+2 ; k++) {
                        for (int l = j-1; l<j+2 ; l++) {
                            if (k>=0&&l>=0&&k<SIZE&&l<SIZE&&map[k][l]=='*')
                            {
                                moveToWinPoint[0]=k;
                                moveToWinPoint[1]=l;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


}
