//Taimoor Hafeez
//CISC 3320 Operating Systems
//Assignment 2

class PidManager {

  private static final int minPid = 300;
  private static final int maxPid = 5000;
  private static int [] pids;


  public static int allocate_map() {
    pids = new int[maxPid - minPid];         // creates an array of size 4700
    for (int i = 0; i < pids.length; i++) {
      pids[i] = 0;                           // assigns a value of 0 to every index meaning every pid is ready to assigned
    }
    if (pids != null) return 1;              // returns 1 if array is initialized and -1 if it not
    else return -1;
  }

  public static int allocate_pid() {
    int pidNumber = 0;                       // declaring a pid value of 0
    for(int i = 0; i < pids.length; i++){    // Goes through each index and if value is 0, changes it to 1 meaning it is in use and assigns a value to pidNumber which is equal to the index + minPid
      if(pids[i] == 0){
        pids[i] = 1;
        pidNumber = i + minPid;
        break;
      }
    }
    if(pidNumber == 0) return -1;           // if pidNumber is still 0 meaning every Pid is in use, -1 is returned. If pidNumber is changes, pidNumber is returned
    return pidNumber;
  }
  
  public static void release_pid(int pid) {
    if (pid < minPid || pid > maxPid)      // if the pid is less than min pid or greater than max pid, error message is returned.
      System.out.println("This Pid is not available for use");
    int pidIndex = pid - minPid;           // this is the index of the pid that is to be released. Just subtract minPid to get index because minPid was added in allocate_pid method. 
    if (pids[pidIndex] == 0)               // Prints error message if pid that is to be released has already been released. 
      System.out.println("This Pid already released and available to be assigned"); 
    pids[pidIndex] = 0; // changes index of pid from 1 to 0 which is from in use to not in use
    System.out.println("Pid: " + pid + " is released"); // lets you know that pid has been successfully released.
  }

}