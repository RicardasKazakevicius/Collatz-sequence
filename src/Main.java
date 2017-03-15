
/**
 * @author Ričardas Kazakevičius
 */
// 7 užduotis. Class Divider divedes array with n numbers into intervals. 
// Finder class finds number with longest iteration in Collatz search

public class Main {
 
    public static void main(String args[]) throws InterruptedException {
        int maxThreads = 32; // maximum number of threads in performance test
        int n = 5000000; // array size
        int intervalSize = 500;
        long array[] = new long[n];
        
        // creating array with numbers from 1 to n
        for (int i = 0; i < n; ++i) {
            array[i] = i + 1;
        }
        
        // storage is for seting number with most iterations
        Storage storage = new Storage();
        
        double speedUp = 0; // how many times threads work faster
        double time = 0; // one thread word time
        System.out.println("Threads  TimeSec  SpeedUp");
        
        // running multithreading performace test
        for (int threads = 1; threads <= maxThreads; threads *= 2) {
            
             // creating and initializing divider
            Divider divider = new Divider();
            divider.initialize(array, intervalSize);
        
            long time0 = System.currentTimeMillis();
            
            storage = new Storage();
            
            // creating threads
            Finder[] finder = new Finder[threads];

            for (int i = 0; i < threads; ++i) {
                finder[i] = new Finder(i, divider, storage);
                finder[i].start();
            }

            for (int i = 0; i < threads; ++i) {
                finder[i].join();
            }
            
            long time1 = System.currentTimeMillis();

            double dtime = (time1-time0)/1000.;
            
            if (threads == 1) {
                speedUp = 1;
                time = dtime;
            }
            else {
                speedUp = time / dtime;
            }
           
            System.out.println(threads + "        " + dtime + "    " + speedUp);
        }
        
        System.out.println("");
        System.out.println("Number with most iterations is: " + storage.getNumber());        
    }
}

