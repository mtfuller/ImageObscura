package org.clevermonkeylabs.obscura.core;

import java.util.ArrayList;

/**
 * Created by Thomas on 10/12/2017.
 */
public class ApplicationModel {
    private ArrayList<ImageModel> images = new ArrayList<>();
    private ImageModel currentImage = null;

    public ApplicationModel() {
    }

    public ImageModel getCurrentImage() {
        return currentImage;
    }

    public int size() {
        return images.size();
    }

    public boolean isEmpty() {
        return images.isEmpty();
    }

    public boolean contains(Object o) {
        return images.contains(o);
    }

    public boolean addImage(ImageModel imageModel) {
        return images.add(imageModel);
    }

    public boolean removeImage(Object o) {
        return images.remove(o);
    }

    public void clear() {
        images.clear();
    }
}
