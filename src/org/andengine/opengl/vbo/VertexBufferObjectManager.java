package org.andengine.opengl.vbo;

import java.util.ArrayList;
import java.util.HashSet;

import org.andengine.opengl.util.GLState;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 17:48:46 - 08.03.2010
 */
public class VertexBufferObjectManager {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final HashSet<IVertexBufferObject> mVertexBufferObjectsLoaded = new HashSet<IVertexBufferObject>();

	private final ArrayList<IVertexBufferObject> mVertexBufferObjectsToBeUnloaded = new ArrayList<IVertexBufferObject>();

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public synchronized int getHeapMemoryByteSize() {
		int byteSize = 0;
		for (IVertexBufferObject vbo : mVertexBufferObjectsLoaded) {
			byteSize += vbo.getHeapMemoryByteSize();
		}
		return byteSize;
	}

	public synchronized int getNativeHeapMemoryByteSize() {
		int byteSize = 0;
		for (IVertexBufferObject vbo : mVertexBufferObjectsLoaded) {
			byteSize += vbo.getNativeHeapMemoryByteSize();
		}
		return byteSize;
	}

	public synchronized int getGPUHeapMemoryByteSize() {
		int byteSize = 0;
		for (IVertexBufferObject vbo : mVertexBufferObjectsLoaded) {
			byteSize += vbo.getGPUMemoryByteSize();
		}
		return byteSize;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void onCreate() {

	}

	public synchronized void onDestroy() {
		for (IVertexBufferObject vbo : mVertexBufferObjectsLoaded) {
			vbo.setNotLoadedToHardware();
		}

		mVertexBufferObjectsLoaded.clear();
	}

	public synchronized void onVertexBufferObjectLoaded(final IVertexBufferObject pVertexBufferObject) {
		this.mVertexBufferObjectsLoaded.add(pVertexBufferObject);
	}

	public synchronized void onUnloadVertexBufferObject(final IVertexBufferObject pVertexBufferObject) {
		if (this.mVertexBufferObjectsLoaded.remove(pVertexBufferObject)) {
			this.mVertexBufferObjectsToBeUnloaded.add(pVertexBufferObject);
		}
	}

	public synchronized void onReload() {
		for (IVertexBufferObject vbo : mVertexBufferObjectsLoaded) {
			vbo.setNotLoadedToHardware();
		}

		mVertexBufferObjectsLoaded.clear();
	}

	public synchronized void updateVertexBufferObjects(final GLState pGLState) {
		/* Unload pending VertexBufferObjects. */
		for (IVertexBufferObject vbo : mVertexBufferObjectsToBeUnloaded) {
			if (vbo.isLoadedToHardware()) {
			  vbo.unloadFromHardware(pGLState);
			}
			mVertexBufferObjectsLoaded.remove(vbo);
		}
		mVertexBufferObjectsToBeUnloaded.clear();
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
