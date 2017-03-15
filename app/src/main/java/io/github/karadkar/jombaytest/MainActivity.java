package io.github.karadkar.jombaytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<List<Photo>>{
    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    PhotoAdapter mAdapter;
    RealmResults<Photo> mRealmPhotoResults;
    RealmChangeListener<RealmResults<Photo>> mRealmListener = new RealmChangeListener<RealmResults<Photo>>() {
        @Override
        public void onChange(RealmResults<Photo> results) {
            if (mAdapter!=null && results!=null && results.size()>0){
                mAdapter.addPhotos(results);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: inti realm in Application class
            Realm.init(getApplicationContext());

        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.act_main_recyclerView);

        // todo: remove hardcoded value
        mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        mAdapter = new PhotoAdapter(getApplicationContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        PhotoService service = RetrofitService.getInstance().createService(PhotoService.class);

        // get data from remote service
        Call<List<Photo>> call = service.getPhotoList();
        call.enqueue(this);

        // add realm listener
        Realm realm = Realm.getDefaultInstance();
        mRealmPhotoResults = realm.where(Photo.class)
                .findAllAsync();

        mRealmPhotoResults.addChangeListener(mRealmListener);
    }

    @Override
    public void onResponse(Call<List<Photo>> call, final Response<List<Photo>> response) {
        if (response.body()!=null && response.body().size() > 0){
            // Store objects to realm
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(response.body());
                }
            });
        }
        Log.e(TAG,"Data size: "+response.body().size());
    }

    @Override
    public void onFailure(Call<List<Photo>> call, Throwable t) {
        t.printStackTrace();
    }
}
