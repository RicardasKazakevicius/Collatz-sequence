
public class Storage {
    
    private long numberWithMostIterations;
    public long mostIterations;
    
    public synchronized void setNumber(long mostIterations, long number) {

        if (mostIterations > this.mostIterations) {
            numberWithMostIterations = number;
            this.mostIterations = mostIterations;
        }
    }
    
    public long getNumber() {
        
        return numberWithMostIterations;
    }
}
