package com.ace.mynote.presentation.ui.homepage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ace.mynote.R
import com.ace.mynote.data.local.database.NoteDatabase
import com.ace.mynote.data.local.database.note.NoteEntity
import com.ace.mynote.databinding.FragmentHomePageBinding
import com.ace.mynote.presentation.ui.homepage.adapter.HomePageAdapter
import com.ace.mynote.presentation.ui.homepage.adapter.NoteItemClickListener
import com.ace.mynote.presentation.ui.loginaccount.LoginAccountFragment
import com.ace.mynote.presentation.ui.loginaccount.LoginAccountFragment.Companion.USERNAME
import com.ace.mynote.utils.viewModelFactory

class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private val viewModel: HomePageViewModel by viewModelFactory {
        HomePageViewModel(
            NoteDatabase.getInstance(requireContext()).noteDao,
            requireNotNull(this.activity).application,
        )
    }
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomePageBinding.inflate(
            layoutInflater, container, false
        )
        return binding.root
    }



    private val adapter: HomePageAdapter by lazy {
        HomePageAdapter(object : NoteItemClickListener {
            override fun onItemClicked(item: NoteEntity, position: Int) {
                openFormFragment(position)
                viewModel.editNote(item)
            }

            override fun onDeleteMenuClicked(item: NoteEntity) {
                viewModel.deleteNote(item)
                findNavController().navigate(R.id.action_homePageFragment_self)
            }

            override fun onEditMenuClicked(item: NoteEntity, position: Int) {
                openFormFragment(position)
                viewModel.editNote(item)
            }
        })
    }

    fun openFormFragment(noteId: Int) {
        val bundle = Bundle()
        bundle.putInt(EXTRA_ID, noteId)

        findNavController().navigate(R.id.action_homePageFragment_to_noteFormFragment, bundle)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(
            LoginAccountFragment.LOGIN_SHARED_PREF,
            Context.MODE_PRIVATE
        )

        binding.homePageViewModel = viewModel
        viewModel.setUsername(getUsername())

        setClickListeners()
        observeData()
        initList()
    }

    private fun getUsername(): String {
        return sharedPreferences.getString(USERNAME,"")!!
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun setClickListeners() {
        binding.fabAddData.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(EXTRA_ID, 0)
            findNavController().navigate(R.id.action_homePageFragment_to_noteFormFragment, bundle)
        }
    }

    private fun initList() {
        binding.rvNoteList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HomePageFragment.adapter
        }
    }

    private fun observeData() {
        viewModel.getNoteList().observe(viewLifecycleOwner) {
            bindDataToAdapter(it)
        }
    }

    private fun getData() {
        viewModel.getNoteList()
    }


    private fun bindDataToAdapter(data: List<NoteEntity>?) {
        if (data.isNullOrEmpty()) {
            adapter.clearItems()
            setErrorState(true, "There is no note")
        } else {
            adapter.setItems(data)
        }
    }

    private fun setErrorState(isError: Boolean, errorMsg: String = "") {
        binding.tvError.text = errorMsg
        binding.tvError.isVisible = isError
    }

    companion object {
        var EXTRA_ID = "extra_id"
    }
}



