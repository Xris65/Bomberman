package fr.ubx.poo.model.decor;

public class Door extends Decor{
    private boolean closed;
    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }


    public Door(boolean closed){
        this.closed = closed;
    }
}
