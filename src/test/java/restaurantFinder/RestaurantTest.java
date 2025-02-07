package restaurantFinder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void add_initial_default_restaurant_for_test() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant restaurant1 = Mockito.spy(restaurant);
        Mockito.doReturn(LocalTime.parse("13:00:00")).when(restaurant1).getCurrentTime();
        assertTrue(restaurant1.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant restaurant1 = Mockito.spy(restaurant);
        Mockito.doReturn(LocalTime.parse("09:00:00")).when(restaurant1).getCurrentTime();
        assertFalse(restaurant1.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>Order Total<<<<<<<<<<<<<<<<<<<<<<<<<

    public int get_order_total_for_test(List<String> itemNames) {
        restaurant.addToOrder(itemNames);
        return restaurant.getOrderTotal();
    }
    @Test
    public void adding_sweet_corn_soup_in_order_should_return_order_cost_119() {
        List<String> itemNames = new ArrayList<String>();
        itemNames.add("Sweet corn soup");
        int orderTotal = get_order_total_for_test(itemNames);
        assertEquals(119, orderTotal);

    }
    @Test
    public void adding_sweet_corn_soup_and_vegetable_lasagne_in_order_should_return_order_cost_388() {
        List<String> itemNames = new ArrayList<String>();
        itemNames.add("Sweet corn soup");
        itemNames.add("Vegetable lasagne");
        int orderTotal = get_order_total_for_test(itemNames);
        assertEquals(388, orderTotal);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<Order Total>>>>>>>>>>>>>>>>>>>>>>>>>
}