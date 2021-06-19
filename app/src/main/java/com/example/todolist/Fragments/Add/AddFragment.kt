package com.example.todolist.Fragments.Add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.data.ShareTodoViewModel
import com.example.todolist.data.models.Priority
import com.example.todolist.data.models.TodoData
import com.example.todolist.data.viewModel.TodoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private val TodoAddViewModel:TodoViewModel by viewModels()

    private val ShareTodoViewModel:ShareTodoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)


        setHasOptionsMenu(true)

        view.spinner_AddFragment.onItemSelectedListener = ShareTodoViewModel.listener

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add){
            insertDatatoDb()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun insertDatatoDb() {

        val mTitle  = title_et.text.toString()
        val mPriority = spinner_AddFragment.selectedItem.toString()
        val mDescription = description_et.text.toString()


        val validation = ShareTodoViewModel.verifyDataFromUser(mTitle,mDescription)
        if(validation){
            val newData = TodoData(
                0,
                mTitle,
                ShareTodoViewModel.parsePriority(mPriority),
                mDescription
            )

            TodoAddViewModel.insert(newData)
            Snackbar.make(requireView(),"Successfully Added!",Snackbar.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        }



    }



}