package com.pankaj.flipkart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_question_layout.*

class QuestionFragment : Fragment() {

    private val viewModel: AppViewModel by lazy {
        ViewModelProvider(
            this,
            AppViewModel.AppViewModelFactory(Instance.getAppRepository())
        )
            .get(AppViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initObserver()
        btn_submit.setOnClickListener {
            checkAnswer()
        }
    }

    private fun checkAnswer() {
        viewModel.checkForNextRoundAndUpdateDifficulty(et_answer.text.toString())
    }

    private fun initObserver() {
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            viewModel.getQuestionBasedOnDifficulty()
        })
        viewModel.quesData.observe(viewLifecycleOwner, Observer {
            viewModel.startTimer()
            Glide.with(requireContext()).load(it.imageUrl).into(iv_image)
            Toast.makeText(requireContext(), "Difficulty - ${it.difficulty} , QuesId - ${it.id}", Toast.LENGTH_SHORT).show()
        })
        viewModel.endRound.observe(viewLifecycleOwner, Observer {
            tv_score.text = "Total Score $it"
            btn_submit.isEnabled = false
            Toast.makeText(requireContext(), "Total Score $it", Toast.LENGTH_SHORT).show()
        })
        viewModel.elapsedTime.observe(viewLifecycleOwner, Observer {
            if (it < 0L) {
                checkAnswer()
                return@Observer
            }
            tv_timer.text = "$it"
        })
        viewModel.getList()
    }

    override fun onDestroyView() {
        viewModel.cancelTimer()
        super.onDestroyView()
    }
}