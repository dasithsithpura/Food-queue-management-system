import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


// QueueManagementSystem keep no of Queues and no of burgers

public class QueueManagementSystem
{
    // store to 3 queue
    private static FoodQueue[] foodQueues = new FoodQueue[3];
    private static final int maxQueues = 3;
    // define burger cou
    private static int burgerCount = 50;
    //waiting list
    private static WaitingQueue waitingQueue = new WaitingQueue( 20 );
    public QueueManagementSystem()
    {
        initializeQueues();
    }
// methode for display menu
    public Boolean displayMenu()
    {
        Scanner scanner = new Scanner( System.in );
        String option;
        System.out.println( "=============================================" );
        System.out.println( "-------------FOOD CENTER MENU----------------" );
        System.out.println( "=============================================" );
        System.out.println( "Enter 100 or VFQ: To View all Queues." );
        System.out.println( "Enter 101 or VEQ: To View all Empty Queues." );
        System.out.println( "Enter 102 or ACQ: To Add customer to a Queue." );
        System.out.println( "Enter 103 or RCQ: To Remove a customer from a Queue. (From a specific location)" );
        System.out.println( "Enter 104 or PCQ: To Remove a served customer." );
        System.out.println( "Enter 105 or VCS: To View Customers Sorted in alphabetical order." );
        System.out.println( "Enter 106 or SPD: To Store Program Data into file." );
        System.out.println( "Enter 107 or LPD: To Load Program Data from file." );
        System.out.println( "Enter 108 or STK: To View Remaining burgers Stock." );
        System.out.println( "Enter 109 or AFS: To Add burgers Stock." );
        System.out.println( "Enter 110 or IFQ: To View income of each queue" );
        System.out.println( "Enter 999 or EXT: To Exit the Program." );
        System.out.println( "---------------------------------------------" );

        System.out.print( "Enter your option: " );
        option = scanner.nextLine().trim().toUpperCase();

        switch( option )
        {
            // View all burger Queues
            case "100":
            case "VFQ":
                viewAllQueues();

                break;

            // View all Empty Queues
            case "101":
            case "VEQ":
                viewEmptyQueues();
                break;

            // Add customer to a Queue
            case "102":
            case "ACQ":
                addCustomer( scanner );
                break;

            // Remove a customer from a Queue
            case "103":
            case "RCQ":
                removeCustomer( scanner );

                break;

            // Remove a served customer
            case "104":
            case "PCQ":
                removeServedCustomer( scanner );

                break;

            // View Customers Sorted in alphabetical order
            case "105":
            case "VCS":
                viewCustomersSorted();

                break;

            // Store Program Data into file
            case "106":
            case "SPD":
                storeProgramData();
                break;

            // Load Program Data from file
            case "107":
            case "LPD":
                System.out.println( "Load Program Data from file" );
                fileRead();
                break;

            // View Remaining burger Stock
            case "108":
            case "STK":
                viewStock();
                break;

            // Add burger Stock
            case "109":
            case "AFS":
                addBurgersToStock( scanner );
                break;
            case "110":
            case "IFQ":
                printQueueIncome();
                break;
            // Exit the Program
            case "999":
            case "EXT":
                System.out.println( "Exit the Program" );
                System.out.println( "Thank you!" );
                return false;

            // if the user enter invalid option
            default:
                System.out.println( "Invalid option.Please try again." );
                break;

        }
        return true;
    }
    // methode for initialize Queues
    private void initializeQueues()
    {
        foodQueues[0] = new FoodQueue( 2 );
        foodQueues[1] = new FoodQueue( 3 );
        foodQueues[2] = new FoodQueue( 5 );
    }
    // methode for adding customers to the Queues
    private void addCustomer( Scanner scanner )
    {
        System.out.print( "Enter customer’s first name: " );
        String firstName = scanner.nextLine();
        System.out.print( "Enter customer’s last name: " );
        String lastName = scanner.nextLine();
        int burgersRequired = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter the number of burgers required: ");
                burgersRequired = Integer.parseInt(scanner.nextLine());
                if (burgersRequired > 5) {
                    System.out.println("Warning: Maximum 5 burgers can be ordered.");
                    continue;
                }
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        Customer customer = new Customer( firstName, lastName, burgersRequired );

        if( burgerCount < burgersRequired )
        {
            System.out.println( "Can't offer that many burgers. We have " + burgerCount + " burgers remaining." );
        }
        else
        {
            FoodQueue shortestQueue = getShortestQueue( maxQueues );
            if( shortestQueue != null )
            {
                shortestQueue.addCustomer( customer );
            }
            else
            {
                System.out.println(
                        "All queues are full. Customer cannot be queued. Customer will be added to the waiting list." );
                waitingQueue.enqueue( customer );
            }
        }
    }


//      this methode helps identify the shortest path
//      @param maxQueues no of Queues as parameter
//      @return shortest food Queue

