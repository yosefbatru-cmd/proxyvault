package com.spiritdev.proxyvault.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.spiritdev.proxyvault.R
import com.spiritdev.proxyvault.databinding.ActivityMainBinding
import com.spiritdev.proxyvault.model.ProxyItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: ProxyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        adapter = ProxyAdapter(
            onCopy = { proxy -> copyToClipboard(proxy) },
            onTest = { proxy -> viewModel.testSingle(proxy) }
        )

        binding.recyclerProxies.layoutManager = LinearLayoutManager(this)
        binding.recyclerProxies.adapter = adapter

        binding.btnRefresh.setOnClickListener {
            viewModel.refresh()
        }

        binding.btnExport.setOnClickListener {
            val (ok, payload) = viewModel.buildExportText()
            if (!ok && payload.startsWith("No working")) {
                toast(payload)
                return@setOnClickListener
            }
            if (!ok && payload.startsWith("Free limit")) {
                // still copy the free slice
                val list = viewModel.workingProxies.value.take(50)
                copyToClipboardRaw(list.joinToString("\n") { it.address })
                toast(payload)
                return@setOnClickListener
            }
            copyToClipboardRaw(payload)
            toast("Exported ${viewModel.workingProxies.value.size} proxies")
        }

        binding.btnRedeem.setOnClickListener {
            openRedeem()
        }

        observeState()
        viewModel.refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_redeem -> {
                openRedeem()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openRedeem() {
        RedeemDialog {
            viewModel.refreshTierLabel()
            toast("Tier updated")
        }.show(supportFragmentManager, "redeem")
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.workingProxies.collectLatest { list ->
                adapter.submit(list)
                binding.tvCount.text = "${list.size} working proxies"
                binding.tvLastRefresh.text = "Last: ${viewModel.lastRefreshLabel}"
            }
        }
        lifecycleScope.launch {
            viewModel.isLoading.collectLatest { loading ->
                binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
                binding.btnRefresh.isEnabled = !loading
            }
        }
        lifecycleScope.launch {
            viewModel.statusMessage.collectLatest { msg ->
                if (msg.isNotBlank()) binding.tvStatus.text = msg
            }
        }
        lifecycleScope.launch {
            viewModel.tierLabel.collectLatest { tier ->
                binding.tvTier.text = tier
            }
        }
    }

    private fun copyToClipboard(proxy: ProxyItem) {
        copyToClipboardRaw(proxy.address)
        toast("Copied ${proxy.address}")
    }

    private fun copyToClipboardRaw(text: String) {
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText("proxy", text))
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
