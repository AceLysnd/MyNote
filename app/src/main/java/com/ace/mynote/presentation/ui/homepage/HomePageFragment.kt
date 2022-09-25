package com.ace.mynote.presentation.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ace.mynote.R
import com.ace.mynote.data.local.database.NoteDatabase
import com.ace.mynote.data.local.database.entity.NoteEntity
import com.ace.mynote.databinding.FragmentHomePageBinding
import com.ace.mynote.presentation.ui.homepage.adapter.HomePageAdapter
import com.ace.mynote.presentation.ui.homepage.adapter.NoteItemClickListener
import com.ace.mynote.utils.viewModelFactory

class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private val viewModel: HomePageViewModel by viewModelFactory {
        HomePageViewModel(NoteDatabase.getInstance(requireContext()).noteDao,
            requireNotNull(this.activity).application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentHomePageBinding.inflate(
            layoutInflater, container, false)


        val application = requireNotNull(this.activity).application

        val noteDao = NoteDatabase.getInstance(application).noteDao

        val viewModelFactory = HomePageViewModelFactory(noteDao, application)

        val homePageViewModel =
                ViewModelProvider(
                    this, viewModelFactory).get(HomePageViewModel::class.java)
        return binding.root

        binding.setLifecycleOwner (this)
        binding.homePageViewModel = homePageViewModel

    }
    private val adapter: HomePageAdapter by lazy {
        HomePageAdapter(object : NoteItemClickListener {
            override fun onItemClicked(item: NoteEntity) {

            }

            override fun onDeleteMenuClicked(item: NoteEntity) {
                TODO("Not yet implemented")
            }

            override fun onEditMenuClicked(item: NoteEntity) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        initList()
        observeData()
    }

    private fun setClickListeners(){
        binding.fabAddData.setOnClickListener{
            findNavController().navigate(R.id.action_homePageFragment_to_noteFormFragment)
        }
    }

    private fun initList() {
        binding.rvNoteList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@HomePageFragment.adapter
        }
    }

    private fun observeData() {
        viewModel.noteListResult.observe(viewLifecycleOwner) {
            bindDataToAdapter(it)
        }
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
}
