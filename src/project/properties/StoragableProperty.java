package project.properties;

import project.exeptions.TooManyThingsException;

public abstract class StoragableProperty<T extends Thingable> extends Property {

    protected Storage<Thingable> storage;

    abstract void storeItem(T item) throws TooManyThingsException;
    abstract void retrieveItem(T item);
    public StoragableProperty(Blok blok, int number, double a, double b, double h) {
        super(blok, number, a, b, h);
        this.storage = new Storage<>();
    }

    public StoragableProperty(Blok blok, int number, double volume) {
        super(blok, number, volume);
        this.storage = new Storage<>();
    }

    public Storage<Thingable> getStorage() {
        return storage;
    }

    @Override
    String getPropertyType() {
        return null;
    }

    @Override
    public void releaseProperty() {
        this.storage.cleanStorage();
        this.tenant = null;
    }
}
