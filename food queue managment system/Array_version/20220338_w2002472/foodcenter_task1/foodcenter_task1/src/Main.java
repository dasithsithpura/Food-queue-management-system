import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;


public class Main {
    //Declaring an array to store customers
    private static String[] queu1 = new String[2];
    private static String[] queu2 = new String[3];
    private static String[] queu3 = new String[5];
    private static String[][] queues = new String[3][];


    //to store integer values
    static int burgerstock = 50;

    //calculating the total available customers
    static int freeCount;


    public static void main(String[] args) {
        queues[0] = queu1;
        queues[1] = queu2;
        queues[2] = queu3;

        //while loop for loop entire program
        while (true) {
            //MENU
            System.out.println("""
                    =============================================                   
                    -------------FOOD CENTER MENU----------------
                    =============================================
                    Enter 100 or VFQ:- To View all Queues.
                    Enter 101 or VEQ:- To View all Empty Queues.
                    Enter 102 or ACQ:- To Add customer to a Queue.
                    Enter 103 or RCQ:- To Remove a customer from a Queue. (From a specific location)
                    Enter 104 or PCQ:- To Remove a served customer.
                    Enter 105 or VCS:- To View Customers Sorted in alphabetical order
                    Enter 106 or SPD:- To Store Program Data into file.
                    Enter 107 or LPD:- To Load Program Data from file.
                    Enter 108 or STK:- To View Remaining burgers Stock.
                    Enter 109 or AFS:- To Add burgers Stock.
                    Enter 999 or EXT:- To Exit the Program.
                    -----------------------------------------                  
                    """);

            Scanner get = new Scanner(System.in);
            System.out.print("Enter the option: ");
            String option = get.nextLine().toUpperCase();

            switch (option) {
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

                //Add customer to a Queue
                case "102":
                case "ACQ":
                    addCustomerToQueue(get);

                    break;

                //Remove a customer from a Queue
                case "103":
                case "RCQ":
                    removeCustomerFromQueue(get);

                    break;

                //Remove a served customer
                case "104":
                case "PCQ":
                    removeServedCustomer();

                    break;

                //View Customers Sorted in alphabetical order
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
                    System.out.println("Load Program Data from file");
                    fileRead();
                    break;

                // View Remaining burger Stock
                case "108":
                case "STK":
                    viewStock();
                    break;

                //Add burger Stock
                case "109":
                case "AFS":
                    addBurgersToStock(get);
                    break;

                // Exit the Program
                case "999":
                case "EXT":
                    System.out.println("Exit the Program");
                    System.out.println("Thank you!");
                    return;

                //if the user enter invalid option
                default:
                    System.out.println("Invalid option.Please try again.");
                    break;


            }
        }
    }

    //method for view all queues
    // Method for viewing all queues
    private static void viewAllQueues() {
        System.out.println("*****************");
        System.out.println("*   Cashiers   *");
        System.out.println("*****************");

        int lineLength = 0;
        for (String[] queue : queues) {
            lineLength = Math.max(lineLength, queue.length);
        }

        for (int i = 0; i < lineLength; i++) {
            for (String[] queue : queues) {
                if (i < queue.length) {
                    if (queue[i] != null) {
                        System.out.print("0");
                    } else {
                        System.out.print("X");
                    }

                } else {
                    System.out.print(" ");
                }
                System.out.print("      ");
            }
            System.out.println();
        }

    }

    //method for view all empty queues
    private static void viewEmptyQueues() {
        for (int i = 0; i < queues.length; i++) {
            int freeCount = countEmptySpaces(queues[i]);
            int occupiedCount = queues[i].length - freeCount;

            System.out.println("Queue " + (i + 1) + " : Available spaces: " + freeCount);
            System.out.println("Occupied places: " + occupiedCount);
            System.out.println();
        }
    }

    private static int countEmptySpaces(String[] queue) {
        int freeCount = 0;
        for (String name : queue) {
            if (name == null) {
                freeCount++;
            }
        }
        return freeCount;
    }



    //method for add to the customer

    private static void addCustomerToQueue(Scanner scanner) {
        int queueNum = getValidQueueNumber(scanner);
        if (queueNum == -1) {
            System.out.println("Invalid queue number. Please try again.");
            addCustomerToQueue(scanner);
            return;
        }

        String[] temp = queues[queueNum - 1];
        int index = getFirstAvailableIndex(temp);
        if (index == -1) {
            System.out.println("Queue is full. Customer cannot be added.");
            addCustomerToQueue(scanner);
            return;
        }

        System.out.println("Enter customer name: ");
        String customerName = scanner.nextLine();

        if (temp[index] != null) {
            System.out.println("Index is already occupied. Customer cannot be added.");
            addCustomerToQueue(scanner);
            return;
        }

        temp[index] = customerName;
        System.out.println("Customer added to queue " + queueNum);
    }

