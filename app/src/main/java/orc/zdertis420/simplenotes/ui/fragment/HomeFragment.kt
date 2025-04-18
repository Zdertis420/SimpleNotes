package orc.zdertis420.simplenotes.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import orc.zdertis420.simplenotes.R
import orc.zdertis420.simplenotes.databinding.FragmentHomeBinding
import orc.zdertis420.simplenotes.ui.adapter.PagerAdapter
import orc.zdertis420.simplenotes.ui.state.HomeState
import orc.zdertis420.simplenotes.ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), View.OnClickListener {

    private var _views: FragmentHomeBinding? = null
    private val views get() = _views!!

    private val viewModel by viewModel<HomeViewModel>()

    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _views = FragmentHomeBinding.inflate(inflater, container, false)

        return views.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()

        viewModel.homeStateLiveData.observe(viewLifecycleOwner) { state ->
            render(state)
        }

        viewModel.updateTime()

        views.viewPager.adapter = PagerAdapter(childFragmentManager, lifecycle)
        setupTabLayout()
    }

    private fun setupTabLayout() {
        tabLayoutMediator = TabLayoutMediator(views.tabLayout, views.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.active_tasks)
                1 -> tab.text = getString(R.string.completed_tasks)
                2 -> tab.text = getString(R.string.all_tasks)
            }
        }.also { it.attach() }
    }

    private fun setupClickListeners() {
        with(views) {
            settingsButton.setOnClickListener(this@HomeFragment)
            newTask.setOnClickListener(this@HomeFragment)
        }
    }

    private fun render(state: HomeState) {
        when (state) {
            is HomeState.StartUp -> changeGreeting(state.partOfDate)
        }
    }

    private fun changeGreeting(partOfDay: Int) {
        when (partOfDay) {
            0 -> views.homeToolbar.subtitle = getString(R.string.greeting_night)
            1 -> views.homeToolbar.subtitle = getString(R.string.greeting_morning)
            2 -> views.homeToolbar.subtitle = getString(R.string.greeting_day)
            3 -> views.homeToolbar.subtitle = getString(R.string.greeting_evening)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabLayoutMediator?.detach()
        tabLayoutMediator = null
        _views = null
    }

    override fun onClick(v: View?) {
        val activityNavControl = requireActivity().findNavController(R.id.main_fragment_container)

        when (v?.id) {
            R.id.settings_button -> activityNavControl.navigate(R.id.action_homeFragment_to_settingsFragment)

            R.id.new_task -> activityNavControl.navigate(R.id.action_homeFragment_to_editTaskFragment)
        }
    }
}