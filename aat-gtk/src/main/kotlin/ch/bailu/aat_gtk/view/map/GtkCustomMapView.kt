package ch.bailu.aat_gtk.view.map

import ch.bailu.aat_gtk.app.GtkAppContext
import ch.bailu.aat_lib.coordinates.BoundingBoxE6
import ch.bailu.aat_lib.dispatcher.DispatcherInterface
import ch.bailu.aat_lib.map.*
import ch.bailu.aat_lib.map.layer.LayerWrapper
import ch.bailu.aat_lib.map.layer.MapLayerInterface
import ch.bailu.aat_lib.map.layer.MapPositionLayer
import ch.bailu.aat_lib.map.tile.MapsForgeTileLayerStackConfigured
import ch.bailu.aat_lib.preferences.OnPreferencesChanged
import ch.bailu.aat_lib.preferences.StorageInterface
import ch.bailu.aat_lib.preferences.map.SolidTileSize
import ch.bailu.aat_lib.util.Limit
import ch.bailu.gtk.cairo.Context
import org.mapsforge.core.model.BoundingBox
import org.mapsforge.core.model.Dimension
import org.mapsforge.core.model.LatLong
import org.mapsforge.core.model.MapPosition
import org.mapsforge.core.util.LatLongUtils
import org.mapsforge.core.util.Parameters
import org.mapsforge.map.gtk.graphics.GtkGraphicContext
import org.mapsforge.map.gtk.view.MapView
import org.mapsforge.map.layer.Layer
import org.mapsforge.map.model.IMapViewPosition
import org.mapsforge.map.model.common.Observer
import java.util.*

class GtkCustomMapView (
    private val storage: StorageInterface,
    dispatcher: DispatcherInterface
) : MapView(), MapViewInterface, OnPreferencesChanged, Attachable {

    companion object {
        private const val SHOW_DEBUG_LAYERS = false
    }

    private val backgroundContext: GtkMapContext = GtkMapContext(this, this.javaClass.simpleName)
    private val foregroundContext: GtkMapContextForeground

    private val layers = ArrayList<MapLayerInterface>(10)

    private val pos: MapPositionLayer
    private val stack: MapsForgeTileLayerStackConfigured

    init {
        model.displayModel.setFixedTileSize(SolidTileSize(storage, AppDensity()).getTileSize())

        foregroundContext = GtkMapContextForeground(
            MapsForgeMetrics(this, AppDensity()), backgroundContext, layers)
        addLayer(backgroundContext)
        mapScaleBar.isVisible = false
        if (SHOW_DEBUG_LAYERS) {
            fpsCounter.isVisible = true
        }
        Parameters.SQUARE_FRAME_BUFFER = false
        stack = MapsForgeTileLayerStackConfigured.All(this, GtkAppContext)
        add(stack)

        pos = MapPositionLayer(mContext, storage, dispatcher)
        add(pos)
        model.mapViewPosition.addObserver(object : Observer {
            private var center: LatLong = model.mapViewPosition.center
            override fun onChange() {
                val newCenter: LatLong = model.mapViewPosition.center
                if (newCenter != center) {
                    center = newCenter
                    pos.onMapCenterChanged(center)
                }
            }
        })
        onAttached()
    }


    override fun frameBounding(boundingBox: BoundingBoxE6) {
        frameBounding(boundingBox.toBoundingBox())
    }

    private fun frameBounding(bounding: BoundingBox) {
        val dimension = model.mapViewDimension.dimension
        if (dimension != null) {
            val zoom: Byte = zoomForBounds(bounding, dimension)
            val position = MapPosition(bounding.centerPoint, zoom)
            model.mapViewPosition.mapPosition = position
        }
    }

    private fun zoomForBounds(bounding: BoundingBox, dimension: Dimension): Byte {
        var zoom: Byte = if (bounding.minLatitude == 0.0 && bounding.minLongitude == 0.0 && bounding.maxLatitude == 0.0 && bounding.maxLongitude == 0.0) {
                0
            } else {
                LatLongUtils.zoomForBounds(
                    dimension,
                    bounding,
                    model.displayModel.tileSize
                )
            }
        zoom = Math.min(zoom.toInt(), model.mapViewPosition.zoomLevelMax.toInt()).toByte()
        zoom = Math.max(zoom.toInt(), model.mapViewPosition.zoomLevelMin.toInt()).toByte()
        return zoom
    }

    override fun zoomOut() {
        setZoomLevel(
            Limit.clamp(
                model.mapViewPosition.zoomLevel - 1,
                model.mapViewPosition.zoomLevelMin.toInt(),
                model.mapViewPosition.zoomLevelMax.toInt()
            ).toByte()
        )
    }

    override fun zoomIn() {
        setZoomLevel(
            Limit.clamp(
                model.mapViewPosition.zoomLevel + 1,
                model.mapViewPosition.zoomLevelMin.toInt(),
                model.mapViewPosition.zoomLevelMax.toInt()
            ).toByte()
        )
    }

    override fun onResize(width: Int, height: Int) {
        super.onResize(width, height)
        layers.forEach {
            it.onLayout(true, 0,0,width, height)
        }
    }

    override fun requestRedraw() {
        layerManager.redrawLayers()
    }

    override fun add(layer: MapLayerInterface) {
        val wrapper = if (layer is Layer) {
            layer
        } else {
            LayerWrapper(GtkAppContext.services, mContext, layer)
        }
        addLayer(wrapper)
        layers.add(layer)
        layer.onAttached()
    }


    override fun getMContext(): MapContext {
        return backgroundContext
    }

    override fun reDownloadTiles() {
        stack.reDownloadTiles()
    }

    override fun getMapViewPosition(): IMapViewPosition {
        return model.mapViewPosition
    }

    override fun onDraw(context: Context) {
        val canvas = GtkGraphicContext(context, width, height)
        foregroundContext.dispatchDraw(canvas)
    }

    override fun onAttached() {
        storage.register(this)
        for (layer in layers) {
            layer.onAttached()
        }
    }

    override fun onDetached() {
        storage.unregister(this)
        for (layer in layers) {
            layer.onDetached()
        }
    }

    override fun onPreferencesChanged(storage: StorageInterface, key: String) {
        for (layer in layers) {
            layer.onPreferencesChanged(storage, key)
        }
    }
}