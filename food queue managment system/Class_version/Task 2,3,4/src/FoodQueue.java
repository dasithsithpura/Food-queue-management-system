
//  FoodQueue class

public class FoodQueue
{
    private Customer[] customers;
    private int maxSize;
    private int size;

    public FoodQueue(int maxSize){
        this.maxSize = maxSize;
        this.customers = new Customer[maxSize];
        this.size =0;
    }

    public Customer[] getCustomers()
    {
        return customers;
    }

    public void setCustomers( Customer[] customers )
    {
        this.customers = customers;
    }


//      use for display customers in the Queue

    public void displayQueue(){
        int i = 0;
        for( Customer customer: customers )
        {
            if(customer!= null) {
                 System.out.println( ( i + 1 ) + ". " + customer.getFirstName() +" "+customer.getLastName());
                 i++;
            }
        }
    }

    public int getMaxSize()
    {
        return maxSize;
    }

    public void setMaxSize( int maxSize )
    {
        this.maxSize = maxSize;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize( int size )
    {
        this.size = size;
    }

    public Customer get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return customers[index];
    }


    public boolean isFull()
    {
        return size == maxSize;
    }
    public boolean isEmpty() {
        return size == 0;
    }


    public int getLength() {
        return size;
    }


//      methode that use for enter user into the Queue
//      @param customer pass the customer object

    public void addCustomer(Customer customer) {
        if (!isFull()) {
            customers[size] = customer;
            size++;
            System.out.println("Customer added to queue.");
        } else {
            System.out.println(customer.getFirstName() + " queue is already full.");
        }
    }


//      removed selected customer
//      @param index customer index in the Queue
//      @return removed Customer

    public Customer removeCustomer(int index) {
        if (index >= 0 && index < size) {
            for (int i = index; i < size - 1; i++) {
                customers[i] = customers[i + 1];
            }
            Customer customer = customers[size-1];
            customers[size - 1] = null;
            size--;
            System.out.println("Customer removed from  queue.");
            return customer;
        } else {
            System.out.println("Invalid customer index.");
        }
        return null;
    }

    public double getIncome() {
        double income = 0;
        for (int i = 0; i < size; i++) {
            income += customers[i].getBurgersRequired() * 650;
        }
        return income;
    }

}
