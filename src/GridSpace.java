public class GridSpace
{
    public boolean up,down,left,right,mine,start,end;
    public Integer X,Y, prevX,prevY,distance;
    private static final int UP = 1;
    private static final int DOWN = 4;
    private static final int RIGHT = 2;
    private static final int LEFT = 8;
    private static final int MINE = 64;
    private static final int START = 16;
    private static final int END = 32;

    public GridSpace(int num, int x, int y){
        up = ((num & UP) == UP);
        down = ((num & DOWN) == DOWN);
        right = ((num & RIGHT) == RIGHT);
        left = ((num & LEFT) == LEFT);
        mine = ((num & MINE) == MINE);
        start = ((num & START) == START);
        end = ((num & END) == END);

        distance = 1000000;
        X = x;
        Y = y;
    }
}
