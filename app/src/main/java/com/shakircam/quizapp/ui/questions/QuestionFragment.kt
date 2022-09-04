package com.shakircam.quizapp.ui.questions


import android.content.res.ColorStateList
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.shakircam.quizapp.R
import com.shakircam.quizapp.databinding.FragmentQuestionBinding
import com.shakircam.quizapp.model.Question
import com.shakircam.quizapp.ui.home.HomeFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class QuestionFragment : Fragment() {

    private var _binding : FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    val question = mutableListOf<Question>()
    var questionCounter = 0
    var point = 0
    var result = 0
    private lateinit var timer : CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)

       // addSampleQuestion()
        addSportsQuestion()


        binding.optionOneTv.setOnClickListener {
                result=1
                binding.optionOneTv.apply {
                    if (questionCounter<question.size){
                        chipStrokeWidth = 10f
                        chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context,
                            R.color.purple_200
                        ))
                    }
                }
                binding.optionTwoTv.apply {
                    chipStrokeWidth = 4f
                    chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
                }
                binding.optionThreeTv.apply {
                    chipStrokeWidth = 10f
                    chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
                }
                binding.optionFourTv.apply {
                    chipStrokeWidth = 10f
                    chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
                }

        }

        binding.optionTwoTv.setOnClickListener {
                result=2
                binding.optionTwoTv.apply {
                    if (questionCounter<question.size){
                        chipStrokeWidth = 10f
                        chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context,
                            R.color.purple_200
                        ))
                    }
                }
                binding.optionOneTv.apply {
                    chipStrokeWidth = 4f
                    chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
                }
                binding.optionThreeTv.apply {
                    chipStrokeWidth = 4f
                    chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
                }
                binding.optionFourTv.apply {
                    chipStrokeWidth = 4f
                    chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
                }
        }

        binding.optionThreeTv.setOnClickListener {
            result=3
            binding.optionThreeTv.apply {
                if (questionCounter<question.size) {
                    chipStrokeWidth = 10f
                    chipStrokeColor =
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.purple_200))
                }
            }
            binding.optionOneTv.apply {
                chipStrokeWidth = 4f
                chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
            }
            binding.optionTwoTv.apply {
                chipStrokeWidth = 4f
                chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
            }
            binding.optionFourTv.apply {
                chipStrokeWidth = 4f
                chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
            }
        }

        binding.optionFourTv.setOnClickListener {
            result=4
            binding.optionFourTv.apply {
                if (questionCounter<question.size){
                    chipStrokeWidth = 10f
                    chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context,
                        R.color.purple_200
                    ))
                }
            }
            binding.optionOneTv.apply {
                chipStrokeWidth = 4f
                chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
            }
            binding.optionThreeTv.apply {
                chipStrokeWidth = 4f
                chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
            }
            binding.optionTwoTv.apply {
                chipStrokeWidth = 4f
                chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
            }
        }


        timer = object : CountDownTimer(21000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished<6000){
                    binding.timeTv.setTextColor(ResourcesCompat.getColor(resources,R.color.red,null))
                }else{
                    binding.timeTv.setTextColor(ResourcesCompat.getColor(resources,R.color.black,null))
                }
                val a = millisUntilFinished / 1000
                if (a<1){
                    questionCounter++
                    uncheckChipGroup()
                    showQuestion()
                }

                binding.timeTv.text = (millisUntilFinished / 1000).toString()
            }
            override fun onFinish() {
                binding.timeTv.text = "over!"
            }
        }
        showQuestion()
        return binding.root
    }

    private fun showQuestion() {

        if (questionCounter < question.size){
            Log.d("tag","size:${questionCounter+1}")
            timer.start()
            binding.questionTv.text = question[questionCounter].question
            binding.optionOneTv.text = question[questionCounter].option_one
            binding.optionTwoTv.text = question[questionCounter].option_two
            binding.optionThreeTv.text = question[questionCounter].option_three
            binding.optionFourTv.text = question[questionCounter].option_four
           // binding.NumberTv.text = "Question: ${questionCounter+1} / ${question.size}"

                binding.nextBt.setOnClickListener {
                    if (questionCounter<question.size){
                        if (result==question[questionCounter].right_answer){
                            point += 5
                            binding.pointTv.text ="$point"
                        }
                        timer.cancel()
                        uncheckChipGroup()
                        questionCounter++
                        if (questionCounter==question.size){
                            binding.nextBt.text= "End"
                        }
                        showQuestion()
                    }else{
                        val action = QuestionFragmentDirections.actionQuestionFragmentToHomeFragment()
                        view?.findNavController()?.navigate(action)
                        Toast.makeText(requireContext(),"Quiz completed",Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    private fun uncheckChipGroup() {
        binding.optionOneTv.apply {
            chipStrokeWidth = 4f
            chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
        }
        binding.optionTwoTv.apply {
            chipStrokeWidth = 4f
            chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
        }
        binding.optionThreeTv.apply {
            chipStrokeWidth = 4f
            chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
        }
        binding.optionFourTv.apply {
            chipStrokeWidth = 4f
            chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.ashe))
        }
    }


    private fun addSampleQuestion(){
        question.add(Question(1,"What's your name","Jack","Hero","Alam","Unknown",4))
        question.add(Question(2,"What's your country","Bangladesh","India","Pakistan","Afghanistan",1))
        question.add(Question(3,"What's your age","25","22","28","20",1))
        question.add(Question(4,"What's your favorite pet","Dog","Cat","Tiger","Deer",1))
        question.add(Question(5,"What's your nick name","Shakir","Cam","John","Foo",2))
        question.add(Question(6,"What's your hobby","Reading","Gaming","Cricket","Football",3))
        question.add(Question(7,"What's your father name","Fazlur","Abdul","Haque","Unknown",1))
    }


    private fun addSportsQuestion(){
        question.add(Question(1,"When real madrid founded?","1904","1902","1912","1910",2))
        question.add(Question(2,"Who played most game for real?","Stéfano","Raul","Marcelo","Casias",2))
        question.add(Question(3,"All time top scorer of real madrid","Raul","Stéfano","Cristiano Ronaldo","Zidane",3))
        question.add(Question(4,"The club is renamed as Real Madrid.","1912","1914","1918","1920",4))
        question.add(Question(5,"When real madrid first time win la liga","1932","1934","1935","1933",1))
        question.add(Question(6,"Who is the most successful real madrid manager?","Zidane","Miguel Muñoz","Vicente del Bosque","Luis Molowny",2))
        question.add(Question(7,"Real madrid biggest win","11-1","11-2","9-2","8-0",1))
        question.shuffle()
    }

    private fun addGkQuestion(){
        question.add(Question(1,"Bangladesh shares land borders with only two nations. Which among the following are those two nations?","India-Buthan","India-Nepal","India-Mayanmar","India-China",3))
        question.add(Question(2,"Who named bangladesh?","Portuguese","Sheikh Mujibur Rahman","Rabindranath Tagore","Shamsuddin Iliyas Shah",2))
        question.add(Question(3,"All time top scorer of real madrid","Raul","Stéfano","Cristiano Ronaldo","Zidane",3))
        question.add(Question(4,"The club is renamed as Real Madrid.","1912","1914","1918","1920",4))
        question.add(Question(5,"When real madrid first time will la liga","1932","1934","1935","1933",1))
        question.add(Question(6,"Who is the most successful real madrid manager","Zidane","Miguel Muñoz","Vicente del Bosque","Luis Molowny",2))
        question.add(Question(7,"Real madrid biggest win?","11-1","11-2","9-2","8-0",1))
        question.shuffle()
    }



}