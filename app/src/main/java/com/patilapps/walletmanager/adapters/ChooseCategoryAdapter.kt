package com.patilapps.walletmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.patilapps.walletmanager.databinding.ListItemCategoryBinding
import com.patilapps.walletmanager.domain.Category

class ChooseCategoryAdapter(val selectedCategory: (Category) -> Unit) :
    RecyclerView.Adapter<ChooseCategoryAdapter.CategoryViewHolder>() {
    var categories = listOf<Category>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class CategoryViewHolder(
        val binding: ListItemCategoryBinding,
        val selectedCategory: (Category) -> Unit,
        val context: Context,
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun create(
                parent: ViewGroup,
                selectedCategory: (Category) -> Unit,
            ): CategoryViewHolder {
                val binding = ListItemCategoryBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false)
                return CategoryViewHolder(binding, selectedCategory, parent.context)
            }
        }

        fun bind(category: Category) {
                  binding.category = category
                  binding.executePendingBindings()
            //  binding.imageViewCategory.background.setTint(category.colorId)
         /*   binding.tvCategory.text = category.name
            binding.imageViewCategory.setImageDrawable(AppCompatResources.getDrawable(context,
                category.iconId))

            binding.imageViewCategory.drawable.setTint(ResourcesCompat.getColor(context.resources,
                category.colorId,
                context.resources.newTheme()))*/

            itemView.setOnClickListener { view ->
                selectedCategory(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.create(parent, selectedCategory)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = categories.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int = categories.size
}
