package edu.ucf.userinterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import edu.ucf.dao.DAOFactory;
import edu.ucf.racedao.RaceBikesDao;
import edu.ucf.racetransferobjects.RaceBikes;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;

public class BikeInformation extends JFrame {

	private JPanel contentPane;
	private DAOFactory sqlFactory = DAOFactory.getDAOFactory(DAOFactory.ORACLE);
	private RaceBikesDao raceBikesDao = sqlFactory.getRaceBikesDAO();
	private RaceBikes raceBike;
	private JLabel bikeCountryLabel;
	private JLabel costLabel;	
	private JComboBox<String> bikeNameComboBox;
	private boolean isInitializingComboBox = false;
	private RaceBikeDetails raceBikeDetails;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BikeInformation frame = new BikeInformation();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public BikeInformation()
	{
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if (raceBikeDetails != null)
					raceBikeDetails.dispose();
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				if (raceBikeDetails != null)
					raceBikeDetails.dispose();
			}
		});
		createFrame();
		initializeBikeNameComboBox();
	}

	private void createFrame() {
		setTitle("Bike Information");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				raceBikeDetails = new RaceBikeDetails();
				raceBikeDetails.setVisible(true);
			}
		});
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(addButton);
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				raceBikeDetails = new RaceBikeDetails(raceBike);
				raceBikeDetails.setVisible(true);
			}
		});
		editButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(editButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RaceBikes existingRaceBike = raceBikesDao.findRaceBikes(bikeNameComboBox.getSelectedItem().toString());
				
				if (existingRaceBike != null)
				{
					
					switch(showConfirmDialog())
					{
					case 0:
						boolean result = raceBikesDao.deleteRaceBikes(bikeNameComboBox.getSelectedItem().toString());
						
						if (!result) {
							String message = new String("Race Bike not deleted due to an error.");
							JOptionPane.showMessageDialog(null, message, "Delete failed", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							String message = new String("Race Bike deleted from Database.");
							JOptionPane.showMessageDialog(null, message);
							refresh();
						}
						break;
					case 1:
						String noMessage = new String("Race Bike not deleted");
						JOptionPane.showMessageDialog(null, noMessage);
						break;
					case 2:
						String cancelMessage = new String("Deletion canceled");
						JOptionPane.showMessageDialog(null, cancelMessage);
						break;
					}
				}
				else
				{
					String message = new String("Race Bike does not exist.");
					JOptionPane.showMessageDialog(null, message, "Data Error", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(deleteButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BikeInformation.this.processWindowEvent(new WindowEvent(BikeInformation.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		JButton btnRefresh = new JButton("Reload");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(btnRefresh);
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(exitButton);
		
		JPanel infoPanel = new JPanel();
		contentPane.add(infoPanel, BorderLayout.CENTER);
		infoPanel.setLayout(null);
		
		JLabel lblBikeName = new JLabel("Bike Name:");
		lblBikeName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBikeName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBikeName.setBounds(65, 29, 110, 17);
		infoPanel.add(lblBikeName);
		
		bikeNameComboBox = new JComboBox<String>();
		bikeNameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isInitializingComboBox)
					return;
				
				raceBike = raceBikesDao.findRaceBikes(bikeNameComboBox.getSelectedItem().toString());
				bikeCountryLabel.setText(raceBike.getCountryOfOrigin());
				costLabel.setText(Integer.toString(raceBike.getCost()));
			}
		});
		bikeNameComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bikeNameComboBox.setBounds(185, 27, 148, 20);
		infoPanel.add(bikeNameComboBox);
		
		JLabel lblBikeCountry = new JLabel("Bike Country:");
		lblBikeCountry.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBikeCountry.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBikeCountry.setBounds(65, 88, 110, 17);
		infoPanel.add(lblBikeCountry);
		
		bikeCountryLabel = new JLabel("");
		bikeCountryLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bikeCountryLabel.setBounds(185, 88, 110, 17);
		infoPanel.add(bikeCountryLabel);
		
		JLabel lblCost = new JLabel("Cost:");
		lblCost.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCost.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCost.setBounds(65, 150, 110, 17);
		infoPanel.add(lblCost);
		
		costLabel = new JLabel("");
		costLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		costLabel.setBounds(185, 150, 110, 17);
		infoPanel.add(costLabel);
	}
	
	private void initializeBikeNameComboBox() {
		
		isInitializingComboBox = true;
		
		bikeNameComboBox.removeAllItems();
		ArrayList<RaceBikes> raceBikeList = (ArrayList<RaceBikes>)raceBikesDao.selectAllRaceBikes();
		int max = raceBikeList.size();
		RaceBikes tempRaceBike;
		
		for (int i = 0; i < max; i++)
		{
			tempRaceBike = raceBikeList.get(i);
			bikeNameComboBox.addItem(tempRaceBike.getBikeName());
		}
		isInitializingComboBox = false;
	}
	
	private int showConfirmDialog() {
		
		String confirmMessage = new String("Are you sure you want to delete the " + bikeNameComboBox.getSelectedItem().toString() + " race bike?");
		int response = JOptionPane.showConfirmDialog(null, confirmMessage);
		
		return response;
	}
	
	private void refresh() {
		initializeBikeNameComboBox();
	}
}
