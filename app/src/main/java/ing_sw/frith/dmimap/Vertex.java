package ing_sw.frith.dmimap;



public class Vertex {


    private int v;
    private int w;


    public Vertex(int v, int w) {

        this.v = v;
        this.w = w;
    }


    public int getV() {

        return v;
    }

    public int W() {

        return w;

    }

    @Override
    public String toString() {

        return "{" +
                "v=" + v +
                ", w=" + w +
                '}';
    }
}
