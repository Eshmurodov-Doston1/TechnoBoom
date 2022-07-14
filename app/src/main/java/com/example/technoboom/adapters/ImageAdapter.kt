package com.example.technoboom.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.technoboom.dataBase.entity.ImageEntity
import com.example.technoboom.databinding.RvRemoteItemBinding

class ImageAdapter(var onItemClickListener: OnItemClickListener):ListAdapter<ImageEntity,ImageAdapter.Vh>(MyDiffUtil()) {
    inner class Vh(var rvRemoteItemBinding: RvRemoteItemBinding):RecyclerView.ViewHolder(rvRemoteItemBinding.root){
        fun onBind(imageEntity:ImageEntity,position: Int){

            rvRemoteItemBinding.image.setImageURI(Uri.parse(imageEntity.imagePath))
            rvRemoteItemBinding.cardImage.setOnClickListener {
                onItemClickListener.onItemClick(imageEntity,position)
            }
            rvRemoteItemBinding.cardDelete.setOnClickListener {
                onItemClickListener.onItemClickDelete(imageEntity,position)
            }

            rvRemoteItemBinding.cardUpdate.setOnClickListener {
                onItemClickListener.onItemClickUpdate(imageEntity,position)
            }
        }
    }

    class MyDiffUtil :DiffUtil.ItemCallback<ImageEntity>(){
        override fun areItemsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
            return oldItem.equals(newItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvRemoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position),position)
    }
    interface OnItemClickListener{
        fun onItemClick(uri: ImageEntity,position: Int)
        fun onItemClickDelete(uri: ImageEntity,position: Int)
        fun onItemClickUpdate(uri: ImageEntity,position: Int)
    }
}