package intermediate.iak.laylasm.imovie;

/**
 * Created by Layla Siti Mardhiyah on 16/08/2017.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;
public class ObjectTrailer {
    @SerializedName("results")
    List<DataTrailer> results;

    public class DataTrailer {
        @SerializedName("name")
        public String name;

        @SerializedName("key")
        public String key;

        @SerializedName("type")
        public String type;
    }
}
