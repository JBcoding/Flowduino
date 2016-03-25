public class BranchData {
    public int x;
    public int y;
    public int width;
    public int height;

    public BranchData(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public BranchData(int x, int y, int width) {
        this(x, y);
        this.width = width;
    }

    public BranchData(int x, int y, int width, int height) {
        this(x, y, width);
        this.height = height;
    }

    public int nextX() {
        return x + width;
    }

    public int nextY() {
        return y + height;
    }
}
