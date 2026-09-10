package com.spiritdev.proxyvault.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
            val list = viewModel.workingProxies.value
            if (list.isEmpty()) {
                toast("No working proxies to export")
                return@setOnClickListener
            }
            val text = list.joinToString("\n") { it.address }
            copyToClipboardRaw(text)
            toast("Exported ${list.size} proxies to clipboard")
        }

        observeState()
        viewModel.refresh()
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.workingProxies.collectLatest { list ->
                adapter.submit(list)
                binding.tvCount.text = "${list.size} working proxies"
                binding.tvLastRefresh.text = viewModel.lastRefreshLabel
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
