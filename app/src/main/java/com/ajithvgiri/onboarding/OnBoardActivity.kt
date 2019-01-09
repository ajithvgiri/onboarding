package com.ajithvgiri.onboarding

import android.animation.ArgbEvaluator
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.ajithvgiri.onboarding.fragments.PlaceholderFragment
import kotlinx.android.synthetic.main.activity_on_board.*


class OnBoardActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null


    var lastLeftValue = 0
    lateinit var indicators: Array<ImageView>
    var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = ContextCompat.getColor(this, R.color.black_trans80)
        }
        setContentView(R.layout.activity_on_board)

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the bottom bar

        var colorList = intArrayOf(
            ContextCompat.getColor(this, R.color.cyan),
            ContextCompat.getColor(this, R.color.green),
            ContextCompat.getColor(this, R.color.orange)
        )
        indicators = arrayOf(intro_indicator_0, intro_indicator_1, intro_indicator_2)
        updateIndicators(page)


        // Set up the ViewPager with the sections adapter.
        val evaluator = ArgbEvaluator()
        container.adapter = mSectionsPagerAdapter
        container.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                /* color update */
                val colorUpdate = evaluator.evaluate(
                    positionOffset,
                    colorList[position],
                    colorList[if (position == 2) position else position + 1]
                ) as Int
                container.setBackgroundColor(colorUpdate)
            }

            override fun onPageSelected(position: Int) {
                page = position
                updateIndicators(page)

                when (position) {
                    0 -> container.setBackgroundColor(ContextCompat.getColor(this@OnBoardActivity, R.color.cyan))
                    1 -> container.setBackgroundColor(ContextCompat.getColor(this@OnBoardActivity, R.color.green))
                    2 -> container.setBackgroundColor(ContextCompat.getColor(this@OnBoardActivity, R.color.orange))
                }

                intro_btn_finish.visibility = if (position == 2) View.VISIBLE else View.GONE
                intro_btn_next.visibility = if (position == 2) View.GONE else View.VISIBLE
            }

            override fun onPageScrollStateChanged(i: Int) {

            }
        })


    }

    fun updateIndicators(position: Int) {
        for (i in 0 until indicators.size) {
            indicators[i].setBackgroundResource(
                if (i == position) R.drawable.indicator_selected else R.drawable.indicator_unselected
            )
        }
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }


}
