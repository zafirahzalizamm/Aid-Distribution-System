/**
 * Act as a superclass for the donated and requested aid
 * Stores aid name and aid quantity
 */
public class Aid {
    private String aid;
    private Integer quantity;
    private String status;

    /**
     * Default constructor
     */
    Aid(){}

    /**
     * Overloaded constructor
     * @param aid the name of the aid donated or requested
     * @param quantity the quantity of the aid donated or requested
     */
    Aid(String aid,Integer quantity,String status){
        this.aid = aid.toUpperCase();
        this.quantity = quantity;
        this.status = status;
    }

    /**
     * Getter for the name of aid
     * @return name of aid donated // requested
     */
    public String getAidname(){
        return this.aid;
    }

    /**
     * Getter for the quantity of aid (in string)
     * Used to display the quantity in string in the tableview of javafx
     * @return string representation of quantity of aid donated // requested
     */
    public String getAidqty(){
        return this.quantity.toString();
    }

    public String getStatus(){
        return this.status;
    }

    public void setstatus(String status){
        this.status = status;
    }


    /**
     * Getter for the quantity of aid
     * @return quantity of aid donated // requested
     */
    public Integer getQuantity(){
        return this.quantity;
    }

    /**
     * Setter for the quantity of aid
     * Act as the modifier to modify the private data field (this.quantity)
     * @param quantity new quantity of aid
     */
    public void setqty(Integer quantity){
        this.quantity = quantity;
    }
}
