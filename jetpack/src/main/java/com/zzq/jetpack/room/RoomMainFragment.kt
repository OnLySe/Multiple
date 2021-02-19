package com.zzq.jetpack.room

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zzq.jetpack.R
import com.zzq.jetpack.base.BaseFragment
import com.zzq.jetpack.databinding.FragmentRoomMainBinding
import com.zzq.jetpack.room.data.AppDatabase
import com.zzq.jetpack.room.data.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class RoomMainFragment : BaseFragment() {

    private var index = 0L
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var rvMain: RecyclerView
    private var resetIndex = false
    private val dataSource = ArrayList<User>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_room_main
    }
    override fun initView(view: View) {

        val binding = FragmentRoomMainBinding.inflate(layoutInflater)

        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        val allUser = userDao.getAllUser()
        rvMain = binding.rvMain
        rvMain.layoutManager = LinearLayoutManager(requireContext())
        userListAdapter = UserListAdapter()
        rvMain.adapter = userListAdapter

        subscribeUi()

        binding.fabAdd.setOnClickListener {
            resetIndex = true
            index++
            val user = User(index, "user_fab $index", System.currentTimeMillis(),
                    "$index user_fab $index")
            if ((user.userId % 2).toInt() == 0) {
                user.address = user.name
            }
//            val currentList = userListAdapter.currentList
//            val size = currentList.size
//            currentList.add(size, user)
//            userListAdapter.notifyItemInserted(size)
            GlobalScope.launch {
                userDao.addUser(user)
            }
        }

        binding.fabAdd.setOnLongClickListener {
            if (dataSource.isNotEmpty()) {
                if (index >= 1) {
                    index--
                }
                GlobalScope.launch { userDao.deleteUser(dataSource.last()) }
                return@setOnLongClickListener true
            }
            return@setOnLongClickListener false
        }

        GlobalScope.launch {
            val list = userDao.getAllUser2()
            Log.e("tetetetete", "onCreate size: ${list.size}")
        }
    }

    private fun subscribeUi() {
        AppDatabase.getInstance(requireContext()).userDao().getAllUser().observe(this, {
            Log.e("tetetetete", "data change ${it.size}")
            userListAdapter.submitList(it)
            if (!resetIndex && it.isNotEmpty()) {
                index = it.last().userId
            }

            dataSource.clear()
            dataSource.addAll(it)
            if (it.size > 0) {
                rvMain.smoothScrollToPosition(it.size - 1)
            }
        })
    }

}
