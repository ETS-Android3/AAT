package ch.bailu.aat_gtk.ui.view.map.control

import ch.bailu.gtk.gtk.Orientation

class InfoBar {

    val bar = Bar(Orientation.VERTICAL)
    private val plus  = bar.add("zoom-in-symbolic")
    private val minus = bar.add("zoom-out-symbolic")
    private val frame = bar.add("zoom-fit-best-symbolic")
}