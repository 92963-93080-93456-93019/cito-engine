package ua.tqs.cito.model;

public class test {
    public static void main(String args[]){
        Rider r1 = new Rider(1L,"Dinis","Cruz","912223334","Mercedes","00-00-00");
        r1.addRep(5);
        System.out.println(r1.getReputation());
    }

}
