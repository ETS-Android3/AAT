package ch.bailu.aat_lib.description;

import ch.bailu.aat_lib.coordinates.CH1903Coordinates;
import ch.bailu.aat_lib.gpx.GpxInformation;
import ch.bailu.aat_lib.resources.Res;

public class CH1903NorthingDescription extends CH1903EastingDescription {

    @Override
    public String getLabel() {
        return Res.str().d_chx();
    }

    @Override
    public void onContentUpdated(int iid, GpxInformation info) {
        if (setCache(info.getLatitude())) setCH1903_x(info);
    }

    private void setCH1903_x(GpxInformation info) {
        coordinate = new CH1903Coordinates(info.getLatitude(), info.getLongitude()).getNorthing();
    }

}
