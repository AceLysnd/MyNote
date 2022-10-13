package com.ace.mynote.presentation.ui.noteform

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ace.mynote.R
import com.ace.mynote.data.local.database.NoteDatabase
import com.ace.mynote.data.local.database.note.NoteEntity
import com.ace.mynote.databinding.FragmentNoteFormBinding
import com.ace.mynote.utils.viewModelFactory

class NoteFormFragment : Fragment() {

    private lateinit var binding: FragmentNoteFormBinding

    private val viewModel: NoteFormViewModel by viewModelFactory {
        NoteFormViewModel(NoteDatabase.getInstance(requireContext()).noteDao)
    }


//    val noteDao = NoteDatabase.getInstance(application).noteDao
//
//    val viewModelFactory = HomePageViewModelFactory(noteDao, application)
//
//    val note =
//        ViewModelProvider(
//            this, viewModelFactory).get(HomePageViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNoteFormBinding.inflate(layoutInflater, container, false)
        return binding.root
        val application = requireNotNull(this.activity).application
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setOnClickListener()
        getNoteId()
//        var noteId = requireActivity().intent.getIntExtra(ARG_NOTE_ID, UNSET_NOTE_ID)
    }

    private fun getNoteId(): Int? {
        return requireActivity().intent.getIntExtra(ARG_NOTE_ID, UNSET_NOTE_ID)
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
//            id = getNoteId()
        )
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

//    private fun getInitialData() {
//        if (isEditAction()) {
//            val noteId = getNoteId()
//            noteId?.let {
//                viewModel.getNoteById(it)
//            }
//        }
//    }

    private fun saveData() {
        if (validateForm()) {
            viewModel.insertNewNote(parseFormIntoEntity())
            Toast.makeText(requireContext(), "Save Success", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_noteFormFragment_to_homePageFragment)
        }
    }

    private fun observeData() {
        viewModel.detailDataResult.observe(viewLifecycleOwner) {
            bindDataToForm(it)
        }
        viewModel.updateResult.observe(viewLifecycleOwner) {

        }
        viewModel.insertResult.observe(viewLifecycleOwner) {

        }
    }
//
//    private fun isEditAction(): Boolean {
//        val noteId = getNoteId()
//        return noteId != null && noteId != UNSET_NOTE_ID
//    }


    companion object {
        private const val ARG_NOTE_ID = "ARG_NOTE_ID"
        private const val UNSET_NOTE_ID = -1

        @JvmStatic
        fun newInstance(context: Context, noteId: Int? = null): Intent =
            Intent(context, NoteFormFragment::class.java).apply {
                noteId?.let {
                    putExtra(ARG_NOTE_ID, it)
                }
            }
    }

}
