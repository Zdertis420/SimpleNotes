package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import orc.zdertis420.simplenotes.R
import orc.zdertis420.simplenotes.databinding.FragmentHomeBinding
import orc.zdertis420.simplenotes.ui.adapter.PagerAdapter
import orc.zdertis420.simplenotes.ui.state.HomeState
import orc.zdertis420.simplenotes.ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _views: FragmentHomeBinding? = null
    private val views get() = _views

    private val viewModel by viewModel<HomeViewModel>()

    private lateinit var tabLayoutMediator: TabLayoutMediator


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _views = FragmentHomeBinding.inflate(inflater, container, false)

        return views?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.homeStateLiveData.observe(viewLifecycleOwner) { state ->
            render(state)
        }


        tabLayoutMediator
    }

    private fun render(state: HomeState) {
        when (state) {
            is HomeState.StartUp -> changeGreeting(state.partOfDate)
        }
    }

    private fun changeGreeting(partOfDay: Int) {
        when (partOfDay) {
            0 -> views?.homeToolbar?.subtitle = getString(R.string.greeting_night)
            1 -> views?.homeToolbar?.subtitle = getString(R.string.greeting_morning)
            2 -> views?.homeToolbar?.subtitle = getString(R.string.greeting_day)
            3 -> views?.homeToolbar?.subtitle = getString(R.string.greeting_evening)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _views = null
    }
}