package fr.ubx.poo.model.decor;

public class Door extends Decor{
    private boolean closed;
    private boolean prev;

    public boolean isClosed() {
        return closed;
    }
    public boolean isPrev(){
        return prev;
    }


    public Door(boolean closed,boolean prev){
        this.closed = closed;
        this.prev = prev;
    }
}
