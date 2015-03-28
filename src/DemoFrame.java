import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A Graphic User Interface to demonstrate result of input data
 * @author Ted
 */
public class DemoFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7063193797495071710L;
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 250;	
	private static final int AREA_ROWS = 10;
	private static final int AREA_COLUMNS = 30;
	//private JLabel input;
	//private JTextField outputField;
	private JButton computeButton;
	//private JButton drawButton;

	private JTextArea resultArea;
	private JFileChooser chooser;
	private JMenuBar bar;
	private JMenu fileMenu;
	private JMenuItem openItem;
	private File[] files;
	private ArrayList<Double> rssiArray;
	private ArrayList<Double> lossRateArray;
	/**
	 * Construct a GUI to demonstrate result
	 */
	public DemoFrame() {
		resultArea = new JTextArea(AREA_ROWS, AREA_COLUMNS);
		//resultArea.setText("initial\n");
		resultArea.setEditable(false);
	    //createTextField();
		createComputeButton();
		//createDrawButton();
		createMenu();
		createPanel();
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
	
	/**
	 * Parse input .txt file and store rssi value and packet loss rate
	 * in given array 
	 * @param file the input filestream
	 * @param rssiArray ArrayList to store parsed rssi value
	 * @param lossRateArray ArrayList to store parsed packet loss rate value
	 * @throws IOException
	 */
	private void parseInputFile (File file, ArrayList<Double> rssiArray, ArrayList<Double> lossRateArray) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		try {
			String inputLine = "";
			String pattern1 = "rssi=";
			String pattern2 = "received=";
			String pattern3 = " num_packet_sent_by_sender=";
			String num_rec = "";
			String num_send = "";
			while((inputLine = br.readLine()) != null) {
				int index1 = inputLine.indexOf(pattern1);
				if(index1 != -1) {
					String substr = inputLine.substring(index1 + pattern1.length());
					double value = Double.parseDouble(substr);
					rssiArray.add(value);
					//resultArea.append(substr + "\n");
					//resultArea.append(value + "\n");
					continue;
				}
				int index2 = inputLine.indexOf(pattern2);
				if(index2 == -1) {
					continue;
				}
				int index3 = inputLine.indexOf(pattern3);
				if(index3 == -1) {
					continue;
				}
				num_rec = inputLine.substring(index2 + pattern2.length(), index3);
				num_send = inputLine.substring(index3 + pattern3.length());
				
			}
			double lossRate = Double.parseDouble(num_rec) / Double.parseDouble(num_send);
			lossRateArray.add(lossRate);
			//resultArea.append(num_rec + "\n");
			//resultArea.append(num_send + "\n");
			//resultArea.append(lossRate + "\n");
		} finally {
			br.close();
		}
	}
	
	/*private void createTextField() {
		input = new JLabel("input");
		
		final int FIELD_WIDTH = 10;
		outputField = new JTextField(FIELD_WIDTH);
		outputField.setText("new\n");
	}*/
	
	/**
	 * construct a FileChooser Frame to choose input files
	 */
	private void createFileChooser() {
		chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		JFrame frame = new JFrame();
		chooser.showOpenDialog(frame);
		files = chooser.getSelectedFiles();
	}
	
	/**
	 * create compute button
	 * calculate and display the variance and mean value of input files when click button
	 */
	private void createComputeButton() {
		computeButton = new JButton("Compute");
		computeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				rssiArray = new ArrayList<Double>();
				lossRateArray = new ArrayList<Double>();
				try {
					for (File file : files) {
						parseInputFile(file, rssiArray, lossRateArray);
					}
				} catch (IOException exception) {
					exception.printStackTrace();
				}

				double rssiMean = MathUtil.mean(rssiArray);
				double rssiVar = MathUtil.variance(rssiArray);
				double lossRateMean = MathUtil.mean(lossRateArray);
				double lossRateVar = MathUtil.variance(lossRateArray);

				resultArea.setText("");
				resultArea.append("Rssi_Average  " + rssiMean + "\n");
				resultArea.append("Rssi_Variance " + rssiVar + "\n");
				resultArea.append("Packet Loss_Average " + lossRateMean + "\n");
				resultArea.append("Packet Loss_Variance " + lossRateVar + "\n");
				}
			});
	}
	
	/**
	 * create draw button
	 * draw the graphic when click the button
	 
	private void createDrawButton() {
		drawButton = new JButton("Draw");
	}*/
	
	/**
	 * create a Panel hold the txtarea
	 */
	private void createPanel() {
		JPanel panel = new JPanel();
		//panel.add(input);
		//panel.add(outputField);
		panel.add(computeButton);
		//panel.add(drawButton);
		JScrollPane scrollPane = new JScrollPane(resultArea);
		panel.add(scrollPane);
		add(panel);
	}
	
	/**
	 * create a menu
	 * open file chooser frame when click open
	 */
	private void createMenu() {
		bar = new JMenuBar();
		fileMenu = new JMenu("Files");
		openItem = new JMenuItem("open");
		//saveItem = new JMenuItem("save");
		//closeItem = new JMenuItem("close");
		fileMenu.add(openItem);
		//fileMenu.add(saveItem);
		//fileMenu.add(closeItem);
		bar.add(fileMenu);
		setJMenuBar(bar);
		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				createFileChooser();
				resultArea.setText("");
			}
		});
	}
}
