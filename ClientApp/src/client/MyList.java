package client;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * This class implements method to manage functions to create list from each
 * client
 * 
 * @author Quentin Beeckmans - Matthieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class MyList {

	private ArrayList<String> list;

	/**
	 * Allows to create an custom arrayList with adding port listener, l'ip from
	 * client server and absolute path from each audio
	 * 
	 * @param Dialogue object
	 */
	synchronized public void sendFileList(Dialogue d) {
		ArrayList<String> temp = new ArrayList<String>();
		list = getArrayListMusics();

		if (!list.isEmpty()) {
			for (String item : list) {
				temp.add(d.getPort() + ";" + d.getIp() + ";" + item);
			}
			d.sendObject(temp);
		}
	}

	/**
	 * This method returns ArrayList list
	 * 
	 * @return ArrayList
	 */
	public ArrayList<String> getMyList() {

		return list;

	}

	/**
	 * This method returns an ArrayList from a directory
	 * 
	 * @return ArrayList
	 */
	public ArrayList<String> getArrayListMusics() {
		ArrayList<String> arrayTemp = new ArrayList<String>();

		String temp = choosePathDirectory();
		File[] files = new File(temp).listFiles();

		for (File file : files) {
			if (file.isFile()) {
				arrayTemp.add(file.getAbsolutePath());
			}
		}

		return arrayTemp;
	}

	/**
	 * This method allows to open a JFileChooser and get back path from directories
	 * only
	 * 
	 * @return
	 */
	private String choosePathDirectory() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		JFrame jframeChooser = new JFrame();
		jframeChooser.setAlwaysOnTop(true);
		int retour = chooser.showOpenDialog(jframeChooser);

		String directoryPath;

		while (retour != JFileChooser.APPROVE_OPTION) {
			System.out.println("Choose a folder to share music !");

			retour = chooser.showOpenDialog(jframeChooser);
		}

		directoryPath = chooser.getSelectedFile().getAbsolutePath();
		return directoryPath;
	}

}
