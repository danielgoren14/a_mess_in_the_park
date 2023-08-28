import java.util.*;

public class Park extends Thread {
    private List<Visitor> visitors;
    private List<Apparatus> apparatuses;

    public Park () {
        this.visitors = new ArrayList<>();
        this.apparatuses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Visitor visitor = new Visitor(i, "Visitor_"+i);
            this.visitors.add(visitor);
        }
        for (int i = 0; i < 5; i++) {
            Apparatus apparatus = new Apparatus(i,"Apparatus_"+i);
            this.apparatuses.add(apparatus);
        }
    }

    @Override
    public void run() {
        while (true) {
            for (Visitor visitor : this.visitors) {
                System.out.println(visitor.toStringForCurrentApparatus());
                Random random = new Random();
                int randomApparatusId = random.nextInt(0,5);
                System.out.println(randomApparatusId);
                boolean isPreferenceRight = false;
                Apparatus currentApparatus = this.apparatuses.get(randomApparatusId);
                if (visitor.getPreferences().get(randomApparatusId) != null && visitor.getPreferences().get(randomApparatusId) != 0) {
                    isPreferenceRight = true;
                    System.out.println("preference of visitor: "+ visitor + "\n" + "and the percentages of the preference = " + visitor.getPreferences().get(randomApparatusId) + " accepted");
                }
                if (isPreferenceRight) {
                    synchronized (visitor) {
                        synchronized (currentApparatus) {
                            visitor.addOneToAmountOfUsedApparatuses();
                            System.out.println("waiting time " + currentApparatus.getActivityTime());
                            if (currentApparatus.getCurrentVisitorsAmount() < currentApparatus.getCapacity()) {
                                visitor.setCurrentApparatus(currentApparatus);
                                System.out.println("visitor to add = " + visitor.toString());
                                currentApparatus.addVisitor(visitor);
                                currentApparatus.addOneToCurrentVisitorsAmount();
                                currentApparatus.getVisitors().stream().forEach(visitor1 -> {
                                    System.out.println(visitor1.getId() + "\n" + visitor1.toString());
                                });
                            } else {
                                System.out.println("the current apparatus " + currentApparatus + " is already full in capacity and waiting to start a round, please wait");
                            }
                        }
                    }
                } else {
                    if (visitor.getPreferences().get(randomApparatusId) == null) {
                        System.out.println("The visitor doesn't even want to go to this apparatus because its reference for this preference for apparatus" + currentApparatus.toString() + " is null" + "\n" + "NULL");
                    } else if (visitor.getPreferences().get(randomApparatusId) == 0) {
                        System.out.println("The visitor doesn't even want to go to this apparatus because its reference for this preference for apparatus " + currentApparatus.toString() + " is zero" + "\n" + "ZERO");
                    }

                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int timeToSleep = 0;
            for (Apparatus apparatus : this.apparatuses) {
//                System.out.println(apparatus + ") \n" + apparatus.getVisitors());
                System.out.println("activity time of Apparatus "+ apparatus.toString() + "is "+ apparatus.getActivityTime());
                if (apparatus.getActivityTime() > timeToSleep) {
                    timeToSleep = apparatus.getActivityTime();
                }
            }
            System.out.println("The highest activity time is " + timeToSleep);
            try {
                Thread.sleep(1000 * timeToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.apparatuses.stream().forEach(apparatus -> apparatus.initialize());
            System.out.println("Round finished");
        }

//        try {
//            System.out.println("waiting time "+this.apparatuses.get(randomApparatusId).getActivityTime());
////                                    Thread.sleep(random.nextInt(this.apparatuses.get(randomApparatusId).getActivityTime()) * 1000);
//            Thread.sleep(2000);
////                                this.apparatuses.get(randomApparatusId).initializeApparatus();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) {
        Thread parkThread = new Thread(new Park());
        parkThread.start();
    }
}
