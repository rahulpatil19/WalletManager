package com.walletmanager.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.walletmanager.app.R
import com.walletmanager.app.domain.Category

class CategoryAdapter(val selectedCategory: (Category) -> Unit): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    var categories = listOf<Category>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class CategoryViewHolder(itemView: View, val selectedCategory: (Category) -> Unit, val context: Context): RecyclerView.ViewHolder(itemView){
        val tvCategory : TextView = itemView.findViewById(R.id.tv_category)
        val imageView: ImageView = itemView.findViewById(R.id.image_view_category)

        fun bind(category: Category){
            tvCategory.text = category.name
            imageView.setImageDrawable(AppCompatResources.getDrawable(context, category.icon))
            itemView.setOnClickListener { view->
                selectedCategory(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_category, parent, false)
        return CategoryViewHolder(view, selectedCategory, parent.context)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = categories.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int = categories.size
}