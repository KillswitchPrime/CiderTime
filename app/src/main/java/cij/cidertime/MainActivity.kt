package cij.cidertime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cij.cidertime.adapters.LabelAdapter
import cij.cidertime.models.Beverage
import cij.cidertime.models.Label
import com.example.cidertime.R
import com.example.cidertime.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private val fileName = "CiderTimeDataStorage"
    private val rowDelimiter = ";"
    private val columnDelimiter = "£"
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val fileInputStream: FileInputStream by lazy {
        openFileInput(fileName)
    }

    private val inputStreamReader: InputStreamReader by lazy{
        InputStreamReader(fileInputStream)
    }

    private val bufferedReader: BufferedReader by lazy{
        BufferedReader(inputStreamReader)
    }

    private val labels: List<Label> by lazy{
        getDataFromStorage()
    }

    /**
     * Outer list
     */
    private val linearLayoutManager: RecyclerView.LayoutManager by lazy{
        LinearLayoutManager(this)
    }

    private val labelAdapter: RecyclerView.Adapter<LabelAdapter.ViewHolder> by lazy{
        LabelAdapter(labels)
    }

    /**
     * Inner list
     */
    private val gridLayoutManager: GridLayoutManager by lazy{
        GridLayoutManager(this, 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val labelList: RecyclerView = this.findViewById(R.id.labelList)
        labelList.layoutManager = linearLayoutManager
        labelList.adapter = labelAdapter
    }

    private fun getDataFromStorage(): List<Label>{
        var labelList: List<Label> = emptyList()

        var text = bufferedReader.readLine()
        while(text != null){
            val row = text.split(rowDelimiter)

            lateinit var label: Label
            label.title = row[0]
            var beverages: List<Beverage> = emptyList()
            for(data in row.drop(1)){
                val column = data.split(columnDelimiter)
                beverages = beverages.plus(Beverage(column[0], column[1]))
            }
            label.beverages = beverages

            labelList = labelList.plus(label)
            text = bufferedReader.readLine()
        }

        return labelList
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}