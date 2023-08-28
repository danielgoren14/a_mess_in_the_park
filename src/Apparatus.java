import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Apparatus{
    private List<Visitor> visitors;
    private int id;
    private String name;
    private int activityTime;
    private int capacity;
    private int currentVisitorsAmount;
//    private final Lock lock = new ReentrantLock();

    public Apparatus (int id, String name) {
        this.visitors = new ArrayList<>();
        this.id = id;
        this.name = name;
        Random random = new Random();
        this.activityTime = random.nextInt(10,31);
        this.capacity = random.nextInt(1,7);
//        this.currentVisitorsAmount = 0;
    }
//    public boolean tryUse() {
//        if (capacity < capacity && lock.tryLock()) {
//            capacity++;
//            return true;
//        }
//        return false;
//    }
    public int getActivityTime() {
        return activityTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void addOneToCurrentVisitorsAmount() {
        this.currentVisitorsAmount += 1;
    }

    public int getCurrentVisitorsAmount() {
        return currentVisitorsAmount;
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }

//    public void release() {
//        lock.unlock();
//        this.visitors = new ArrayList<>();
//        this.setCapacity(0);
//    }

    public void initialize () {
        this.visitors = new ArrayList<>();
        this.currentVisitorsAmount = 0;
    }
//    public void lockForDuration(long durationMillis) throws InterruptedException {
//        lock.lock();
//        Thread.sleep(durationMillis);
//        lock.unlock();
//    }

    public void addVisitor(Visitor visitor) {
        this.visitors.add(visitor);
    }

    @Override
    public String toString() {
        return "Apparatus{" +
                "visitors=" + visitors +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", activityTime=" + activityTime +
                ", capacity=" + capacity +
                ", currentVisitorsAmount=" + currentVisitorsAmount +
//                ", lock=" + lock +
                '}';
    }
}
