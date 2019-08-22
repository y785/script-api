package moe.maple.api.script.model.object.data.provider;

/**
 * Harbors the required providers.
 * Created on 8/21/2019.
 */
public interface ProviderHub {
    FaceProvider getFaceProvider();
    HairProvider getHairProvider();
    ItemProvider getItemProvider();
}
