import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class initialWindow extends JFrame implements ActionListener {
	
	public JButton insetButton = new JButton("Set field size and start the game");
	public static int matrixSize1;
	
	public initialWindow () {
		
		setDefaultLookAndFeelDecorated(true);
		
		this.setTitle("Game of life");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		Border margins = BorderFactory.createEmptyBorder(10, 20, 20, 10);
		mainPanel.setBorder(margins);
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints mainPanelGBC = new GridBagConstraints();
		mainPanelGBC.insets = new Insets(10, 10, 10, 10);
		
				
		JPanel setPanel = new JPanel();
		setPanel.setLayout(new GridBagLayout());
		GridBagConstraints setPanelGBC = new GridBagConstraints();
		setPanelGBC.insets = new Insets(5, 5, 5, 5);
		setPanelGBC.gridy =0;
		setPanelGBC.gridx =2;
		setPanel.add(insetButton,setPanelGBC);
		insetButton.addActionListener(this);
		
		JLabel label = new JLabel("Matrix size(3-10)");
		setPanelGBC.gridy =0;
		setPanelGBC.gridx =0;
		setPanel.add(label,setPanelGBC);
		//
		String[] size = {"3","4","5","6","7","8","9","10"};
		
		
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox box = (JComboBox)e.getSource();
                        String item = (String)box.getSelectedItem();
                        String matrixSizeString = item;
                		matrixSize1 = Integer.parseInt(matrixSizeString);
            }
		};
		JComboBox<Integer> list = new JComboBox(size);
		setPanelGBC.gridy =0;
		setPanelGBC.gridx =1;
		setPanel.add(list,setPanelGBC);
		list.addActionListener(actionListener);
		
		this.add(setPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
}
public void actionPerformed(ActionEvent e) {
	
	
	this.setVisible(false);
	this.dispose();
	String[] args={};
    window.main(args);
    getMatrixSize();
    System.out.println(matrixSize1);
   }
   
    
	


 public static int getMatrixSize() {
	 	  return matrixSize1;
 }
	 
	
	public static void main(String[] args) {
		initialWindow gameOfLifeWindow0 = new initialWindow();
		
	}

	
	
}
