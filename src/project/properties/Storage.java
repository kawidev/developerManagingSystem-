package project.properties;

import project.interfaces.Measureable;

import java.util.ArrayList;
import java.util.List;

public class Storage<T extends Thingable> implements Measureable {


    private List<T> items;
        private double storageVolume;

        public Storage() {
            this.items = new ArrayList<>();
        }

        public void addItem(T item) {
            items.add(item);
            this.storageVolume += item.getVolume();
            item.setAsStorage(true);
        }
        public void removeItem(T item) {
            if(items.contains(item)) {
                items.remove(item);
                this.storageVolume -= item.getVolume();
                item.setAsStorage(false);
            } else System.out.println("Nie ma takiego przedmiotu");
        }

        public void cleanStorage() {
            this.storageVolume = 0.0;
            items.clear();
        }

        public List<T> getItems() {
            return items;
        }

        public boolean contains(T item) {
            return items.contains(item);
        }

    @Override
    public double getVolume() {
        return storageVolume;
    }

}
