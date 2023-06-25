package com.example.todoapp.ui.additem

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.TodoApp
import com.example.todoapp.data.model.Priority
import com.example.todoapp.databinding.FragmentAddItemBinding
import com.example.todoapp.util.DateParser
import com.example.todoapp.util.PriorityMapper
import com.example.todoapp.viewmodel.additem.AddTodoItemViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class AddTodoItemFragment : Fragment(R.layout.fragment_add_item) {

    private val addTodoItemViewModel: AddTodoItemViewModel by lazy {
        (requireActivity().application as TodoApp).addTodoItemViewModel
    }

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
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
            addTodoItemViewModel.updateDescription(it?.toString() ?: "")
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addTodoItemViewModel.todoItem.collectLatest { todoItem ->
                    binding.importanceValue.text = PriorityMapper.mapToString(todoItem.priority)
                    binding.taskDescription.setTextKeepState(todoItem.text)
                    binding.deadlineDate.text = DateParser.parse(todoItem.deadline)
                }
            }
        }

        addTodoItemViewModel.createOrFind(arguments?.getString(ARG_TODO_ITEM_ID))
        binding.switchButton.isChecked = addTodoItemViewModel.todoItem.value.deadline != null
    }

    private fun highlightHighImportanceElement(popupMenu: PopupMenu) {
        val highElement: MenuItem = popupMenu.menu.findItem(R.id.menu_item_high_importance)
        val s = SpannableString(getString(R.string.high_priority))
        s.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red)),
            0, s.length, 0
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
                        addTodoItemViewModel.updatePriority(Priority.COMMON)
                        true
                    }

                    R.id.menu_item_low_importance -> {
                        addTodoItemViewModel.updatePriority(Priority.LOW)
                        true
                    }

                    R.id.menu_item_high_importance -> {
                        addTodoItemViewModel.updatePriority(Priority.HIGH)
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
            addTodoItemViewModel.saveTodoItem()
            findNavController().navigate(R.id.action_AddItemFragment_to_TodoItemsFragment)
        }

        binding.deleteTaskButton.setOnClickListener {
            addTodoItemViewModel.removeTodoItem()
            findNavController().navigate(R.id.action_AddItemFragment_to_TodoItemsFragment)
        }

        binding.closeTaskButton.setOnClickListener {
            findNavController().navigate(R.id.action_AddItemFragment_to_TodoItemsFragment)
        }
    }

    private fun setupDatePicker() {
        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireContext(), R.style.DatePickerStyle,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        val selectedCalendar = Calendar.getInstance()
                        selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                        val selectedDate = selectedCalendar.time
                        addTodoItemViewModel.updateDeadline(selectedDate)
                    }, year, month, day
                )

                datePickerDialog.setOnCancelListener {
                    binding.switchButton.isChecked = false
                }

                datePickerDialog.show()
            } else {
                addTodoItemViewModel.updateDeadline(null)
            }
        }
    }

    companion object {
        const val ARG_TODO_ITEM_ID = "todoItemId"
    }
}
