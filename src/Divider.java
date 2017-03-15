
public class Divider {
    private long[] array;
    private long[] interval;
    private int intervalSize;
    private int arrayPosition;
    
    public void initialize(long[] array, int intervalSize) {
        this.array = array;
        this.intervalSize = intervalSize;
        arrayPosition = 0;
    }
    
    public synchronized long[] getInterval() { //synchronized
        
        if (array.length > arrayPosition) {
            
            int intervalPosition = 0;
            
            interval = new long[setIntervalArraySize()];
                     
            while ((array.length > arrayPosition) && (intervalPosition < intervalSize)) {
                
                interval[intervalPosition] = array[arrayPosition];
                ++intervalPosition;
                ++arrayPosition;
            }

            return interval;
        }
        
        return null;
    }
    
    private int setIntervalArraySize() {
        if ((array.length - arrayPosition) >= intervalSize)
                return intervalSize;
            else
                return array.length - arrayPosition;
    }
}
