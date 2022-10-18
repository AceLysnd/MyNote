package com.ace.mynote.ui.noteform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ace.mynote.R
import com.ace.mynote.data.local.database.note.NoteEntity
import com.ace.mynote.databinding.FragmentNoteFormBinding
import com.ace.mynote.di.ServiceLocator
import com.ace.mynote.ui.homepage.HomePageFragment
import com.ace.mynote.utils.viewModelFactory
import com.ace.mynote.wrapper.Resource

class NoteFormFragment : Fragment() {

    private lateinit var binding: FragmentNoteFormBinding

    private val viewModel: NoteFormViewModel by viewModelFactory {
        NoteFormViewModel(ServiceLocator.provideServiceLocator(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNoteFormBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeData()
        getInitialData()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnSave.setOnClickListener {
            saveData()
        }
    }

    private fun bindDataToForm(data: NoteEntity?) {
        data?.let {
            binding.etNoteTitle.setText(data.noteTitle)
            binding.etNoteDescription.setText(data.noteDescription)
            binding.etNoteContent.setText((data.noteContent))
        }
    }

    private fun parseFormIntoEntity(): NoteEntity {
        return NoteEntity(
            noteTitle = binding.etNoteTitle.text.toString().trim(),
            noteDescription = binding.etNoteDescription.text.toString().trim(),
            noteContent = binding.etNoteContent.text.toString().trim(),
        ). apply {
            id = getNoteId()!!
        }
    }

    private fun validateForm(): Boolean {
        val noteTitle = binding.etNoteTitle.text.toString()
        val noteDescription = binding.etNoteDescription.text.toString()
        val noteContent = binding.etNoteContent.text.toString()
        var isFormValid = true
        if (noteTitle.isEmpty()) {
            isFormValid = false
            binding.tilNoteTitle.isErrorEnabled = true
            binding.tilNoteTitle.error = "Title is Empty"
        }
        if (noteDescription.isEmpty()) {
            isFormValid = false
            binding.tilNoteDescription.isErrorEnabled = true
            binding.tilNoteDescription.error = "Description is Empty"
        }
        if (noteContent.isEmpty()) {
            isFormValid = false
            binding.tilNoteContent.isErrorEnabled = true
            binding.tilNoteContent.error = "Description is Empty"
        } else {
            binding.tilNoteTitle.isErrorEnabled = false
            binding.tilNoteDescription.isErrorEnabled = false
            binding.tilNoteContent.isErrorEnabled = false
        }
        return isFormValid
    }

    private fun getNoteId(): Int? {
        return arguments?.getInt(HomePageFragment.EXTRA_ID)
    }

    private fun getInitialData() {
        getNoteId()?.let {
            viewModel.getNoteById(it)
        }
    }



    private fun saveData() {
        if (validateForm()) {
            if (getNoteId() != 0) {
                viewModel.updateNote(parseFormIntoEntity())
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_noteFormFragment_to_homePageFragment)
            } else  {
                viewModel.insertNewNote(parseFormIntoEntity())
                Toast.makeText(requireContext(), "Save Success", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_noteFormFragment_to_homePageFragment)
            }
        }
    }

    private fun observeData() {
        viewModel.detailDataResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> bindDataToForm(it.payload)
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "error while getting data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {}
            }
        }
        viewModel.updateResult.observe(viewLifecycleOwner) {

        }
        viewModel.insertResult.observe(viewLifecycleOwner) {

        }
    }
}