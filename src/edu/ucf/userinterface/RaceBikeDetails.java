package edu.ucf.userinterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.ucf.dao.DAOFactory;
import edu.ucf.racedao.RaceBikesDao;
import edu.ucf.racetransferobjects.RaceBikes;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class RaceBikeDetails extends JFrame {

	private JPanel contentPane;
	private JTextField bikeNameTextField;
	private JTextField countryTextField;
	private JTextField costTextField;
	private DAOFactory sqlFactory = DAOFactory.getDAOFactory(DAOFactory.ORACLE);
	private RaceBikesDao raceBikesDao = sqlFactory.getRaceBikesDAO();
	private RaceBikes newRaceBike, editedRaceBike;
	private JPanel buttonPanel;
	private JButton exitButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RaceBikeDetails frame = new RaceBikeDetails();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RaceBikeDetails() {
		initialize();
		RaceBikeDetails.this.setTitle("Add new bike");
		JButton saveButton = new JButton("Save");
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(saveButton);
		buttonPanel.add(exitButton);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (bikeNameTextField.getText().isEmpty())
				{
					String message = new String("Please enter a bike name.");
					JOptionPane.showMessageDialog(null, message, "Data Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try
				{
					if (costTextField.getText().isEmpty())
						costTextField.setText("0");
					
					int checkCost = Integer.parseInt(costTextField.getText());
				}
				catch (NumberFormatException ex)
				{
					String message = new String("The cost must be a valid number >= 0.");
					JOptionPane.showMessageDialog(null, message, "Data Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
								
				newRaceBike = new RaceBikes(
						bikeNameTextField.getText(),
						countryTextField.getText(),
						Integer.parseInt(costTextField.getText()));
				RaceBikes existingRaceBike = raceBikesDao.findRaceBikes(newRaceBike.getBikeName());
				
				if (existingRaceBike != null)
				{
					String message = new String("Race Bike alread exist.");
					JOptionPane.showMessageDialog(null, message, "Data Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					boolean result = raceBikesDao.insertRaceBike(newRaceBike);
					
					if (!result) {
						String message = new String("Race Bike not added due to an error.");
						JOptionPane.showMessageDialog(null, message, "Insert failed", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						String message = new String("Race Bike added to the DataBase.");
						JOptionPane.showMessageDialog(null, message);
						RaceBikeDetails.this.processWindowEvent(new WindowEvent(RaceBikeDetails.this, WindowEvent.WINDOW_CLOSING));
					}
				}
			}
		});	
	}
	
	public RaceBikeDetails(RaceBikes raceBike)
	{
		initialize();
		RaceBikeDetails.this.setTitle("Edit bike");
		bikeNameTextField.setText(raceBike.getBikeName());
		bikeNameTextField.enable(false);
		countryTextField.setText(raceBike.getCountryOfOrigin());
		costTextField.setText(Integer.toString(raceBike.getCost()));
		JButton editButton = new JButton("Save Edits");
		editButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(editButton);
		buttonPanel.add(exitButton);
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				editedRaceBike = new RaceBikes(
						bikeNameTextField.getText(),
						countryTextField.getText(),
						Integer.parseInt(costTextField.getText()));
				RaceBikes existingRaceBike = raceBikesDao.findRaceBikes(editedRaceBike.getBikeName());
				
				if (existingRaceBike != null)
				{
					boolean result = raceBikesDao.updateRaceBikes(editedRaceBike);
					
					if (!result) {
						String message = new String("Race Bike not updated due to an error.");
						JOptionPane.showMessageDialog(null, message, "Update failed", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						String message = new String("Race Bike updated in the Database.");
						JOptionPane.showMessageDialog(null, message);
						RaceBikeDetails.this.processWindowEvent(new WindowEvent(RaceBikeDetails.this, WindowEvent.WINDOW_CLOSING));
					}
				}
				else
				{
					String message = new String("Race Bike does not exist.");
					JOptionPane.showMessageDialog(null, message, "Data Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	private void initialize() {
		setTitle("Race Bike Details");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RaceBikeDetails.this.processWindowEvent(new WindowEvent(RaceBikeDetails.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JPanel detailsPanel = new JPanel();
		contentPane.add(detailsPanel, BorderLayout.CENTER);
		detailsPanel.setLayout(null);
		
		JLabel label = new JLabel("Bike Name:");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(49, 35, 110, 17);
		detailsPanel.add(label);
		
		JLabel label_1 = new JLabel("Bike Country:");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBounds(49, 94, 110, 17);
		detailsPanel.add(label_1);
		
		JLabel label_2 = new JLabel("Cost:");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_2.setBounds(49, 156, 110, 17);
		detailsPanel.add(label_2);
		
		bikeNameTextField = new JTextField();
		bikeNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bikeNameTextField.setBounds(169, 35, 154, 20);
		detailsPanel.add(bikeNameTextField);
		bikeNameTextField.setColumns(10);
		
		countryTextField = new JTextField();
		countryTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		countryTextField.setColumns(10);
		countryTextField.setBounds(169, 94, 154, 20);
		detailsPanel.add(countryTextField);
		
		costTextField = new JTextField();
		costTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		costTextField.setColumns(10);
		costTextField.setBounds(169, 156, 154, 20);
		detailsPanel.add(costTextField);
	}
}
