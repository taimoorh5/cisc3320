public class mutex {

    private static Semaphore sem = new Semaphore(1);

    public static int acquire() {
        if(sem.availablePermits()==1){
            int pid= PidManager.allocate_pid();
            try{
                sem.acquire();
                System.out.println("Successfully acquired a lock for Pid "+ pid);
                return pid;
            }
            catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }
        else System.out.println("There is 0 permits available");
        return -1;
    }

    public static void release(int pid){
        sem.release();
        PidManager.release_pid(pid);
        System.out.println("Successfully released lock for Pid "+ pid);
        System.out.println(sem.availablePermits() + " permit(s) are available.");
    }

    public static void main(String[] args) {

        PidManager pM = new PidManager();
        pM.allocate_map();

        mutex m1= new mutexLock();
        int pid = m1.acquire();

        for(int i = 0; i < 5; i++) {

            PidSleepThread thread = new PidSleepThread(i);
            m1.acquire();
            thread.start();
            try {
                thread.join(10);
            }
            catch (InterruptedException e) {
                e.getMessage();
            }
            if(i==4) m1.release(pid);
        }

    }
}