package project.properties;

import project.exeptions.AlreadyExistsException;

import java.util.ArrayList;
import java.util.List;

public class Estate {


    static List<Estate> estatesList = new ArrayList<>();
    private final String estateName;
    private List<Blok> blocks;


    public Estate(String estateName) {
        this.estateName = estateName;
        this.blocks = new ArrayList<>();
        estatesList.add(this);
    }

    public String getEstateName() {
        return estateName;
    }

    public List<Blok> getBlocks() {
        return blocks;
    }
    public void addBlockToEstate(Blok blok) {
        if(!blocks.contains(blok))
            blocks.add(blok);
    }


    public Blok addBlock(String streetName, String blockNumber, int MAX_FLATS) throws AlreadyExistsException {
        Blok blok = null;

        blok = new Blok(this, streetName, blockNumber, MAX_FLATS);

        if (!blocks.contains(blok)) {
                this.blocks.add(blok);
        }

        return blok;
    }

    public static List<Estate> getEstatesList() {
        return estatesList;
    }
}
