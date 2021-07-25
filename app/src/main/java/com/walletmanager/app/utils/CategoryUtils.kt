package com.walletmanager.app.utils

import com.walletmanager.app.R
import com.walletmanager.app.domain.Category

object CategoryUtils {

    val expenseCategories = mutableListOf<String>()

    val incomeCategories = mutableListOf<String>()

    val categroies = mutableListOf<Category>()

    init {
        populateExpenseCategories()
        populateIncomeCategories()
    }

    private fun populateIncomeCategories() {
        incomeCategories.add("Salary")
        incomeCategories.add("Business")
        incomeCategories.add("Interest")
        incomeCategories.add("Salary")
        incomeCategories.add("Salary")
        incomeCategories.add("Salary")
        incomeCategories.add("Salary")

        /*
   Icon categories -
   accessories, award, beer, boat, book, car, clothes, coffee, computer, cosmetic, drink, electric, entertainment, fitness
   food, fruit, gift, grocery, home, hotel, icrecream, launcher, laundry, medical, noodle, oil, other, people,
   phone, pill, pizza, plane, restaurant, saving, sell, shopping, sweet, taxi, train, water, working
    */

        categroies.add(Category(1, "Food", R.drawable.ic_food))
        categroies.add(Category(2, "Drink", R.drawable.ic_drink))
        categroies.add(Category(3, "Fuel", R.drawable.ic_oil))
        categroies.add(Category(4, "Travel", R.drawable.ic_train))
        categroies.add(Category(5, "Medical", R.drawable.ic_medical))
        categroies.add(Category(6, "Gift", R.drawable.ic_gift))
        categroies.add(Category(7, "Coffee", R.drawable.ic_coffe))
        categroies.add(Category(8, "Work", R.drawable.ic_working))
        categroies.add(Category(9, "Sweet", R.drawable.ic_sweet))
        categroies.add(Category(10, "People", R.drawable.ic_people))
        categroies.add(Category(11, "Taxi", R.drawable.ic_taxi))
        categroies.add(Category(12, "Pizza", R.drawable.ic_pizza))
        categroies.add(Category(13, "Restaurant", R.drawable.ic_restaurant))
        categroies.add(Category(14, "Car", R.drawable.ic_car))
        categroies.add(Category(15, "Clothes", R.drawable.ic_clothes))
        categroies.add(Category(16, "Electric", R.drawable.ic_electric))
        categroies.add(Category(17, "Water", R.drawable.ic_water))
        categroies.add(Category(18, "Shopping", R.drawable.ic_shopping))
        categroies.add(Category(19, "Entertainment", R.drawable.ic_entertainmmment))
        categroies.add(Category(20, "Gym", R.drawable.ic_fitness))
        categroies.add(Category(21, "Hotel", R.drawable.ic_hotel))
        categroies.add(Category(22, "Book", R.drawable.ic_book))
        categroies.add(Category(23, "Accessory", R.drawable.ic_accessory))
        categroies.add(Category(24, "Laundry", R.drawable.ic_laundry))
        categroies.add(Category(25, "Fruit", R.drawable.ic_fruit))
        categroies.add(Category(26, "Home", R.drawable.ic_home_black))
        categroies.add(Category(27, "Plane", R.drawable.ic_plane))
    }

    fun populateExpenseCategories(){
        expenseCategories.add("Food")
        expenseCategories.add("Drink")
        expenseCategories.add("Fuel")
        expenseCategories.add("Travel")
        expenseCategories.add("Recharge")
        expenseCategories.add("Bills")
    }


}