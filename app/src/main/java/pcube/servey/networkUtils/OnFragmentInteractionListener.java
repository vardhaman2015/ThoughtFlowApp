package pcube.servey.networkUtils;

import android.net.Uri;
import android.support.v4.app.Fragment;

public interface OnFragmentInteractionListener {

    void onFragmentInteraction(Uri uri);
    void updateData();
    void replaceFragment(Fragment fragment);
}
