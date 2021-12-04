package ch.bailu.aat.map.mapsforge;

import org.mapsforge.core.graphics.Canvas;
import org.mapsforge.core.model.BoundingBox;
import org.mapsforge.core.model.Point;
import org.mapsforge.map.layer.Layer;

import ch.bailu.aat_lib.map.MapContext;
import ch.bailu.aat_lib.map.layer.MapLayerInterface;
import ch.bailu.aat_lib.service.InsideContext;
import ch.bailu.aat_lib.service.ServicesInterface;

public class LayerWrapper extends Layer {

    private final MapContext mcontext;
    private final ServicesInterface services;
    private final MapLayerInterface layer;

    public LayerWrapper(ServicesInterface serviceContext, MapContext mc, MapLayerInterface l) {
        mcontext = mc;
        services = serviceContext;
        layer = l;
    }


    @Override
    public void draw(BoundingBox bounding, byte zoom, Canvas canvas, Point topLeftPoint) {
        new InsideContext(services) {
            @Override
            public void run() {
                layer.drawInside(mcontext);
            }
        };
    }
}
