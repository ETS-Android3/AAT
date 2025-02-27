package ch.bailu.aat_lib.preferences.presets;

import java.util.ArrayList;
import java.util.Collections;

import ch.bailu.aat_lib.exception.ValidationException;
import ch.bailu.aat_lib.preferences.OldSolidMET;
import ch.bailu.aat_lib.preferences.SolidString;
import ch.bailu.aat_lib.preferences.StorageInterface;
import ch.bailu.aat_lib.resources.Res;

public class SolidMET extends SolidString {
    private final int preset;

    public SolidMET(StorageInterface s, int i) {
        super(s, SolidMET.class.getSimpleName()+ "_" + i);
        preset = i;
    }

    @Override
    public String getLabel() {
        return Res.str().p_met();
    }

    @Override
    public String getValueAsString() {
        String r = super.getValueAsString();

        if (getStorage().isDefaultString(r)) {
            r = new OldSolidMET(getStorage(), preset).getValueAsString();
        }
        return r;
    }


    public float getMETValue() {
        String val = getValueAsString();

        final int from = 0;
        int to = val.indexOf(' ');

        float r = 0f;


        if (to > from) {
            try {
                String met = val.substring(from, to);
                r = Float.parseFloat(met);
            } catch (NumberFormatException e) {
                // TODO : userfeedback missing
                r = 0f;
            }
        }

        if (r > 20f || r < 0f) {
            // TODO : userfeedback missing, validation should be moved to MET preferences
            r = 0f;
        }
        return r;
    }

    @Override
    public void setValueFromString(String s) throws ValidationException {
        s = s.trim();

        if (! validate(s)) {
            throw new ValidationException(Res.str().error_met());
        } else {
            setValue(s);
        }
    }


    @Override
    public ArrayList<String> buildSelection(ArrayList<String> list) {

        String[] array = Res.str().p_met_list();

        Collections.addAll(list, array);

        return list;
    }

    @Override
    public boolean validate(String s) {
        // entering 0.0 is still possible
        return s.split(" ")[0].matches("1?[0-9].[0-9]|20.0");
    }
}
