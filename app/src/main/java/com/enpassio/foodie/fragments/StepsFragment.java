package com.enpassio.foodie.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enpassio.foodie.R;
import com.enpassio.foodie.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.graphics.Color.rgb;
import static com.enpassio.foodie.R.layout.item_steps;
import static com.enpassio.foodie.util.Constants.MY_POSITION_KEY;
import static com.enpassio.foodie.util.Constants.MY_STEPS_ARRAYLIST_KEY;
import static com.enpassio.foodie.util.Constants.SEEKBAR_KEY;


public class StepsFragment extends Fragment {

    private static MediaSessionCompat mMediaSession;
    Context context;
    Bundle bundle;
    ArrayList<Step> stepArrayList;
    Step step;
    boolean isSavedInstanceNull;
    /**
     * referenced from the @link:
     * https://github.com/udacity/AdvancedAndroid_ClassicalMusicQuiz/blob/TMED.06-Solution-AddMediaButtonReceiver/app/src/main/java/com/example/android/classicalmusicquiz/QuizActivity.java
     */
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private PlaybackStateCompat.Builder mStateBuilder;
    private long mSeekBar;
    private String videoUrlString;

    public StepsFragment() {
        //required empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        context = getContext();
        stepArrayList = bundle.getParcelableArrayList(MY_STEPS_ARRAYLIST_KEY);
        step = stepArrayList.get(bundle.getInt(MY_POSITION_KEY));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                /* Inflate the layout for this fragment */
        View rootView = inflater.inflate(item_steps, container, false);

        if (savedInstanceState != null) {
            mSeekBar = savedInstanceState.getLong(SEEKBAR_KEY);
            isSavedInstanceNull = false;
        } else {
            mSeekBar = 0;
            isSavedInstanceNull = true;
        }
        videoUrlString = "";
        TextView shortDescription = rootView.findViewById(R.id.shortDescription);
        TextView description = rootView.findViewById(R.id.description);
        ImageView stepsThumbnail = rootView.findViewById(R.id.stepsThumbnail);
        mPlayerView = rootView.findViewById(R.id.player);
        rootView.findViewById(R.id.stepsCardView).setBackgroundColor(rgb(191, 216, 89));
        rootView.findViewById(R.id.stepsLinearLayout).setBackgroundColor(rgb(191, 216, 89));
        if ((step.getVideoURL() != null && !step.getVideoURL().isEmpty() && !step.getVideoURL().equals(""))) {
            mPlayerView.setVisibility(View.VISIBLE);
            // Initialize the Media Session.
            initializeMediaSession();
            videoUrlString = step.getVideoURL();
            initializePlayer(Uri.parse(videoUrlString));

        } else if (!step.getThumbnailURL().isEmpty()) {
            mPlayerView.setVisibility(View.GONE);
            Picasso.with(context)
                    .load(step.getThumbnailURL())
                    .placeholder(R.drawable.fruits)
                    .into(stepsThumbnail);
        }

        shortDescription.setText(step.getShortDescription());
        description.setText(step.getDescription());
        mPlayerView.setPlayer(mExoPlayer);
        return rootView;
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(context, TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    /**
     * Initialize ExoPlayer.
     *
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(new ExoPlayer.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                /**
                 * Method that is called when the ExoPlayer state changes. Used to update the MediaSession
                 * PlayBackState to keep in sync, and post the media notification.
                 *
                 * @param playWhenReady true if ExoPlayer is playing, false if it's paused.
                 * @param playbackState int describing the state of ExoPlayer. Can be STATE_READY, STATE_IDLE,
                 *                      STATE_BUFFERING, or STATE_ENDED.
                 */
                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
                        mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                                mExoPlayer.getCurrentPosition(), 1f);
                    } else if ((playbackState == ExoPlayer.STATE_READY)) {
                        mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                                mExoPlayer.getCurrentPosition(), 1f);
                    }
                    mMediaSession.setPlaybackState(mStateBuilder.build());
                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity() {

                }
            });

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(context, "StepsFragment");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
        mExoPlayer.seekTo(mSeekBar);
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;

    }

    /**
     * Release the player when the fragment is paused
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mSeekBar = mExoPlayer.getCurrentPosition();
            releasePlayer();
        }
        mMediaSession.setActive(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isSavedInstanceNull) {
            initializePlayer(Uri.parse(videoUrlString));
            mExoPlayer.seekTo(mSeekBar);
        }
        mMediaSession.setActive(true);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMediaSession.setActive(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SEEKBAR_KEY, mSeekBar);
        outState.putString("videoUrlString", videoUrlString);

    }


    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}
