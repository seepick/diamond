package nl.uwv.smz.diamond.app

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import java.time.format.DateTimeFormatter
import kotlin.math.ceil

data class BannerConfig(
    val version: String,
    val buildTime: LocalDateTime,
    val branchName: String,
)

@Suppress("MagicNumber")
fun buildBanner(props: BuildProperties) =
    """
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⣴⠶⠶⠶⠦⠤⠤⠤⠤⠤⠤⠤⢤⣤⣀⡀
⠀⠀⠀⠀⠀⠀⢀⣠⠴⢶⣾⣿⡟⠋ ${props.appVersion.limitAndCentered(15)} ⠉⠙⠒⠦⢤⣀⣀
⠀⠀⠀⠀⣠⡶⠋⠀⢀⣴⠏⠀⠙⢦⡀ ${props.buildTime.format(FORMAT).limitAndCentered(20)} ⠈⠉⠓⠲⣤⡀
⠀⢀⣤⡾⠋⠀⠀⣠⠞⢁⣀⣠⠤⠤⠿⣶⠦⢄⣀⡀ ${props.branchName.limitAndCentered(19)} ⠈⢻⣦⣄
⢠⣿⣯⠤⠶⣲⠞⡟⠉⠁⠀⠀⠀⠀⠀⠹⡆⠀⠀⠉⠙⠓⠲⠤⢄⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⠿⣿⢷⣦⡀
⠈⣿⡀⢀⡴⠃⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⢹⡄⠀⠀⣀⣀⡤⠴⠒⠛⠻⢭⡉⠉⠉⠉⠙⠛⠛⠒⢲⠶⢯⣁⠀⠀⠹⡄⠀⠙⣦
⠀⠘⣟⢿⣀⠀⠀⣧⠀⠀⠀⠀⢀⣀⣤⠴⠖⡿⣏⠉⠁⠀⠀⠀⠀⠀⠀⠀⠉⠳⣤⡀⠀⠀⢀⡴⠋⠀⠀⠈⠓⢦⣀⢻⡄⠀⠘⣷⡀
⠀⠀⠹⣷⠻⢷⣤⢿⣴⠖⠚⠋⠉⠀⠀⠀⢠⠇⠈⢳⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⣲⣴⡋⠀⠀⠀⠀⠀⠀⠀⠈⠛⣷⣄⠀⠘⣷⡄
⠀⠀⠀⠈⠳⣄⠉⠳⣌⡙⠦⣄⡀⠀⠀⠀⡼⠀⠀⠀⠙⢦⡀⠀⠀⠀⠀⠀⢀⣠⠔⠋⠉⡏⠙⠦⣄⠀⠀⠀⠀⠀⠀⣼⠁⢯⠙⠢⣼⣿
⠀⠀⠀⠀⠀⠘⢧⡄⠘⣯⡉⠉⠙⠿⣍⡉⠙⠲⣄⣀⡀⠀⠙⣦⠀⣀⡤⠞⠉⠀⠀⠀⢰⠃⠀⠀⠈⠛⢦⣀⠀⠀⣸⠃⠀⠈⣧⢀⣨⡿
⠀⠀⠀⠀⠀⠀⠀⠙⢦⡘⣿⣄⠀⠀⠀⠙⣟⠛⠛⠛⠻⠯⣍⣉⠙⠛⠦⠤⣤⣀⣤⡤⠾⣤⣀⡀⠀⠀⢀⣈⣳⣶⣧⣀⣠⡴⢛⡽⠋
⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣼⣏⠳⣄⠀⠀⠹⡄⠀⠀⠀⠀⠀⣹⡏⠉⠉⠉⠉⠓⠲⢤⣤⠤⠾⠛⠛⠻⢭⣤⡤⠖⠛⢉⣩⠿⠛⠁
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣧⠙⢦⡀⠀⢳⡀⠀⠀⠀⣰⠋⡇⠀⠀⠀⠀⠀⡴⠋⠀⠀⠀⠀⣠⣴⠞⠁⣀⡤⠞⠉
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢧⡈⠻⣄⠈⣧⠀⠀⢠⠏⠀⡇⠀⠀⠀⣠⠞⠁⠀⢀⣠⠖⣻⣟⣡⠴⠛⠁
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⣦⡈⢧⡸⡆⢀⡟⠀⠀⣧⠀⢀⡴⠋⢀⡤⠞⠉⣠⡾⠛⠉
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠳⣄⠙⢿⡞⠀⠀⠀⣿⣠⣏⠴⠚⢁⣠⠴⠋⠁
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢳⡌⢷⠀⠀⢠⠟⠋⣡⡴⠚⠉
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢿⣇⣴⣯⠴⠋⠁
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠉
"""

private val FORMAT = DateTimeFormatter.ISO_DATE_TIME

private fun String.limitAndCentered(length: Int) =
    ensureMax(length).padAround(length)

private fun String.ensureMax(max: Int): String =
    if (length <= max) this else substring(0, max - 1) + "…"

private fun String.padAround(targetLength: Int): String {
    val padBefore = ceil((targetLength - length) / 2.0).toInt()
    return padStart(padBefore + length).padEnd(targetLength)
}
