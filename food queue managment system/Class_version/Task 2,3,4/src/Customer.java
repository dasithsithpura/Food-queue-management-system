public class Customer
{
    private String firstName;
    private String lastName;
    private int burgersRequired;

    public Customer(String firstName, String lastName, int burgersRequired) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.burgersRequired = burgersRequired;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBurgersRequired() {
        return burgersRequired;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public void setBurgersRequired( int burgersRequired )
    {
        this.burgersRequired = burgersRequired;
    }
}
