package com.example.todoapp.ui.edititem

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.model.Priority
import com.example.todoapp.databinding.FragmentEditItemBinding
import com.example.todoapp.di.component.EditTodoItemFragmentComponent
import com.example.todoapp.ui.main.MainActivity
import com.example.todoapp.util.DateParser
import com.example.todoapp.util.PriorityMapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class EditTodoItemFragment : Fragment(R.layout.fragment_edit_item) {

    private lateinit var component: EditTodoItemFragmentComponent

    private val editTodoItemViewModel: EditTodoItemViewModel by viewModels {
        (activity as MainActivity).activityComponent.viewModelFactory
    }

    private var _binding: FragmentEditItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = (activity as MainActivity).activityComponent.editTodoItemFragmentComponent
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewModel()
        setupPriorityMenu()
        setupDatePicker()
        setupButtons()
        setupDescription()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupDescription() {
        binding.taskDescription.addTextChangedListener {
            editTodoItemViewModel.updateDescription(it?.toString() ?: "")
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editTodoItemViewModel.todoItem.collectLatest { todoItem ->
                    binding.importanceValue.text = PriorityMapper.mapToString(todoItem.priority)
                    binding.taskDescription.setTextKeepState(todoItem.text)
                    binding.deadlineDate.text = DateParser.parse(todoItem.deadline)
                }
            }
        }

        editTodoItemViewModel.createOrFind(arguments?.getString(ARG_TODO_ITEM_ID))
        binding.switchButton.isChecked = editTodoItemViewModel.todoItem.value.deadline != null
    }

    private fun highlightHighImportanceElement(popupMenu: PopupMenu) {
        val highElement: MenuItem = popupMenu.menu.findItem(R.id.menu_item_high_importance)
        val s = SpannableString(getString(R.string.high_priority))
        s.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red)),
            0,
            s.length,
            0
        )
        highElement.title = s
    }

    private fun setupPriorityMenu() {
        binding.importanceValue.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), binding.importanceTitle)
            popupMenu.menuInflater.inflate(R.menu.importance_menu, popupMenu.menu)
            highlightHighImportanceElement(popupMenu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_item_no_importance -> {
                        editTodoItemViewModel.updatePriority(Priority.COMMON)
                        true
                    }

                    R.id.menu_item_low_importance -> {
                        editTodoItemViewModel.updatePriority(Priority.LOW)
                        true
                    }

                    R.id.menu_item_high_importance -> {
                        editTodoItemViewModel.updatePriority(Priority.HIGH)
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    private fun setupButtons() {
        binding.saveTaskButton.setOnClickListener {
            editTodoItemViewModel.saveTodoItem()
            findNavController().navigate(R.id.action_AddItemFragment_to_TodoItemsFragment)
        }

        binding.deleteTaskButton.setOnClickListener {
            editTodoItemViewModel.removeTodoItem()
            findNavController().navigate(R.id.action_AddItemFragment_to_TodoItemsFragment)
        }

        binding.closeTaskButton.setOnClickListener {
            findNavController().navigate(R.id.action_AddItemFragment_to_TodoItemsFragment)
        }
    }

    private fun openDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DatePickerStyle,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                val selectedDate = selectedCalendar.time
                editTodoItemViewModel.updateDeadline(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.setOnCancelListener {
            binding.switchButton.isChecked = false
        }

        datePickerDialog.show()
    }

    private fun setupDatePicker() {
        binding.deadlineDate.setOnClickListener {
            openDatePicker()
        }

        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                openDatePicker()
            } else {
                editTodoItemViewModel.updateDeadline(null)
            }
        }
    }

    companion object {
        const val ARG_TODO_ITEM_ID = "todoItemId"
    }
}
