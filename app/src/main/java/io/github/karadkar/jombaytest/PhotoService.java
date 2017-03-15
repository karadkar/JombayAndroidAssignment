package io.github.karadkar.jombaytest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by rnztx on 15/3/17.
 */

public interface PhotoService {
    @GET("photos.json")
    Call<List<Photo>> getPhotoList();
}
