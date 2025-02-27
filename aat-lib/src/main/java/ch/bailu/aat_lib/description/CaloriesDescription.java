package ch.bailu.aat_lib.description;

import ch.bailu.aat_lib.gpx.GpxInformation;
import ch.bailu.aat_lib.preferences.StorageInterface;
import ch.bailu.aat_lib.preferences.general.SolidWeight;
import ch.bailu.aat_lib.preferences.presets.SolidMET;
import ch.bailu.aat_lib.preferences.presets.SolidPreset;
import ch.bailu.aat_lib.resources.Res;

public class CaloriesDescription extends LongDescription {

    private final StorageInterface storage;

    public CaloriesDescription(StorageInterface s) {
        storage = s;
    }

    @Override
    public String getLabel() {
        return Res.str().calories();
    }

    @Override
    public String getUnit() {
        return "kcal";
    }

    public String getValue() {
        return String.valueOf(getCache());
    }


    @Override
    public void onContentUpdated(int iid, GpxInformation info) {
        setCache((long)calculateCalories(info));
    }


    private float calculateCalories(GpxInformation track) {
        int preset = new SolidPreset(storage).getIndex();

        float hours = ((float)track.getTimeDelta()) / (1000f * 60f * 60f);
        float met = new SolidMET(storage, preset).getMETValue();
        // TODO : add userfeedback about wrong calculation here ?
        float weight = new SolidWeight(storage).getValue();
        float kcal = hours*met*weight;

        return kcal;
    }
}
