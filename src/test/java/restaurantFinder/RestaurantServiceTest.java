package restaurantFinder;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;

    @BeforeEach
    public void add_initial_default_restaurant_for_test() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        assertEquals(restaurant, service.findRestaurantByName("Amelie's cafe"));
    }

    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.findRestaurantByName("Pantry d'or"));
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>IS RESTAURANT OPEN OR CLOSE<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void Is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() throws restaurantNotFoundException {
        Restaurant restaurant1 = Mockito.spy(service.findRestaurantByName("Amelie's cafe"));
        Mockito.doReturn(LocalTime.parse("13:00:00")).when(restaurant1).getCurrentTime();
        assertTrue(restaurant1.isRestaurantOpen());
    }

    @Test
    public void Is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() throws restaurantNotFoundException {
        Restaurant restaurant1 = Mockito.spy(service.findRestaurantByName("Amelie's cafe"));
        Mockito.doReturn(LocalTime.parse("09:00:00")).when(restaurant1).getCurrentTime();
        assertFalse(restaurant1.isRestaurantOpen());
    }
    //<<<<<<<<<<<<<<<<<<<<<IS RESTAURANT OPEN OR CLOSE>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>
}