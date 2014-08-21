package org.andengine.ui.fragment;

import java.io.IOException;

import org.andengine.entity.scene.Scene;

public abstract class SimpleLayoutGameFragment extends LayoutGameFragment {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    protected abstract void onCreateResources() throws IOException;
    protected abstract Scene onCreateScene() throws IOException;

    @Override
    public final void onCreateResources(final OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        this.onCreateResources();

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public final void onCreateScene(final OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        final Scene scene = this.onCreateScene();

        pOnCreateSceneCallback.onCreateSceneFinished(scene);
    }

    @Override
    public final void onPopulateScene(final Scene pScene, final OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}