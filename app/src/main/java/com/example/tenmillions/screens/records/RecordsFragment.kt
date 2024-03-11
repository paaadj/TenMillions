package com.example.tenmillions.screens.records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tenmillions.R
import com.example.tenmillions.database.AppDatabase
import com.example.tenmillions.databinding.RecordsFragmentBinding
import kotlin.requireNotNull


class RecordsFragment : Fragment() {
    private lateinit var binding: RecordsFragmentBinding
    private lateinit var viewModel: RecordsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.records_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).userDao()
        val viewModelFactory = RecordsViewModelFactory(dao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[RecordsViewModel::class.java]

        viewModel.usersLoaded.observe(viewLifecycleOwner, Observer { status ->
            if (status) {
                for (user in viewModel.usersList) {
                    val tableRow = TableRow(application)
                    tableRow.layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    )
                    val usernameTextView = TextView(application)
                    usernameTextView.text = user.username
                    usernameTextView.layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT, 1f
                    )
                    usernameTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    val scoreTextView = TextView(application)
                    scoreTextView.text = user.score.toString()
                    scoreTextView.layoutParams = TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT, 1f
                    )
                    scoreTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    tableRow.addView(usernameTextView)
                    tableRow.addView(scoreTextView)
                    binding.tableLayout.addView(tableRow)
                }
            }
        })

        binding.backButton.setOnClickListener {
            findNavController().navigate(RecordsFragmentDirections.actionRecordsFragmentToTitleFragment())
        }
        return binding.root
    }
}