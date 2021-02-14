package restaurantFinder;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();
    private List<String> order = new ArrayList<String>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        LocalTime currentTime = getCurrentTime();
        int afterOpening = currentTime.compareTo(openingTime);
        int beforeClosing = closingTime.compareTo(currentTime);
        return afterOpening >= 0 && beforeClosing >= 0;
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        return menu;
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("RestaurantFinder.Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }

    public void addToOrder(List<String> itemNames) {
        for(String orderItem: itemNames) {
            order.add(orderItem);
        }
    }

    public int getOrderTotal() {
        int orderTotal = 0;
        for(String orderItem: order) {
            Item orderItemObject = findItemByName(orderItem);
            if(orderItemObject != null) {
                orderTotal += orderItemObject.getPrice();
            }
        }
        return orderTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return name.equals(that.name) &&
                location.equals(that.location) &&
                openingTime.equals(that.openingTime) &&
                closingTime.equals(that.closingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location, openingTime, closingTime);
    }
}
