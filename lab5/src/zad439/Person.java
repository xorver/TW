package zad439;

public class Person implements Runnable{
    private Table table;
    private String description;
    private int id;

    public Person(Table table, String description, int id) {
        this.table = table;
        this.description = description;
        this.id= id;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while(true){
            try {
                table.seatAtTable(id);
                System.out.println(description+ " with id: "+id+" is sitting");
                table.leaveTable();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
