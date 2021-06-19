package com.example.todolist.Fragments.Update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist.R
import com.example.todolist.data.ShareTodoViewModel
import com.example.todolist.data.models.Priority
import com.example.todolist.data.models.TodoData
import com.example.todolist.data.viewModel.TodoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

private val args by navArgs<UpdateFragmentArgs>()

    private val mShareViewModel : ShareTodoViewModel by viewModels()
   private val mTodoViewModel : TodoViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_update, container, false)

        view.update_title_et.setText(args.currentItem.title)
        view.description_et.setText(args.currentItem.description)
        view.spinner_UpdateFragment.setSelection(mShareViewModel.parsePriority1(args.currentItem.priority))
        view.spinner_UpdateFragment.onItemSelectedListener = mShareViewModel.listener
        setHasOptionsMenu(true)

        return view


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> deleteItem()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = update_title_et.text.toString()
        val description = description_et.text.toString()
        val getPriority = spinner_UpdateFragment.selectedItem.toString()

        val validation = mShareViewModel.verifyDataFromUser(title,description)
        if (validation){
            val updateItem = TodoData(
                args.currentItem.id,
                title,
                mShareViewModel.parsePriority(getPriority),
                description
            )

            mTodoViewModel.update(updateItem)
            Toast.makeText(requireContext(),"SuccessFully Updated!!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Updated the Item Plzzz",Toast.LENGTH_SHORT).show()

        }
    }

    private fun deleteItem(){

        val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("YES"){
                _,_ -> mTodoViewModel.delete(args.currentItem)
                Snackbar.make(requireView(),"SuccessFully Deleted: ${args.currentItem.title}",Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("Are you sure you want to delete '${args.currentItem.title}'?")
        builder.create().show()



    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }

}