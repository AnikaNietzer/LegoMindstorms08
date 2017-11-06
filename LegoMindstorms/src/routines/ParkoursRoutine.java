package routines;

public abstract class ParkoursRoutine implements Runnable{

    @Override
    public void run() {
        // TODO Auto-generated method stub
        start();
        
    }

    protected abstract void start();
    
    public void stop() {
        
    }
}
