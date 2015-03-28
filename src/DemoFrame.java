import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Construct a frame to add input files
 * @author Ted
 * @param 
 * @return
 */
public class DemoFrame extends JFrame {
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 250;
	
	private static final int AREA_ROWS = 10;
	private static final int AREA_COLUMNS = 30;
	
	//private JLabel input;
	//private JTextField outputField;
	private JButton button;
	private JTextArea resultArea;
	private JFileChooser chooser;
	//private File[] files;
	
	public DemoFrame() {
		resultArea = new JTextArea(AREA_ROWS, AREA_COLUMNS);
		//resultArea.setText("initial\n");
		resultArea.setEditable(false);
		
	//	createTextField();
		createButton();
		createPanel();
	//	createFileChooser();
		chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		JFrame frame = new JFrame();
		chooser.showOpenDialog(frame);
		File[] files = chooser.getSelectedFiles();
		//InputStreamReader reader = new InputStreamReader(new FileInputStream(files[0]));
		try {
			/*for(File file : files) {
				parseInputFile(file);
			}*/
			parseInputFile(files[0]);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
	
	private void parseInputFile (File file) throws IOException {
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
					resultArea.append(substr + "\n");
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
			resultArea.append(num_rec + "\n");
			resultArea.append(num_send + "\n");

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
	
	class addListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
//			InputStreamReader reader = new InputStreamReader(new FileInputStream(files[0]));
		}
	}
	
/*	private void createFileChooser() {
		chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		JFrame frame = new JFrame();
		chooser.showOpenDialog(frame);
		files = chooser.getSelectedFiles();

	}*/
	
	private void createButton() {
		button = new JButton("compute");
		
		ActionListener listener = new addListener();
		button.addActionListener(listener);
	}
	
	private void createPanel() {
		JPanel panel = new JPanel();
		//panel.add(input);
		//panel.add(outputField);
		panel.add(button);
		JScrollPane scrollPane = new JScrollPane(resultArea);
		panel.add(scrollPane);
		add(panel);
	}
}
