import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class window extends JFrame implements ActionListener {
	
	public int ledLane = initialWindow.getMatrixSize();
	public int ledColumn=initialWindow.getMatrixSize();
	public JButton[][] led = new JButton[ledLane][ledColumn];
	public JButton clearButton = new JButton("Clear");
	public JButton generateButton = new JButton("Generate initial pattern");
	public JButton nextStepButton = new JButton("Calculate next step");
	public JButton startButton = new JButton("Start");
	public JButton stopButton = new JButton("Stop");
	public JButton selectButton = new JButton("Select initial pattern");
	
	public int[][] matrixRandom = new int[ledLane][ledColumn];
	public int[][] matrixRandomExtended = new int[ledLane+2][ledColumn+2];
	public int[][] matrixSum = new int[ledLane+2][ledColumn+2];
	
   		
	Timer timer = new Timer(); 
	
	
	public long delay = 1*1000;

	public boolean select = false;
	
	public window () {
		
		setDefaultLookAndFeelDecorated(true);
		
		this.setTitle("Game of life");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel();
		Border margins = BorderFactory.createEmptyBorder(10, 20, 20, 10);
		mainPanel.setBorder(margins);
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints mainPanelGBC = new GridBagConstraints();
		mainPanelGBC.insets = new Insets(10, 10, 10, 10);
		
				
		JPanel ioPanel = new JPanel();
		ioPanel.setLayout(new GridBagLayout());
		GridBagConstraints ioPanelGBC = new GridBagConstraints();
		ioPanelGBC.insets = new Insets(5, 5, 5, 5);
		ioPanelGBC.gridy =0;
		ioPanelGBC.gridx =0;
		ioPanel.add(generateButton,ioPanelGBC);
		generateButton.addActionListener(this);
		
		//JButton selectButton = new JButton("Select initial pattern");
		ioPanelGBC.gridy =0;
		ioPanelGBC.gridx =1;
		ioPanel.add(selectButton,ioPanelGBC);
		selectButton.addActionListener(this);
		
		mainPanelGBC.gridy =1;
		mainPanelGBC.gridx=0;
		mainPanel.add(ioPanel, mainPanelGBC);
		
		JPanel ledPanel = new JPanel();
		ledPanel.setLayout(new GridBagLayout());
		GridBagConstraints ledPanelGBC = new GridBagConstraints();
		ledPanelGBC.insets = new Insets(5, 5, 5, 5);
		
		
		
		for(int lane = 0; lane < ledLane; lane++) {
			for (int column = 0; column < ledColumn; column++) {
		led[lane][column] = new JButton();
		led[lane][column].setPreferredSize(new Dimension(80,40));
		led[lane][column].setBackground(Color.RED);
		ledPanelGBC.gridy = lane;
		ledPanelGBC.gridx = column; 
	    ledPanel.add(led[lane][column], ledPanelGBC);
	    led[lane][column].addActionListener(this);
		     }
		}
		
		mainPanelGBC.gridy =2;
		mainPanelGBC.gridx=0;
		mainPanel.add(ledPanel, mainPanelGBC);
		
		JPanel nextStepPanel = new JPanel();
		nextStepPanel.setLayout(new GridBagLayout());
		GridBagConstraints nextStepPanelGBC = new GridBagConstraints();
		nextStepPanelGBC.insets = new Insets(5, 5, 5, 5);
		//JButton nextStepButton = new JButton("NextStep");
		nextStepPanelGBC.gridy =0;
		nextStepPanelGBC.gridx =0;
		nextStepPanel.add(nextStepButton,nextStepPanelGBC);
		nextStepButton.addActionListener(this);
		
		mainPanelGBC.gridy =3;
		mainPanelGBC.gridx=0;
		mainPanel.add(nextStepPanel, mainPanelGBC);
		
		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new GridBagLayout());
		GridBagConstraints actionPanelGBC = new GridBagConstraints();
		actionPanelGBC.insets = new Insets(5, 5, 5, 5);
		//JButton startButton = new JButton("Start");
		actionPanelGBC.gridy =0;
		actionPanelGBC.gridx =0;
		actionPanel.add(startButton,actionPanelGBC);
		startButton.addActionListener(this);
		
		//JButton stopButton = new JButton("Stop");
		actionPanelGBC.gridy =1;
		actionPanelGBC.gridx =0;
		actionPanel.add(stopButton,actionPanelGBC);
		stopButton.addActionListener(this);
		
		mainPanelGBC.gridy =4;
		mainPanelGBC.gridx=0;
		mainPanel.add(actionPanel, mainPanelGBC);
		
		JPanel clearPanel = new JPanel();
		clearPanel.setLayout(new GridBagLayout());
		GridBagConstraints clearPanelGBC = new GridBagConstraints();
		clearPanelGBC.insets = new Insets(5, 5, 5, 5);
		clearPanelGBC.gridy =0;
		clearPanelGBC.gridx =0;
		clearPanel.add(clearButton,clearPanelGBC);
		clearButton.addActionListener(this);
		
		mainPanelGBC.gridy =5;
		mainPanelGBC.gridx=0;
		mainPanel.add(clearPanel, mainPanelGBC);
		
		
		this.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
	}
	
	
	public void timerRun () {
		      this.timer = new Timer();
		      this.timer.scheduleAtFixedRate(new TimerTask() {
			  public void run() {
				matrixCalculation();
				   }
				}, delay, delay);
		 	}
	
	public void timerStop() {
		this.timer.cancel();
		this.timer.purge();
		
	}
	
	public void matrixCalculation() {
		for (int i = 0; i < ledColumn+2; i++) {
			for (int j = 0; j < ledLane+2; j++) {
			matrixRandomExtended[i][j]=0;			
		}
		}
		for (int matrixLane =0; matrixLane < ledLane; matrixLane++) {
			for (int matrixColumn =0; matrixColumn< ledColumn; matrixColumn++) {
				matrixRandomExtended[matrixLane+1][matrixColumn+1]=matrixRandom[matrixLane][matrixColumn];
			}			
				}
		
		for (int matrixLane =1; matrixLane< ledLane+1; matrixLane++) {
			for (int matrixColumn =1; matrixColumn< ledColumn+1; matrixColumn++) {
				 matrixSum[matrixLane][matrixColumn]=matrixRandomExtended[matrixLane-1][matrixColumn-1]+matrixRandomExtended[matrixLane-1][matrixColumn]+matrixRandomExtended[matrixLane-1][matrixColumn+1]+matrixRandomExtended[matrixLane][matrixColumn-1]+matrixRandomExtended[matrixLane][matrixColumn+1]+matrixRandomExtended[matrixLane+1][matrixColumn-1]+matrixRandomExtended[matrixLane+1][matrixColumn]+matrixRandomExtended[matrixLane+1][matrixColumn+1];
			}			
				}
		
		for (int matrixLane =1; matrixLane< ledLane+1; matrixLane++) {
			for (int matrixColumn =1; matrixColumn< ledColumn+1; matrixColumn++) {
				if((matrixRandomExtended[matrixLane][matrixColumn]==1) && ((1 < matrixSum[matrixLane][matrixColumn]) && (matrixSum[matrixLane][matrixColumn]<=3))) {
					matrixRandom[matrixLane-1][matrixColumn-1] = 1;
				}
				else if((matrixRandomExtended[matrixLane][matrixColumn]==1) &&  ((matrixSum[matrixLane][matrixColumn]<=1) || (matrixSum[matrixLane][matrixColumn] > 3))) {
					matrixRandom[matrixLane-1][matrixColumn-1] = 0;
				}
				
				else if((matrixRandomExtended[matrixLane][matrixColumn]==0) && (matrixSum[matrixLane][matrixColumn]==3 )) {
					matrixRandom[matrixLane-1][matrixColumn-1] = 1;
				}

				else if((matrixRandomExtended[matrixLane][matrixColumn]==0) && (matrixSum[matrixLane][matrixColumn]!=3 )) {
					matrixRandom[matrixLane-1][matrixColumn-1] = 0;
				}
			}			
				}
		for (int matrixLane =0; matrixLane< ledLane; matrixLane++) {
			for (int matrixColumn =0; matrixColumn< ledColumn; matrixColumn++) {
		   if (matrixRandom[matrixLane][matrixColumn] == 1) {
			led[matrixLane][matrixColumn].setBackground(Color.GREEN);
		   }
		   else if (matrixRandom[matrixLane][matrixColumn] == 0) {
			led[matrixLane][matrixColumn].setBackground(Color.RED);
		}
		}	
		}
		
	}
	
	
	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getSource() == generateButton) {
		        timerStop();
		 //led[1][1].setBackground(Color.GREEN);
			Random matrixFill = new Random();
			for (int matrixLane =0; matrixLane < ledLane; matrixLane++) {
				for (int matrixColumn =0; matrixColumn < ledColumn; matrixColumn++) {
					matrixRandom[matrixLane][matrixColumn]=matrixFill.nextInt(2);
					if (matrixRandom[matrixLane][matrixColumn] == 1) {
						led[matrixLane][matrixColumn].setBackground(Color.GREEN);
						
					}
				}
			}
			//System.out.println("Generated matrix " + matrixRandom[0][0] + matrixRandom[0][1]+ matrixRandom[0][2]);
			//System.out.println("Generated matrix " + matrixRandom[1][0] + matrixRandom[1][1]+ matrixRandom[1][2]);
			//System.out.println("Generated matrix " + matrixRandom[2][0] + matrixRandom[2][1]+ matrixRandom[2][2]);
		}
		
		else if(evt.getSource() == clearButton) {
			 //Clear field;
			timerStop();
				for (int matrixLane =0; matrixLane < ledLane; matrixLane++) {
					for (int matrixColumn =0; matrixColumn < ledColumn; matrixColumn++) {
						matrixRandom[matrixLane][matrixColumn]=0;
						led[matrixLane][matrixColumn].setBackground(Color.RED);
						
					}
				}
			
			}
		
		else if(evt.getSource() == startButton) {	
			timerStop();
			timerRun();			
					}
		
		else if(evt.getSource() == stopButton) {
			timerStop();
			
		}
		
		else if(evt.getSource() == selectButton) {
			 //Clear field;
			timerStop();
				for (int matrixLane =0; matrixLane < ledLane; matrixLane++) {
					for (int matrixColumn =0; matrixColumn < ledColumn; matrixColumn++) {
						matrixRandom[matrixLane][matrixColumn]=0;
						led[matrixLane][matrixColumn].setBackground(Color.RED);
						
					}
				}
			select = true;
				
				
			}
		
		else if(evt.getSource() == nextStepButton) {
			
			matrixCalculation();
		/*	
			// Generate new matrix of random numbers with 0 at border element positions
			for (int i = 0; i < ledColumn+2; i++) {
				for (int j = 0; j < ledLane+2; j++) {
				matrixRandomExtended[i][j]=0;			
			}
			}
			
			for (int matrixLane =0; matrixLane < ledLane; matrixLane++) {
				for (int matrixColumn =0; matrixColumn< ledColumn; matrixColumn++) {
					matrixRandomExtended[matrixLane+1][matrixColumn+1]=matrixRandom[matrixLane][matrixColumn];
				}			
					}
			//System.out.println("Matrix extended " + matrixRandomExtended[0][0] + matrixRandomExtended[0][1]+ matrixRandomExtended[0][2]+matrixRandomExtended[0][3]+matrixRandomExtended[0][4]);
			//System.out.println("Matrix extended " + matrixRandomExtended[1][0] + matrixRandomExtended[1][1]+ matrixRandomExtended[1][2]+matrixRandomExtended[1][3]+matrixRandomExtended[0][4]);
			//System.out.println("Matrix extended " + matrixRandomExtended[2][0] + matrixRandomExtended[2][1]+ matrixRandomExtended[2][2]+matrixRandomExtended[2][3]+matrixRandomExtended[0][4]);
			//System.out.println("Matrix extended " + matrixRandomExtended[3][0] + matrixRandomExtended[3][1]+ matrixRandomExtended[3][2]+matrixRandomExtended[3][3]+matrixRandomExtended[0][4]);
			
			// Set new color
			for (int matrixLane =1; matrixLane< ledLane+1; matrixLane++) {
				for (int matrixColumn =1; matrixColumn< ledColumn+1; matrixColumn++) {
					 matrixSum[matrixLane][matrixColumn]=matrixRandomExtended[matrixLane-1][matrixColumn-1]+matrixRandomExtended[matrixLane-1][matrixColumn]+matrixRandomExtended[matrixLane-1][matrixColumn+1]+matrixRandomExtended[matrixLane][matrixColumn-1]+matrixRandomExtended[matrixLane][matrixColumn+1]+matrixRandomExtended[matrixLane+1][matrixColumn-1]+matrixRandomExtended[matrixLane+1][matrixColumn]+matrixRandomExtended[matrixLane+1][matrixColumn+1];
				}			
					}
			
			for (int matrixLane =1; matrixLane< ledLane+1; matrixLane++) {
				for (int matrixColumn =1; matrixColumn< ledColumn+1; matrixColumn++) {
					if((matrixRandomExtended[matrixLane][matrixColumn]==1) && ((1 < matrixSum[matrixLane][matrixColumn]) && (matrixSum[matrixLane][matrixColumn]<=3))) {
						matrixRandom[matrixLane-1][matrixColumn-1] = 1;
					}
					else if((matrixRandomExtended[matrixLane][matrixColumn]==1) &&  ((matrixSum[matrixLane][matrixColumn]<=1) || (matrixSum[matrixLane][matrixColumn] > 3))) {
						matrixRandom[matrixLane-1][matrixColumn-1] = 0;
					}
					
					else if((matrixRandomExtended[matrixLane][matrixColumn]==0) && (matrixSum[matrixLane][matrixColumn]==3 )) {
						matrixRandom[matrixLane-1][matrixColumn-1] = 1;
					}
			
					else if((matrixRandomExtended[matrixLane][matrixColumn]==0) && (matrixSum[matrixLane][matrixColumn]!=3 )) {
						matrixRandom[matrixLane-1][matrixColumn-1] = 0;
					}
				}			
					}
			for (int matrixLane =0; matrixLane< ledLane; matrixLane++) {
				for (int matrixColumn =0; matrixColumn< ledColumn; matrixColumn++) {
			   if (matrixRandom[matrixLane][matrixColumn] == 1) {
				led[matrixLane][matrixColumn].setBackground(Color.GREEN);
			   }
			   else if (matrixRandom[matrixLane][matrixColumn] == 0) {
				led[matrixLane][matrixColumn].setBackground(Color.RED);
			}
			}	
			}
			//System.out.println("Next matrix sum " + matrixSum[1][1] + matrixSum[1][2]+ matrixSum[1][3]);
		   // System.out.println("Next matrix sum" + matrixSum[2][1] + matrixSum[2][2]+ matrixSum[2][3]);
			//System.out.println("Next matrix sum" + matrixSum[3][1] + matrixSum[3][2]+ matrixSum[3][3]);
		*/

		
		}
		
		for(int lane = 0; lane < ledLane; lane++) {
			for (int column = 0; column < ledColumn; column++) {
				if (evt.getSource() == led[lane][column]) {
					if(select) {
						if (led[lane][column].getBackground()== Color.RED) {
						led[lane][column].setBackground(Color.GREEN);
						matrixRandom[lane][column] = 1;}
						else if (led[lane][column].getBackground()== Color.GREEN) {
							led[lane][column].setBackground(Color.RED);
							matrixRandom[lane][column] = 0;}
						
					}
				}
			}
		
			}
		}
			
					
				
	
	
	
public static void main(String[] args) {
	window gameOfLifeWindow = new window();
	
}
}


