package Model;

/**
 * Created by Dani on 1/19/2017.
 */
public class Tuple <E1,E2> {

    private E1 el1;
    private E2 el2;

    public Tuple(E1 e1, E2 e2) {
        this.el1 = e1;
        this.el2 = e2;
    }

    public E1 getFirst() {
        return el1;
    }

    public E2 getSecond() {
        return el2;
    }

    @Override
    public String toString() {
        return this.el1.toString();
    }
}
