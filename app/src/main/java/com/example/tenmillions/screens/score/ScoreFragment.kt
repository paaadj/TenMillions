package com.example.tenmillions.screens.score

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tenmillions.R
import com.example.tenmillions.database.AppDatabase
import com.example.tenmillions.databinding.ScoreFragmentBinding

class ScoreFragment : Fragment() {

    private lateinit var binding: ScoreFragmentBinding
    private lateinit var viewModel: ScoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.score_fragment,
            container,
            false
        )

        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()

        val application = requireNotNull(this.activity).application
        val dao = AppDatabase.getInstance(application).userDao()
        val viewModelFactory = ScoreViewModelFactory(dao, application, scoreFragmentArgs.score)

        viewModel = ViewModelProvider(this, viewModelFactory)[ScoreViewModel::class.java]

        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            val text = resources.getString(R.string.score_text, newScore)
            binding.scoreText.text = text
            if (newScore == 0){
                binding.saveButton.isVisible = false
                binding.username.isVisible = false
            }
            else {
                binding.saveButton.isVisible = true
                binding.username.isVisible = true
            }
        })

        binding.saveButton.setOnClickListener {
            if (binding.username.text.trim().isEmpty()) {
                binding.username.setHintTextColor(Color.RED)
                val text = resources.getString(R.string.empty_username)
                binding.nicknameStatus.text = text
                binding.nicknameStatus.isVisible = true
            } else {
                viewModel.status(binding.username.text.trim().toString()) { stat ->
                    if (!stat) {
                        viewModel.save(binding.username.text.toString())
                        findNavController()
                            .navigate(ScoreFragmentDirections.actionScoreFragmentToTitleFragment())
                    } else {
                        val text = resources.getString(R.string.username_exists)
                        binding.nicknameStatus.text = text
                        binding.nicknameStatus.isVisible = true
                    }
                }
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToTitleFragment())
        }
        return binding.root
    }

}