    private FoodQueue getShortestQueue( int maxQueues )
    {
        FoodQueue shortestQueue = null;
        for( int i = 0; i < maxQueues; i++ )
        {
            if( !foodQueues[i].isFull() )
            {
                if( shortestQueue == null || foodQueues[i].getLength() <= shortestQueue.getLength() )
                {
                    shortestQueue = foodQueues[i];
                }
            }
        }
        return shortestQueue;
    }


//      This method removes a Customer from the selected Queue.
//      @param scanner the Scanner object used to get input
//      @return the removed Customer

    private Customer removeCustomer(Scanner scanner) {
        System.out.println("Select the queue from which a customer should be removed:");
        displayQueueOptions();
        int queueIndex;
        FoodQueue selectedQueue;

        while (true) {
            try {
                queueIndex = Integer.parseInt(scanner.nextLine()) - 1;

                if (queueIndex >= 0 && queueIndex < maxQueues) {
                    selectedQueue = foodQueues[queueIndex];
                    if( selectedQueue.getSize()==0 ){
                        System.out.println("Selected Queue is Empty");
                        break;
                    }
                    break;
                } else {
                    System.out.println("Invalid queue index. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid queue index.");
            }
        }
        System.out.println("Select the customer index to remove:");
        selectedQueue.displayQueue();
        int customerIndex;
        Customer customer;

        while (true) {
            try {
                customerIndex = Integer.parseInt(scanner.nextLine()) - 1;

                if (customerIndex >= 0 && customerIndex < selectedQueue.getSize()) {
                    customer = selectedQueue.removeCustomer(customerIndex);
                    return customer;
                } else {
                    System.out.println("Invalid customer index. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid customer index.");
            }
        }
    }


//      methode for look burger count and update burger count
//      @param scanner get input

    private void addBurgersToStock( Scanner scanner )
    {
        System.out.println("Burger Count :"+ burgerCount);
        System.out.print( "Enter the number of burgers to add to the stock: " );
        int burgersToAdd = scanner.nextInt();
        scanner.nextLine();

        burgerCount += burgersToAdd;

        System.out.println( burgersToAdd + " burgers added to the stock." );
        System.out.println( "Remaining burger stock: " + burgerCount );
    }


//      methode for display no of queue

    private void displayQueueOptions()
    {
        for( int i = 0; i < maxQueues; i++ )
        {
            System.out.println( "Queue" + ( ( i + 1 ) ) );
        }
        System.out.print( "Enter your choice: " );
    }


//      methode for remove Served Customer
//      @param input get input

    private void removeServedCustomer( Scanner input )
    {
        Customer customer = removeCustomer( input );
        burgerCount -= customer.getBurgersRequired();
        if( waitingQueue.availability())
        {
            FoodQueue shortestQueue = getShortestQueue( maxQueues );
            if( shortestQueue != null )
            {
                shortestQueue.addCustomer( waitingQueue.dequeue() );
                System.out.println( "Waiting List Customer Added to the Queue" );
            }
        }

    }


//      methode for get all customers
//      @return all customers as string Array

    private String[] getAllCustomers()
    {
        String[] allCustomers = new String[10];
        int customerCount = 0;

        for( FoodQueue queue : foodQueues )
        {
            Customer[] customers = queue.getCustomers();
            for( int i = 0; i < queue.getLength(); i++ )
            {
                Customer customer = customers[i];
                if( customer != null )
                {
                    allCustomers[customerCount] = customer.getFirstName() + " " + customer.getLastName();
                    customerCount++;
                }
            }
        }

        return allCustomers;
    }


//      methode for count all customers handle null values
//      @param array String Array of customers
//      @return count

    private int countCustomers( String[] array )
    {
        int count = 0;
        for( String customer : array )
        {
            if( customer != null )
            {
                count++;
            }
        }
        return count;
    }


//      methode for view Sorted customers

    public void viewCustomersSorted()
    {
        String[] allCustomers = getAllCustomers();
        int customerCount = countCustomers( allCustomers );

        // Bubble sort the customers
        for( int i = 0; i < customerCount - 1; i++ )
        {
            for( int j = 0; j < customerCount - i - 1; j++ )
            {
                if( allCustomers[j].compareToIgnoreCase( allCustomers[j + 1] ) > 0 )
                {
                    // Swap elements
                    String temp = allCustomers[j];
                    allCustomers[j] = allCustomers[j + 1];
                    allCustomers[j + 1] = temp;
                }
            }
        }

        // Print the sorted customers
        System.out.println( "Customers Sorted in alphabetical order:" );
        for( int i = 0; i < customerCount; i++ )
        {
            System.out.println( allCustomers[i] );
        }
    }


//      methode for print Queue Income

    private void printQueueIncome()
    {
        for( int i = 0; i < maxQueues; i++ )
        {
            System.out.println( "Queue " + i + "income: " + foodQueues[i].getIncome() );
        }

    }

    public void viewAllQueues()
    {
        System.out.println( "*****************" );
        System.out.println( "*   Cashiers   *" );
        System.out.println( "*****************" );

        int lineLength = 0;
        for( FoodQueue queue : foodQueues )
        {
            lineLength = Math.max( lineLength, queue.getMaxSize() );
        }

        for( int i = 0; i < lineLength; i++ )
        {
            for( FoodQueue newQueue : foodQueues )
            {
                if( i < newQueue.getMaxSize() )
                {
                    Customer[] temp = newQueue.getCustomers();
                    if( temp[i] != null )
                    {
                        System.out.print( "0" );
                    }
                    else
                    {
                        System.out.print( "X" );
                    }

                }
                else
                {
                    System.out.print( " " );
                }
                System.out.print( "      " );
            }
            System.out.println();
        }

    }


//      methode for view Queues

    public void viewEmptyQueues()
    {
        for( int i = 0; i < foodQueues.length; i++ )
        {
            int occupiedCustomers = foodQueues[i].getLength();
            int availableSpaces = foodQueues[i].getMaxSize() - occupiedCustomers;

            System.out.println( "Queue " + ( i + 1 ) + " : Available spaces: " + availableSpaces );
            System.out.println( "Occupied places: " + occupiedCustomers );
            System.out.println();
        }
    }


//      methode for store data in to text file

    public void storeProgramData()
    {
        try
        {
            FileWriter writer = new FileWriter( "program_data.txt" );
            String tempNote = "Remaining burger stock is: " + burgerCount + "\n";
            for( int i = 0; i < foodQueues.length; i++ )
            {
                Customer[] customers = foodQueues[i].getCustomers();
                for( int j = 0; j < foodQueues[i].getLength(); j++ )
                {
                    Customer customer = customers[j];
                    if( customer != null )
                    {
                        tempNote += "Queue " + ( i + 1 ) + " Customer " + ( j + 1 ) + ": " +
                                            customer.getFirstName() + " " + customer.getLastName() + "\n";
                    }
                }
            }
            writer.write( tempNote );
            writer.close();
            System.out.println( "Data successfully stored into the file!" );
        }
        catch( IOException e )
        {
            System.out.println( e.getMessage() );
        }
    }


//      methode for load the Text file

    private void fileRead()
    {
        File file = new File( "program_data.txt" );
        try
        {
            Scanner reader = new Scanner( file );
            while( reader.hasNextLine() )
            {
                System.out.println( reader.nextLine() );
            }
            reader.close();
        }
        catch( Exception e )
        {
            System.out.println( e );
        }
    }


//      methode for View burger stock

    private void viewStock()
    {
        System.out.println( "Remaining burger stock: " + burgerCount );
    }

    public static void main( String[] args )
    {
        QueueManagementSystem queueManagementSystem = new QueueManagementSystem();
        boolean choice = true;
        while( choice )
        {
            choice = queueManagementSystem.displayMenu();
        }
    }
}
