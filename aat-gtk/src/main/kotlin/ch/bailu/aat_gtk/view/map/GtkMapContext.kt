package ch.bailu.aat_gtk.view.map

import ch.bailu.aat_lib.map.*
import org.mapsforge.core.graphics.Canvas
import org.mapsforge.core.model.BoundingBox
import org.mapsforge.core.model.Point
import org.mapsforge.map.layer.Layer

class GtkMapContext(val mapView: GtkCustomMapView, val key: String): Layer(), MapContext {
    private val metrics = MapsForgeMetrics(mapView, AppDensity())
    private val draw = GtkMapDraw()
    private val twoNodes = TwoNodes(metrics)

    override fun getMetrics(): MapMetrics {
        return metrics
    }

    override fun draw(): MapDraw {
        return draw
    }

    override fun getSolidKey(): String {
        return key
    }

    override fun getTwoNodes(): TwoNodes {
        return twoNodes
    }

    override fun getMapView(): MapViewInterface {
        return mapView
    }

    override fun draw(boundingBox: BoundingBox?, zoomLevel: Byte, canvas: Canvas, topLeftPoint: Point?) {
        metrics.init(boundingBox, zoomLevel, canvas.dimension, topLeftPoint)
        draw.init(canvas, metrics)
    }
}
