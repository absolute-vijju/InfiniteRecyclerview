package com.developer.vijay.infiniterecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.vijay.infiniterecyclerview.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val usersAdapter by lazy {
        UsersAdapter()
    }
    private val userList = arrayListOf<String>()
    private var isScrolling = false
    private var currentItems = 0
    private var totalItems = 0
    private var scrolledItems = 0
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var startingCount = 0
    private var endingCount = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        linearLayoutManager = LinearLayoutManager(applicationContext)

        mBinding.rvUsers.apply {
            layoutManager = linearLayoutManager
            adapter = usersAdapter
        }

        mBinding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                linearLayoutManager.apply {
                    currentItems = childCount
                    totalItems = itemCount
                    scrolledItems = findFirstVisibleItemPosition()

                    if (isScrolling && (currentItems + scrolledItems == totalItems)) {
                        isScrolling = false
                        addUsersWithDelay()
                    }
                }
            }
        })

        addUsers()
    }

    private fun addUsers() {
        for (i in startingCount until endingCount) {
            userList.add("user: ${i + 1}")
            if (i == endingCount - 1) {
                startingCount = i + 1
                endingCount += i + 1
            }
        }
        usersAdapter.setData(userList)
    }

    private fun addUsersWithDelay() {
        mBinding.pbLoading.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            for (i in startingCount until endingCount) {
                userList.add("user: ${i + 1}")
                if (i == endingCount - 1) {
                    startingCount = i + 1
                    endingCount += i + 1
                }
            }
            withContext(Dispatchers.Main) {
                usersAdapter.setData(userList)
                mBinding.pbLoading.visibility = View.GONE
            }
        }
    }

}