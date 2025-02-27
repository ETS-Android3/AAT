package ch.bailu.aat_lib.service.elevation;

import ch.bailu.aat_lib.coordinates.Dem3Coordinates;
import ch.bailu.aat_lib.service.elevation.updater.ElevationUpdaterClient;

public interface ElevetionServiceInterface {
    short getElevation(int latitudeE6, int longitudeE6);

    void requestElevationUpdates(ElevationUpdaterClient owner, Dem3Coordinates[] srtmTileCoordinates);
    void requestElevationUpdates();

    void cancelElevationUpdates(ElevationUpdaterClient objGpxStatic);
}
