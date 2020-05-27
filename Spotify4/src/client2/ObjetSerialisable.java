package client2;

import java.io.Serializable;
import java.util.ArrayList;

public class ObjetSerialisable implements Serializable {

	ArrayList<String> arrayL ;
	
	public ObjetSerialisable(ArrayList<String> arrayL) {

		this.arrayL = arrayL;
	}
	
	
	public ArrayList<String> getList() {
		return arrayL;
	}
	
}
