package com.bloc.blocspot.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.bloc.blocspot.blocspot.R;
import com.bloc.blocspot.model.CategoriesDao;
import com.bloc.blocspot.model.Category;
import com.bloc.blocspot.model.Place;
import com.bloc.blocspot.model.PlacesDao;

/**
 * Created by Wayne on 11/24/2014.
 */
public class Utilities {
    public static void createInitialDatabase(final Context context){
        SharedPreferences sharedPrefs = context.getSharedPreferences("Custom", Context.MODE_PRIVATE);

        boolean isCreated = sharedPrefs.getBoolean(context.getString(R.string.pref_database_created), false);

        if(isCreated){
            return;
        }

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                CategoriesDao categoriesDao = new CategoriesDao(context);
                Category category = new Category();
                //in the insert we not need pass id because it is created automatically

                category.setFriendly_name("Accounting");
                category.setGoogle_name("accounting");
                categoriesDao.insert(category);

                category.setFriendly_name("Airport");
                category.setGoogle_name("airport");
                categoriesDao.insert(category);

                category.setFriendly_name("Amusement Park");
                category.setGoogle_name("amusement_park");
                categoriesDao.insert(category);

                category.setFriendly_name("Aquarium");
                category.setGoogle_name("aquarium");
                categoriesDao.insert(category);

                category.setFriendly_name("Art Gallery");
                category.setGoogle_name("art_gallery");
                categoriesDao.insert(category);

                category.setFriendly_name("ATM");
                category.setGoogle_name("atm");
                categoriesDao.insert(category);

                category.setFriendly_name("Bakery");
                category.setGoogle_name("bakery");
                categoriesDao.insert(category);

                category.setFriendly_name("Bank");
                category.setGoogle_name("bank");
                categoriesDao.insert(category);

                category.setFriendly_name("Bar");
                category.setGoogle_name("bar");
                categoriesDao.insert(category);

                category.setFriendly_name("Beauty Salon");
                category.setGoogle_name("beauty_salon");
                categoriesDao.insert(category);

                category.setFriendly_name("Bicycle Store");
                category.setGoogle_name("bicycle_store");
                categoriesDao.insert(category);

                category.setFriendly_name("Book Store");
                category.setGoogle_name("book_store");
                categoriesDao.insert(category);

                category.setFriendly_name("Bowling Alley");
                category.setGoogle_name("bowling_alley");
                categoriesDao.insert(category);

                category.setFriendly_name("Bus Station");
                category.setGoogle_name("bus_station");
                categoriesDao.insert(category);

                category.setFriendly_name("Cafe");
                category.setGoogle_name("cafe");
                categoriesDao.insert(category);

                category.setFriendly_name("Campground");
                category.setGoogle_name("campground");
                categoriesDao.insert(category);

                category.setFriendly_name("Car Dealer");
                category.setGoogle_name("car_dealer");
                categoriesDao.insert(category);

                category.setFriendly_name("Car Rental");
                category.setGoogle_name("car_rental");
                categoriesDao.insert(category);

                category.setFriendly_name("Car Repair");
                category.setGoogle_name("car_repair");
                categoriesDao.insert(category);

                category.setFriendly_name("Car Wash");
                category.setGoogle_name("car_wash");
                categoriesDao.insert(category);

                category.setFriendly_name("Casino");
                category.setGoogle_name("casino");
                categoriesDao.insert(category);

                category.setFriendly_name("Cemetery");
                category.setGoogle_name("cemetery");
                categoriesDao.insert(category);

                category.setFriendly_name("Church");
                category.setGoogle_name("church");
                categoriesDao.insert(category);

                category.setFriendly_name("City Hall");
                category.setGoogle_name("city_hall");
                categoriesDao.insert(category);

                category.setFriendly_name("Clothing Store");
                category.setGoogle_name("clothing_store");
                categoriesDao.insert(category);

                category.setFriendly_name("Convenience Store");
                category.setGoogle_name("convenience_store");
                categoriesDao.insert(category);

                category.setFriendly_name("Courthouse");
                category.setGoogle_name("courthouse");
                categoriesDao.insert(category);

                category.setFriendly_name("Dentist");
                category.setGoogle_name("dentist");
                categoriesDao.insert(category);

                category.setFriendly_name("Department Store");
                category.setGoogle_name("department_store");
                categoriesDao.insert(category);

                category.setFriendly_name("Doctor");
                category.setGoogle_name("doctor");
                categoriesDao.insert(category);

                category.setFriendly_name("Electrician");
                category.setGoogle_name("electrician");
                categoriesDao.insert(category);

                category.setFriendly_name("Electronics Store");
                category.setGoogle_name("electronics_store");
                categoriesDao.insert(category);

                category.setFriendly_name("Embassy");
                category.setGoogle_name("embassy");
                categoriesDao.insert(category);

                category.setFriendly_name("Establishment");
                category.setGoogle_name("establishment");
                categoriesDao.insert(category);

                category.setFriendly_name("Finance");
                category.setGoogle_name("finance");
                categoriesDao.insert(category);

                category.setFriendly_name("Fire Station");
                category.setGoogle_name("fire_station");
                categoriesDao.insert(category);

                category.setFriendly_name("Florist");
                category.setGoogle_name("florist");
                categoriesDao.insert(category);

                category.setFriendly_name("Food");
                category.setGoogle_name("food");
                categoriesDao.insert(category);

                category.setFriendly_name("Funeral Home");
                category.setGoogle_name("funeral_home");
                categoriesDao.insert(category);

                category.setFriendly_name("Furniture Store");
                category.setGoogle_name("furniture_store");
                categoriesDao.insert(category);

                category.setFriendly_name("Gas Station");
                category.setGoogle_name("gas_station");
                categoriesDao.insert(category);

                category.setFriendly_name("General Contractor");
                category.setGoogle_name("general_contractor");
                categoriesDao.insert(category);

                category.setFriendly_name("Grocery/Supermarket");
                category.setGoogle_name("grocery_or_supermarket");
                categoriesDao.insert(category);

                category.setFriendly_name("Gym");
                category.setGoogle_name("gym");
                categoriesDao.insert(category);

                category.setFriendly_name("Hair Care");
                category.setGoogle_name("hair_care");
                categoriesDao.insert(category);

                category.setFriendly_name("Hardware Store");
                category.setGoogle_name("hardware_store");
                categoriesDao.insert(category);

                category.setFriendly_name("Health");
                category.setGoogle_name("health");
                categoriesDao.insert(category);

                category.setFriendly_name("Hindu Temple");
                category.setGoogle_name("hindu_temple");
                categoriesDao.insert(category);

                category.setFriendly_name("Home Goods Store");
                category.setGoogle_name("home_goods_store");
                categoriesDao.insert(category);

                category.setFriendly_name("Hospital");
                category.setGoogle_name("hospital");
                categoriesDao.insert(category);

                category.setFriendly_name("Insurance Agency");
                category.setGoogle_name("insurance_agency");
                categoriesDao.insert(category);

                category.setFriendly_name("Jewelry Store");
                category.setGoogle_name("jewelry_store");
                categoriesDao.insert(category);

                category.setFriendly_name("Laundry");
                category.setGoogle_name("laundry");
                categoriesDao.insert(category);

                category.setFriendly_name("Lawyer");
                category.setGoogle_name("lawyer");
                categoriesDao.insert(category);

                category.setFriendly_name("Library");
                category.setGoogle_name("library");
                categoriesDao.insert(category);

                category.setFriendly_name("Liquor Store");
                category.setGoogle_name("liquor_store");
                categoriesDao.insert(category);

                category.setFriendly_name("Local Government Office");
                category.setGoogle_name("local_government_office");
                categoriesDao.insert(category);

                category.setFriendly_name("Locksmith");
                category.setGoogle_name("locksmith");
                categoriesDao.insert(category);

                category.setFriendly_name("Lodging");
                category.setGoogle_name("lodging");
                categoriesDao.insert(category);

                category.setFriendly_name("Meal Delivery");
                category.setGoogle_name("meal_delivery");
                categoriesDao.insert(category);

                category.setFriendly_name("Meal Takeaway");
                category.setGoogle_name("meal_takeaway");
                categoriesDao.insert(category);

                category.setFriendly_name("Mosque");
                category.setGoogle_name("mosque");
                categoriesDao.insert(category);

                category.setFriendly_name("Movie Rental");
                category.setGoogle_name("movie_rental");
                categoriesDao.insert(category);

                category.setFriendly_name("Movie Theater");
                category.setGoogle_name("movie_theater");
                categoriesDao.insert(category);

                category.setFriendly_name("Moving Company");
                category.setGoogle_name("moving_company");
                categoriesDao.insert(category);

                category.setFriendly_name("Museum");
                category.setGoogle_name("museum");
                categoriesDao.insert(category);

                category.setFriendly_name("Night Club");
                category.setGoogle_name("night_club");
                categoriesDao.insert(category);

                category.setFriendly_name("Painter");
                category.setGoogle_name("painter");
                categoriesDao.insert(category);

                category.setFriendly_name("Park");
                category.setGoogle_name("park");
                categoriesDao.insert(category);

                category.setFriendly_name("Parking");
                category.setGoogle_name("parking");
                categoriesDao.insert(category);

                category.setFriendly_name("Pet Store");
                category.setGoogle_name("pet_store");
                categoriesDao.insert(category);

                category.setFriendly_name("Pharmacy");
                category.setGoogle_name("pharmacy");
                categoriesDao.insert(category);

                category.setFriendly_name("Physiotherapist");
                category.setGoogle_name("physiotherapist");
                categoriesDao.insert(category);

                category.setFriendly_name("Place of Worship");
                category.setGoogle_name("place_of_worship");
                categoriesDao.insert(category);

                category.setFriendly_name("Plumber");
                category.setGoogle_name("plumber");
                categoriesDao.insert(category);

                category.setFriendly_name("Police");
                category.setGoogle_name("police");
                categoriesDao.insert(category);

                category.setFriendly_name("Post Office");
                category.setGoogle_name("post_office");
                categoriesDao.insert(category);

                category.setFriendly_name("Real Estate Agency");
                category.setGoogle_name("real_estate_agency");
                categoriesDao.insert(category);

                category.setFriendly_name("Restaurant");
                category.setGoogle_name("restaurant");
                categoriesDao.insert(category);

                category.setFriendly_name("Roofing Contractor");
                category.setGoogle_name("roofing_contractor");
                categoriesDao.insert(category);

                category.setFriendly_name("RV Park");
                category.setGoogle_name("rv_park");
                categoriesDao.insert(category);

                category.setFriendly_name("School");
                category.setGoogle_name("school");
                categoriesDao.insert(category);

                category.setFriendly_name("Shoe Store");
                category.setGoogle_name("shoe_store");
                categoriesDao.insert(category);

                category.setFriendly_name("Shopping Mall");
                category.setGoogle_name("shopping_mall");
                categoriesDao.insert(category);

                category.setFriendly_name("Spa");
                category.setGoogle_name("spa");
                categoriesDao.insert(category);

                category.setFriendly_name("Stadium");
                category.setGoogle_name("stadium");
                categoriesDao.insert(category);

                category.setFriendly_name("Storage");
                category.setGoogle_name("storage");
                categoriesDao.insert(category);

                category.setFriendly_name("Store");
                category.setGoogle_name("store");
                categoriesDao.insert(category);

                category.setFriendly_name("Subway Station");
                category.setGoogle_name("subway_station");
                categoriesDao.insert(category);

                category.setFriendly_name("Synagogue");
                category.setGoogle_name("synagogue");
                categoriesDao.insert(category);

                category.setFriendly_name("Taxi Stand");
                category.setGoogle_name("taxi_stand");
                categoriesDao.insert(category);

                category.setFriendly_name("Train Station");
                category.setGoogle_name("train_station");
                categoriesDao.insert(category);

                category.setFriendly_name("Travel Agency");
                category.setGoogle_name("travel_agency");
                categoriesDao.insert(category);

                category.setFriendly_name("University");
                category.setGoogle_name("university");
                categoriesDao.insert(category);

                category.setFriendly_name("Veterinary Care");
                category.setGoogle_name("veterinary_care");
                categoriesDao.insert(category);

                category.setFriendly_name("Zoo");
                category.setGoogle_name("zoo");
                categoriesDao.insert(category);

                category.setFriendly_name("Default Favorite Category");
                category.setFavorite("Y");
                category.setColor("#40c4ff");
                category.setGoogle_name("default_category");
                categoriesDao.insert(category);

                return null;
            }
        }.execute();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                PlacesDao placesDao = new PlacesDao(context);
                Place place = new Place();

                //in the insert we not need pass id because it is created automatically
                //place.setName("Uncategorized");
                //place.setDescription("Default description");
                //placesDao.insert(place);

                return null;
            }
        }.execute();


        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(context.getString(R.string.pref_database_created), true);
        editor.commit();
        //Message.message(context, "com.bloc.blocspot.database created");

    }
}
