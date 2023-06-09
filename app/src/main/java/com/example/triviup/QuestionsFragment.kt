package com.example.triviup

import android.annotation.SuppressLint
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.view.*

import android.media.MediaPlayer
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.caverock.androidsvg.SVG
import com.example.triviup.adapter.QuestionAdapter
import com.example.triviup.adapter.QuestionClickListener
import com.example.triviup.database.QuestionDatabase
import com.example.triviup.databinding.FragmentQuestionsBinding
import com.example.triviup.model.Category
import com.example.triviup.viewmodel.QuestionsViewModel
import com.example.triviup.viewmodel.QuestionsViewModelFactory
import com.example.triviup.database.QuestionDatabaseDao
import com.example.triviup.model.Question
import com.example.triviup.network.DataFetchStatus
import timber.log.Timber
import kotlin.random.Random


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class QuestionsFragment : Fragment() {

    private lateinit var viewModel: QuestionsViewModel
    private lateinit var viewModelFactory: QuestionsViewModelFactory

    var currentQuestion: Question = Question(0,"","","","","",listOf())
    private lateinit var questionDatabaseDao: QuestionDatabaseDao
    private var answersList = mutableListOf<String>()
    private lateinit var category : Category
    private lateinit var timer : CountDownTimer
    private var remainingTime : Int = 0
    private var questionList = listOf<Question>()
    private var _binding: FragmentQuestionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var difficulty : String
    private lateinit var questionAdapter : QuestionAdapter
    private lateinit var mediaPlayer : MediaPlayer

    companion object{
        var score = 0
        var questionNumber : Int = 1
        var enableButtons = true
        var correctAnswer = ""
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.count_down_audio)
        // Inflate the layout for this fragment
        _binding = FragmentQuestionsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val svg = SVG.getFromResource(resources, R.raw.hourglass)
        val drawable = PictureDrawable(svg.renderToPicture())
        binding.timerImageview.setImageDrawable(drawable)


        difficulty = QuestionsFragmentArgs.fromBundle(requireArguments()).difficulty.toString()

        category = QuestionsFragmentArgs.fromBundle(requireArguments()).category
        binding.categoryName.text = category.name
        binding.score.text = "score : ${score}"
        binding.questionNumber.text = "Question ${questionNumber}/10"
        val application = requireNotNull(this.activity).application
        questionDatabaseDao = QuestionDatabase.getInstance(application).questionDatabaseDao

        viewModelFactory = QuestionsViewModelFactory(category, difficulty,  questionDatabaseDao, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(QuestionsViewModel::class.java)

        val recyclerView = binding.questionRv

        questionAdapter = QuestionAdapter(
            QuestionClickListener { answer ->
                viewModel.onQuestionItemClicked(answer, this)
            })
        recyclerView.adapter = questionAdapter
        enableButtons = true
        viewModel.questionList.observe(viewLifecycleOwner) { questions ->
            questionList = questions
            timer = getNextQuestion()

            questionAdapter.submitList(answersList)
        }

        viewModel.dataFetchStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (status) {
                    DataFetchStatus.LOADING -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.loading_animation)
                    }
                    DataFetchStatus.ERROR -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                    }
                    DataFetchStatus.DONE -> {
                        timer.start()
                        binding.statusImage.visibility = View.GONE
                    }
                }
            }
        }


        return binding.root
    }

    private fun getNextQuestion() : CountDownTimer{
        val timer = object: CountDownTimer(10000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                // Update UI with the remaining time
                remainingTime= (millisUntilFinished / 1000).toInt()
                binding.timerTextview.text = "${remainingTime}s"
            }

            override fun onFinish() {
                waitNextQuestion(false)
            }
        }
        if (questionList.isNotEmpty()) {
            mediaPlayer.start()
            currentQuestion = questionList[questionNumber-1]
            questionNumber += 1

            //get the answers
            answersList = mutableListOf()
            currentQuestion.incorrect_answers.forEach { answer ->
                answersList.add(Html.fromHtml(answer, Html.FROM_HTML_MODE_LEGACY)
                    .toString())
            }
            correctAnswer = Html.fromHtml(currentQuestion.correct_answer, Html.FROM_HTML_MODE_LEGACY)
                .toString()

            val randomIndex = Random.nextInt(answersList.size + 1)
            answersList.add(randomIndex,correctAnswer)
            currentQuestion.question =
                Html.fromHtml(currentQuestion.question, Html.FROM_HTML_MODE_LEGACY)
                    .toString()
            binding.questionTextView.text = currentQuestion.question
            questionAdapter.submitList(answersList)
        } else {
            Timber.tag("getQuestionsError").d("pas de questions , %s", questionList.size)
            binding.questionTextView.text = "Please turn on Internet connection"
        }
        return timer
    }

    @SuppressLint("SetTextI18n")
    fun waitNextQuestion(isCorrect : Boolean){
        if (isCorrect) {
            score+= 1*remainingTime
            MediaPlayer.create(context, R.raw.correct).start();
        }else{
            MediaPlayer.create(context, R.raw.wrong).start();
        }
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(context, R.raw.count_down_audio);
        }
        timer.cancel()
        binding.score.text = "Score : ${score}"
        enableButtons = false
        questionAdapter.notifyDataSetChanged()
        if (questionNumber > 10){
            binding.buttonToResults.visibility = View.VISIBLE
            binding.buttonToResults.isEnabled = true
        }else {
            binding.buttonNextQuestion.visibility = View.VISIBLE
            binding.buttonNextQuestion.isEnabled = true
        }


    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonToResults.setOnClickListener {
            if(mediaPlayer != null) {
                mediaPlayer.stop();
            }
            viewModel.deleteQuestions()
            questionNumber = 1
            timer.cancel()
            findNavController().navigate(QuestionsFragmentDirections.actionQuestionsFragmentToResultFragment())
        }
        binding.buttonNextQuestion.setOnClickListener {
            binding.buttonNextQuestion.visibility = View.GONE
            binding.buttonNextQuestion.isEnabled = false
            enableButtons = true
            binding.questionNumber.text = "Question ${questionNumber}/10"
            questionAdapter.notifyDataSetChanged()
            timer = getNextQuestion()
            timer.start()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                timer.cancel()
                viewModel.deleteQuestions()
                score = 0
                questionNumber = 1
                findNavController().navigate(QuestionsFragmentDirections.actionQuestionsFragmentToMenuFragment())
            }
        }
        callback.isEnabled = true
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


    }

    override fun onDestroyView() {
        viewModel.deleteQuestions()
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroyView()
    }


}