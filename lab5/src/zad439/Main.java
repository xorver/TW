package zad439;

public class Main {

    public static void main(String[] args){
        Table table = new Table();
        for(int i=0;i<Properties.N;i++){
            new Thread(new Person(table,"men",i)).start();
            new Thread(new Person(table,"women",i)).start();
        }
    }
}
