
public class Finder extends Thread{
    Storage storage;
    Divider divider;
    private long number;
    private long numberOfIterations;
    private long numberWithMostIterations; //?
    private long mostIterations; //? volatile
    private final int threadNumber;
    
    public Finder(int threadNumber, Divider divider, Storage storage) {
        this.threadNumber = threadNumber;
        this.storage = storage;
        this.divider = divider;
    }
    
    public void findNumberWithMostIterations(long[] array) {
       
        for (int i = 0; i < array.length; ++i) { //array was
            find(array[i]);
        }
    }
    
    private void find(long number) {

        numberOfIterations = numberOfIterations(number);
        
        if (numberOfIterations > mostIterations) {
            mostIterations = numberOfIterations;
            numberWithMostIterations = number;
        }
    }
        
    private long numberOfIterations(long number) {
        long iterations = 0; 
        
        while (number != 1) 
        {
            if ((number % 2) == 0) {
                number = number / 2;
            }
            else {
                number = 3 * number + 1;
            }
            ++iterations;
        }
        
        return iterations;
    }
    
    @Override
    public void run() {
       
        long[] tempArray;
        
        while ((tempArray = divider.getInterval()) != null) {
                   
            findNumberWithMostIterations(tempArray);       
        }
        
        storage.setNumber(mostIterations, numberWithMostIterations);       
    }
}
