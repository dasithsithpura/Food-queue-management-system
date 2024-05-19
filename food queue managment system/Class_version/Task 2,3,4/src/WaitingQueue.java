
//  Circular Queue

class WaitingQueue {
    private Customer[] queue;
    private int front;
    private int rear;
    private int capacity;

    public WaitingQueue(int capacity) {
        this.queue = new Customer[capacity];
        this.front = 0;
        this.rear = 0;
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        return front == rear;
    }

    public boolean isFull() {
        return (rear + 1) % capacity == front;
    }

    public void enqueue(Customer customer) {
        if (isFull()) {
            System.out.println("Queue is full");
            return;
        }

        rear = (rear + 1) % capacity;
        queue[rear] = customer;
    }

    public Customer dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return null;

        }

        front = (front + 1) % capacity;
        return queue[front];
    }
    public boolean availability(){
        for( Customer customer: queue )
        {
            if(customer!= null){
                return true;
            }

        }
        return false;
    }
}