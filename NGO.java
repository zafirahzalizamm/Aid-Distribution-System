/**
 * Subclass inherited from User
 * Act as Usertype of NGO and store the required details of ngo
 * Such as ngo name and ngo manpower number
 */
public class NGO extends User{

    // data types
    private String name;
    private String manpower;

    /**
     * Default constructor
     */
    NGO(){}

    /**
     * Overloaded constructor
     * @username the username of the ngo and call the superclass constructor(without password)
     * @name the name of the ngo
     * @manpower the manpower number of the ngo
     */
    NGO(String username, String name, String manpower){
        super(username);
        this.manpower = manpower;
        this.name = name;
    }

    /**
     * Overloaded constructor
     * @username the username of the ngo and call the superclass constructor
     * @password the password of the ngo and call the superclass constructor
     * @name the name of the ngo
     * @manpower the manpower number of the ngo
     */
    NGO(String username, String password, String name, String manpower){
        super(username,password);
        this.name = name;
        this.manpower = manpower;
    }

    /**
     * Getter of the ngo name
     * @return the name of the ngo
     */
    public String getNgoname(){
        return this.name;
    }

    /**
     * Getter of the manpower count
     * @return the manpower count of the ngo
     */
    public String getManpower(){
        return this.manpower;
    }

    /**
     *  Convert the ngo into csv string 
     *  Userful when csv string conversion of ngo to the aids.csv file
     *  @return the csv string representation of usertype,ngo superclass constructor,ngo name and manpower
     */
    public String toCSVString(){
        return "NGO" + "," + super.toCSVString() + "," + this.name + "," + this.manpower;
    }

    /**
     * Method to create null ngo ("-","-","-")
     * Used to show that no donor is assigned in the aids.csv file
     * @return the null ngo ("-","-","-")
     */
    public static NGO nullngo(){
        return new NGO("-","-","-");
    }

}
