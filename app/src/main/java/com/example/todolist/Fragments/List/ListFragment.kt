
package com.example.todolist.Fragments.List

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.models.TodoData
import com.example.todolist.data.viewModel.TodoViewModel
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment(),SearchView.OnQueryTextListener {


    private val mTodoViewModel : TodoViewModel by viewModels()
   private  val adapter : ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =   inflater.inflate(R.layout.fragment_list, container, false)

               view.floatingActionButton.setOnClickListener {
                   findNavController().navigate(R.id.action_listFragment_to_addFragment)
               }

         val recyclerView = view.listRecyclerView

        recyclerView.adapter  = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
            (requireActivity())
        recyclerView.itemAnimator = LandingAnimator().apply {
            addDuration = 400

        }


        mTodoViewModel.getAllData.observe(viewLifecycleOwner, Observer {
            data -> adapter.setData(data)
        })

        swipeToDeleteCallBack(recyclerView)
        setHasOptionsMenu(true)



             return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.menu_deleteAll -> deleteAllItem()
            R.id.menu_priorityHigh -> mTodoViewModel.sortByHighPriority.observe(viewLifecycleOwner,
                Observer {
                    adapter.setData(it)
                })
            R.id.menu_priorityLow -> mTodoViewModel.sortByLowPriority.observe(viewLifecycleOwner,
                Observer {
                    adapter.setData(it)
                })

        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("YES"){
                _,_ -> mTodoViewModel.deleteAll()

            Snackbar.make(requireView(),"Successfully Deleted",Snackbar.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete Everything")
        builder.setMessage("Are you sure you want to delete Everything?")
        builder.create().show()
    }

    private fun swipeToDeleteCallBack(recyclerView: RecyclerView){
        val swipeToDelete = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.dataList[viewHolder.adapterPosition]
                mTodoViewModel.delete(itemToDelete)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
               //Restore
                restoreDeleteData(viewHolder.itemView,itemToDelete,viewHolder.adapterPosition)

            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeleteData(
        itemView: View,
        itemToDelete: TodoData,
        adapterPosition: Int
    ) {

        val snackbar = Snackbar.make(itemView,"Deleted '${itemToDelete.title}'",Snackbar.LENGTH_LONG)

        snackbar.setAction("Undo"){
            mTodoViewModel.insert(itemToDelete)
            adapter.notifyItemChanged(adapterPosition)
        }
        snackbar.show()

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
       if (query != null){
           searchDatabase(query)

       }
        return true
    }



    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            searchDatabase(query)

        }
        return true
    }


    private fun searchDatabase(query: String) {

        var searchQuery:String = query
        searchQuery = "%$searchQuery%"

        mTodoViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, Observer {
            list -> list.let {
                adapter.setData(it)
        }
        })


    }
}