    private static int getValidQueueNumber(Scanner scanner) {
        System.out.println("Select queue number (1, 2, or 3): ");
        if (scanner.hasNextInt()) {
            int queueNum = scanner.nextInt();
            scanner.nextLine();
            if (queueNum >= 1 && queueNum <= 3) {
                return queueNum;
            }
        } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Clear the invalid input from the scanner
        }
        return -1;
    }

    private static int getFirstAvailableIndex(String[] queue) {
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == null) {
                return i;
            }
        }
        return -1;
    }



    //method for remove a customer from queue
    private static void removeCustomerFromQueue(Scanner scanner) {
        int queueNum = getValidQueueNumber(scanner);
        if (queueNum == -1) {
            System.out.println("Invalid queue number. Please try again.");
            removeCustomerFromQueue(scanner);
            return;
        }

        int position = getValidPosition(scanner, queueNum);
        if (position == -1) {
            System.out.println("Invalid position. Please try again.");
            return;
        }

        int queueIndex = queueNum - 1;
        String[] queue = queues[queueIndex];

        if (queue[position - 1] == null) {
            System.out.println("There is no customer at position " + position);
        } else {
            String removedCustomer = queue[position - 1];
            queue[position - 1] = null;
            System.out.println("Customer " + removedCustomer + " removed from Queue " + queueNum);
        }
    }


    private static int getValidPosition(Scanner scanner, int queueNum) {
        System.out.println("Enter position of the customer to remove: ");
        int position = scanner.nextInt();
        scanner.nextLine();

        int queueIndex = queueNum - 1;
        int queueLength = queues[queueIndex].length;

        if (position < 1 || position > queueLength) {
            System.out.println("Invalid position. Please try again.");
            return -1;
        }
        return position;
    }


    //method for remove served customer
    private static void removeServedCustomer() {
        System.out.println("Enter customer name to remove: ");
        Scanner scanner = new Scanner(System.in);
        String customerName = scanner.nextLine().toLowerCase();

        boolean customerFound = false;

        for (String[] queue : queues) {
            for (int j = 0; j < queue.length; j++) {
                if (queue[j] != null && queue[j].toLowerCase().equals(customerName)) {
                    System.out.println("Customer " + customerName + " removed from the queue.");
                    queue[j] = null;
                    updateStock();
                    customerFound = true;
                    break;
                }
            }
            if (customerFound) {
                break;
            }
        }

        if (!customerFound) {
            System.out.println("Customer " + customerName + " not found in any queue.");
        }
    }



    // Method for viewing customers sorted in alphabetical order
    private static void viewCustomersSorted() {
        String[] allCustomers = new String[10];
        int customerCount = 0;

        for (String[] queue : queues) {
            for (String customer : queue) {
                if (customer != null) {
                    allCustomers[customerCount] = customer;
                    customerCount++;
                }
            }
        }

        bubbleSort(allCustomers, customerCount);

        System.out.println("Customers Sorted in alphabetical order:");
        for (int i = 0; i < customerCount; i++) {
            System.out.println(allCustomers[i]);
        }
    }

    private static void bubbleSort(String[] array, int length) {
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (array[j] != null && array[j + 1] != null &&
                        array[j].compareToIgnoreCase(array[j + 1]) > 0) {
                    // Swap elements
                    String temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }


    // Method for storing program data into a file
    private static void storeProgramData() {
        try {
            FileWriter writer = new FileWriter("program_data.txt");
            String tempNote = "Remaining burger stock is: " + burgerstock + "\n";
            for (int i = 0; i < queues.length; i++) {
                for (int j = 0; j < queues[i].length; j++) {
                    if (queues[i][j] != null) {
                        tempNote += "Queue " + (i + 1) + " Customer " + (j + 1) + ": " + queues[i][j] + "\n";
                    }
                }
            }
            writer.write(tempNote);
            writer.close();
            System.out.println("Data successfully stored into the file!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //method for read from file
    private static void fileRead() {
        File file = new File("program_data.txt");
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                System.out.println(reader.nextLine());
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void updateStock() {
        burgerstock -= 5;
        if (burgerstock <= 10) {
            System.out.println("Warning: Low stock! Remaining burgers: " + burgerstock);
        }
    }


    // method for view remaining burger stock
    private static void viewStock() {
        System.out.println("Remaining burger stock: " + burgerstock);
    }

    //method for add to the burger stock
    private static void addBurgersToStock(Scanner scanner) {
        System.out.print("Enter the number of burgers to add to the stock: ");
        int burgersToAdd = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        burgerstock += burgersToAdd;

        System.out.println(burgersToAdd + " burgers added to the stock.");
        System.out.println("Remaining burger stock: " + burgerstock);
    }
}











