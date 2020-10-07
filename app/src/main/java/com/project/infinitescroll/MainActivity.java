package com.project.infinitescroll;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.project.infinitescroll.utils.InfiniteScrollProvider;
import com.project.infinitescroll.utils.OnLoadMoreListener;

public class MainActivity extends AppCompatActivity implements OnLoadMoreListener {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView    = findViewById(R.id.recycler_view);
        postAdapter     = new PostAdapter(this);
        postAdapter.addPosts(DataFakeGenerator.getPosts(page));

        //staggered layout
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        //grid layout
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(postAdapter);

        ImageView githubSourceImageView = findViewById(R.id.image_github);
        githubSourceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/achmadqomarudin");
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, uri), "Choose Browser"));
            }
        });

        InfiniteScrollProvider infiniteScrollProvider = new InfiniteScrollProvider();
        infiniteScrollProvider.attach(recyclerView, this);

        progressBar = findViewById(R.id.progress_bar);

    }

    @Override
    public void onLoadMore() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                postAdapter.addPosts(DataFakeGenerator.getPosts(page += 1));
                progressBar.setVisibility(View.GONE);
            }
        }, 1500);
    }
}