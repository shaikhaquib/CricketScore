package com.aquib.cricketscore.Fragment

import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.aquib.cricketscore.Adapter.TabAdapter
import com.aquib.cricketscore.Fragment.Tabs.All
import com.aquib.cricketscore.Fragment.Tabs.Cricket
import com.aquib.cricketscore.Fragment.Tabs.Football
import com.aquib.cricketscore.Fragment.Tabs.IPL
import com.aquib.cricketscore.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

import javax.security.auth.callback.Callback

class HomeFragment : Fragment() {
    lateinit var adapter: TabAdapter
       override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_home, container, false)


           return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.tabLayout.tabGravity = TabLayout.GRAVITY_START
        setupViewPager()
        tabLayout.setupWithViewPager(viewPager)

    }

    private fun setupViewPager() {
        adapter = TabAdapter(childFragmentManager)

        // LoginFragment is the name of Fragment and the Login
        // is a title of tab
        adapter.addFragment(All(), "All")
        adapter.addFragment(IPL(), "IPL")
        adapter.addFragment(Cricket(), "Cricket")
        adapter.addFragment(Football(), "Football")

        // setting adapter to view pager.
        viewPager.setAdapter(adapter)
    }

}