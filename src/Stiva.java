public class Stiva {
    int len = 0;
    Node top = null;

    public Stiva() {
    }

    public void push(String data)
    {
        if (top == null) {
            top = new Node(data);
            top.setNext(null);
        } else {
            Node temp = new Node(data);
            temp.setNext(top);
            top = temp;
        }
    }

    public String pop()
    {
        if (top == null)
            return null;
        else {
            Node temp = top;
            top = temp.getNext();
            return temp.getData();
        }
    }

     public String top()
    {
        if (top == null)
            return null;
        return top.getData();
    }

    public int getLen() {
        return len;
    }

    public boolean isEmpty() {
        return top == null;
    }
}

class Node {
    private String data;
    private Node nextNode = null;

    public Node(String data) {
        this.data = data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public Node getNext() {
        return nextNode;
    }

    public void setNext(Node next) {
        nextNode = next;
    }
}
