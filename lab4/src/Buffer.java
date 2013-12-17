public class Buffer {
    public int[] resources;

    public Buffer(){
        resources = new int[2*Properties.HALF_BUFFER_SIZE];
    }
}
