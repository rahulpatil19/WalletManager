package com.walletmanager.app.utils

import com.walletmanager.app.R
import com.walletmanager.app.domain.Category


object CategoryUtils {

    val incomeCategories = mutableListOf<Category>()

    val expenseCategories = mutableListOf<Category>()

    init {
        populateIncomeCategories()
        populateExpenseCategories()
    }

    fun populateIncomeCategories() {
        incomeCategories.add(Category(
            "Salary",
            R.drawable.ic_category_money_bag, R.color.forest_green))
        incomeCategories.add(Category(
            "Business",
            R.drawable.ic_category_entertainment, R.color.dark_red))
        incomeCategories.add(Category(
            "Interest",
            R.drawable.ic_category_money_bag, R.color.dark_slate_blue))
        incomeCategories.add(Category(
            "Work",
            R.drawable.ic_category_work, R.color.dark_khaki))
        incomeCategories.add(Category(
            "Rent",
            R.drawable.ic_category_rent, R.color.saddle_brown))
        incomeCategories.add(Category(
            "Pocket Money",
            R.drawable.ic_category_money_bag, R.color.yellow_green))
        incomeCategories.add(Category(
            "Other",
            R.drawable.ic_category_entertainment, R.color.orange_red))
    }

    private fun populateExpenseCategories() {
        /*
   Icon categories -
   accessories, award, beer, boat, book, car, clothes, coffee, computer, cosmetic, drink,
    electric, entertainment, fitness
   food, fruit, gift, grocery, home, hotel, icrecream, launcher, laundry, medical,
    noodle, oil, other, people,
   phone, pill, pizza, plane, restaurant, saving, sell, shopping, sweet, taxi, train, water, working
    */

        expenseCategories.add(Category("Food", R.drawable.ic_category_food, R.color.forest_green))
        expenseCategories.add(Category(
            "Entertainment",
            R.drawable.ic_category_entertainment, R.color.dark_slate_blue))

        expenseCategories.add(Category("Fuel", R.drawable.ic_category_fuel_pump, R.color.brown))
        expenseCategories.add(Category("Drink", R.drawable.ic_category_drink, R.color.dark_red))
        expenseCategories.add(Category("Pet", R.drawable.ic_category_paw, R.color.olive))
        expenseCategories.add(Category("Electric",
            R.drawable.ic_category_electricity,
            R.color.gold))
        expenseCategories.add(Category("Water", R.drawable.ic_category_water, R.color.dark_magenta))
        expenseCategories.add(Category("Home", R.drawable.ic_home, R.color.dim_gray))
        expenseCategories.add(Category("Shopping", R.drawable.ic_category_shopping, R.color.orchid))
        expenseCategories.add(Category("Beauty", R.drawable.ic_category_beauty, R.color.deep_pink))
        expenseCategories.add(Category("Taxi", R.drawable.ic_category_taxi, R.color.dark_khaki))
        expenseCategories.add(Category("Fruit", R.drawable.ic_apple_fruits, R.color.orange_red))
        expenseCategories.add(Category("Rent", R.drawable.ic_category_rent, R.color.yellow_green))
        expenseCategories.add(Category("Travel",
            R.drawable.ic_category_travel,
            R.color.rebecca_purple))
        expenseCategories.add(Category("Holiday", R.drawable.ic_category_holiday, R.color.olive))
        expenseCategories.add(Category("Games",
            R.drawable.ic_category_gamepad,
            R.color.dark_slate_blue))
        expenseCategories.add(Category("People",
            R.drawable.ic_category_person,
            R.color.yellow_green))
        expenseCategories.add(Category("Gym",
            R.drawable.ic_category_arm_dumbbell,
            R.color.dark_red))
        expenseCategories.add(Category("Party",
            R.drawable.ic_category_party,
            R.color.midnight_blue))

        expenseCategories.add(Category("Cleaning",
            R.drawable.ic_category_cleaning,
            R.color.dark_cyan))

        expenseCategories.add(Category("Medical", R.drawable.ic_category_medical, R.color.dark_red))
        expenseCategories.add(Category("Gift", R.drawable.ic_category_gift, R.color.yellow_green))
        expenseCategories.add(Category("Coffee",
            R.drawable.ic_category_birthday,
            R.color.saddle_brown))
        expenseCategories.add(Category("Work", R.drawable.ic_category_work, R.color.rebecca_purple))
        expenseCategories.add(Category("Milk", R.drawable.ic_category_milk, R.color.olive))
        expenseCategories.add(Category("Pizza",
            R.drawable.ic_category_pizza_food,
            R.color.orange_red))
        expenseCategories.add(Category(
            "Restaurant",
            R.drawable.ic_category_food_and_drink, R.color.dark_red))
        expenseCategories.add(Category("Car", R.drawable.ic_category_car, R.color.dark_cyan))
        expenseCategories.add(Category("Book", R.drawable.ic_category_book, R.color.midnight_blue))

        /* categroies.add(Category(15, "Clothes", R.drawable.))
         categroies.add(Category(23, "Accessory", R.drawable.))
        */
        expenseCategories.add(Category("Laundry",
            R.drawable.ic_category_laundry_hand,
            R.color.olive))

    }

}