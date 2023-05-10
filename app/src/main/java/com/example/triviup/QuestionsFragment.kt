package com.example.triviup

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.triviup.adapter.QuestionAdapter
import com.example.triviup.adapter.QuestionClickListener
import com.example.triviup.database.QuestionDatabase
import com.example.triviup.databinding.FragmentQuestionsBinding
import com.example.triviup.model.Category
import com.example.triviup.viewmodel.QuestionsViewModel
import com.example.triviup.viewmodel.QuestionsViewModelFactory
import com.example.triviup.database.QuestionDatabaseDao
import com.example.triviup.model.Question
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
    private var questionList = listOf<Question>()
    private var _binding: FragmentQuestionsBinding? = null;
    private val binding get() = _binding!!

    private lateinit var questionAdapter : QuestionAdapter

    companion object{
        var score = 0
        var questionNumber : Int = 1
        var enableButtons = true
        var correctAnswer = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentQuestionsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        category = QuestionsFragmentArgs.fromBundle(requireArguments()).category
        binding.categoryName.text = category.name
        binding.score.text = "score : ${score}"
        binding.questionNumber.text = "Question ${questionNumber}/10"
        val application = requireNotNull(this.activity).application
        questionDatabaseDao = QuestionDatabase.getInstance(application).questionDatabaseDao

        viewModelFactory = QuestionsViewModelFactory(category,  questionDatabaseDao, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(QuestionsViewModel::class.java)

        val recyclerView = binding.questionRv

        questionAdapter = QuestionAdapter(
            QuestionClickListener { answer ->
                viewModel.onQuestionItemClicked(answer, this)
            })
        recyclerView.adapter = questionAdapter

        viewModel.questionList.observe(viewLifecycleOwner) { questions ->
            questionList = questions
            getNextQuestion()
            questionAdapter.submitList(answersList)
        }
        return binding.root
    }

    fun getNextQuestion(){

        if (questionList.isNotEmpty()) {
            Log.d("aaaaaaaaaaaaaaaaaaaaaaaaaa", "nouvelle question : ${questionNumber}")
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

            val randomIndex = Random.nextInt(answersList.size)
            answersList.add(randomIndex,correctAnswer)
            Log.d("uuuuuuuuuuuuuuuuuuuuu", "${currentQuestion.correct_answer}")
            currentQuestion.question =
                Html.fromHtml(currentQuestion.question, Html.FROM_HTML_MODE_LEGACY)
                    .toString()
            binding.questionTextView.text = currentQuestion.question
            questionAdapter.submitList(answersList)
        } else {
            Log.d("aaaaaaaaaaaaaaaaaaaaaaaaaa", "pas de questions , ${questionList.size}")
            binding.questionTextView.text = "no question"
        }
    }

    fun waitNextQuestion(isCorrect : Boolean){
        if (isCorrect) {
            score+= 1
        }
        binding.score.text = "score : ${score}"
        enableButtons = false
        questionAdapter.notifyDataSetChanged()
        if (questionNumber > 10){
            Log.d("rrrrrrrrrrrr", "maximum questions")
            binding.buttonToResults.visibility = View.VISIBLE
            binding.buttonToResults.isEnabled = true
        }else {
            binding.buttonNextQuestion.visibility = View.VISIBLE
            binding.buttonNextQuestion.isEnabled = true
        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonPrevious.setOnClickListener {
            findNavController().navigate(QuestionsFragmentDirections.actionQuestionsFragmentToCategoryFragment())
        }
        binding.buttonToResults.setOnClickListener {
            findNavController().navigate(QuestionsFragmentDirections.actionQuestionsFragmentToResultsFragment())
        }
        binding.buttonNextQuestion.setOnClickListener {
            binding.buttonNextQuestion.visibility = View.GONE
            binding.buttonNextQuestion.isEnabled = false
            enableButtons = true
            binding.questionNumber.text = "Question ${questionNumber}/10"
            questionAdapter.notifyDataSetChanged()
            Log.d("ttttttttttttt", "clicked on next")
            getNextQuestion()
        }

    }

    override fun onDestroyView() {
        viewModel.deleteQuestions()
        super.onDestroyView()
    }


}