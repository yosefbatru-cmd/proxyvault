package com.spiritdev.proxyvault.network

import com.spiritdev.proxyvault.model.ProxySource

/**
 * Built-in public proxy list sources.
 * All are free / public research feeds. No paid provider keys required for core function.
 */
object SourceCatalog {

    val DEFAULT: List<ProxySource> = listOf(
        ProxySource(
            id = "speedx",
            name = "TheSpeedX/PROXY-List",
            url = "https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/http.txt",
            type = "github"
        ),
        ProxySource(
            id = "speedx_socks5",
            name = "TheSpeedX SOCKS5",
            url = "https://raw.githubusercontent.com/TheSpeedX/PROXY-List/master/socks5.txt",
            type = "github"
        ),
        ProxySource(
            id = "shiftytr",
            name = "ShiftyTR/Proxy-List",
            url = "https://raw.githubusercontent.com/ShiftyTR/Proxy-List/master/http.txt",
            type = "github"
        ),
        ProxySource(
            id = "monosans",
            name = "monosans/proxy-list",
            url = "https://raw.githubusercontent.com/monosans/proxy-list/main/proxies/http.txt",
            type = "github"
        ),
        ProxySource(
            id = "proxyscrape_http",
            name = "ProxyScrape HTTP",
            url = "https://api.proxyscrape.com/v2/?request=displayproxies&protocol=http&timeout=10000&country=all&ssl=all&anonymity=all",
            type = "api"
        ),
        ProxySource(
            id = "proxyscrape_socks5",
            name = "ProxyScrape SOCKS5",
            url = "https://api.proxyscrape.com/v2/?request=displayproxies&protocol=socks5&timeout=10000&country=all",
            type = "api"
        ),
        ProxySource(
            id = "free_proxy_list",
            name = "Free-Proxy-List.net",
            url = "https://www.proxy-list.download/api/v1/get?type=http",
            type = "api"
        ),
        ProxySource(
            id = "openproxylist",
            name = "openproxylist",
            url = "https://raw.githubusercontent.com/roosterkid/openproxylist/main/HTTPS_RAW.txt",
            type = "github"
        ),
        ProxySource(
            id = "clarketm",
            name = "clarketm/proxy-list",
            url = "https://raw.githubusercontent.com/clarketm/proxy-list/master/proxy-list-raw.txt",
            type = "github"
        ),
        ProxySource(
            id = "jetkai",
            name = "jetkai/proxy-list",
            url = "https://raw.githubusercontent.com/jetkai/proxy-list/main/online-proxies/txt/proxies-http.txt",
            type = "github"
        )
    )
}
