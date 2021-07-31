package com.developer.vijay.infiniterecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.vijay.infiniterecyclerview.databinding.ItemUserBinding

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private var userList = listOf<String>()

    class UserViewHolder(val mBinding: ItemUserBinding) : RecyclerView.ViewHolder(mBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.apply {
            mBinding.tvUsername.text = userList[position]
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(userList: List<String>) {
        this.userList = userList
        notifyDataSetChanged()
    }

}