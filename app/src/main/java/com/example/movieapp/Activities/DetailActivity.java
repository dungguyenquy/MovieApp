package com.example.movieapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapp.Model.Film;
import com.example.movieapp.R;
import com.example.movieapp.databinding.ActivityDetailBinding;

import eightbitlab.com.blurview.RenderScriptBlur;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        Window w=getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
    private void setVariable(){
        Film item=(Film) getIntent().getSerializableExtra("object");
        RequestOptions requestOptions=new RequestOptions();
        requestOptions=requestOptions.transform(new CenterCrop(),new GranularRoundedCorners(0,0,50,50));
        Glide.with(this)
                .load(item.getPoster())
                .apply(requestOptions)
                .into(binding.filmPic);
        binding.titleTxt.setText(item.getTitle());
        binding.imdbTxt.setText("IMDB:"+item.getImdb());
        binding.movieTimesTxt.setText(item.getYear()+"-"+item.getTime());
        binding.movieSummery.setText(item.getDescription());
        binding.genreTxt.setText(item.getGenre());
        binding.castsTxt.setText(item.getCasts());
        binding.watchTrailerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=item.getTrailer().replace("https://www.youtube.com/watch?v=","");
                Intent appIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube"+id));
                Intent webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(item.getTrailer()));
                try {
                    startActivity(appIntent);
                }catch (ActivityNotFoundException e){
                    startActivity(webIntent);
                }
            }
        });
        binding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        float radius=10f;
        View decorView=getWindow().getDecorView();
        ViewGroup rootView=(ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowsBackground=decorView.getBackground();
        binding.blurView.setupWith(rootView,new RenderScriptBlur(this))
                .setFrameClearDrawable(windowsBackground)
                .setBlurRadius(radius);
        binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.blurView.setClipToOutline(true);

    }

}