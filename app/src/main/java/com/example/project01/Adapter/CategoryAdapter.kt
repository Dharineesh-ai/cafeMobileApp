package com.example.project01.Adapter

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.project01.Activity.ItemsListActivity
import com.example.project01.Domain.CategoryModel
import com.example.project01.R
import com.example.project01.databinding.ViewholderCategoryBinding
import kotlin.jvm.java

class CategoryAdapter(val items: MutableList<CategoryModel>):
RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    private lateinit var context: Context
    private var selectedPosition= -1
    private var lastSelectedPosition= -1
    inner class ViewHolder(val binding: ViewholderCategoryBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.ViewHolder {
        context= parent.context
        val binding=ViewholderCategoryBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
       val item= items[position]
        holder.binding.titleCat.text= item.title

        holder.binding.root.setOnClickListener {
            lastSelectedPosition=selectedPosition
            selectedPosition= position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)

            Handler(Looper.getMainLooper()).postDelayed({
            val intent= Intent(context, ItemsListActivity::class.java).apply{
                putExtra("id",item.id.toString())
                putExtra("title",item.title)
            }
                ContextCompat.startActivity(context,intent,null)
            },500)
        }

        if(selectedPosition==position){
            holder.binding.titleCat.setBackgroundResource(R.drawable.brown_full_corner_bg)
            holder.binding.titleCat.setTextColor(context.resources.getColor(R.color.white))
        }else{
            holder.binding.titleCat.setBackgroundResource(R.drawable.white_full_corner_bg)
            holder.binding.titleCat.setTextColor(context.resources.getColor(R.color.darkbrown))
        }
    }

    override fun getItemCount(): Int=items.size

}