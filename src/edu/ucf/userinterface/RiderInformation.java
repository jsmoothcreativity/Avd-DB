package edu.ucf.userinterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.ucf.dao.DAOFactory;
import edu.ucf.racedao.RaceRidersDao;
import edu.ucf.racetransferobjects.RaceRiders;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RiderInformation extends JFrame {

	private JPanel contentPane;
	private DAOFactory sqlFactory = DAOFactory.getDAOFactory(DAOFactory.ORACLE);
	private RaceRidersDao raceRidersDao = sqlFactory.getRaceRidersDAO();
	private RaceRiders raceRider;
	
	private JComboBox<String> riderNameComboBox;
	private JLabel teamNameLabel;
	private JLabel nationalityLabel;
	private JLabel winsLabel;
	private boolean isInitializingComboBox = false;
	private RaceRiderDetails raceRiderDetails;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RiderInformation frame = new RiderInformation();
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
	public RiderInformation() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if (raceRiderDetails != null)
					raceRiderDetails.dispose();
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				if (raceRiderDetails != null)
					raceRiderDetails.dispose();
			}
		});
		initialize();
		initializeRiderNameComboBox();
	}
	


	private void initialize() {
		setTitle("Rider Information");
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
			public void actionPerformed(ActionEvent e) {
				raceRiderDetails = new RaceRiderDetails();
				raceRiderDetails.setVisible(true);
			}
		});
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(addButton);
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				raceRiderDetails = new RaceRiderDetails(raceRider);
				raceRiderDetails.setVisible(true);
			}
		});
		editButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(editButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RaceRiders existingRaceRider = raceRidersDao.findRaceRiders(riderNameComboBox.getSelectedItem().toString());
				
				if (existingRaceRider != null)
				{
					switch(showConfirmDialog())
					{
					case 0:
						boolean result = raceRidersDao.deleteRaceRiders(riderNameComboBox.getSelectedItem().toString());
						
						if (!result)
						{
							String message = new String("Race Rider not deleted due to an error.");
							JOptionPane.showMessageDialog(null, message, "Delete failed", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							String message = new String("Race Rider deleted from Database.");
							JOptionPane.showMessageDialog(null, message);
							refresh();
						}
						break;
					case 1:
						String noMessage = new String("Race Rider not deleted");
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
					String message = new String("Race Rider does not exist.");
					JOptionPane.showMessageDialog(null, message, "Data Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(deleteButton);
		
		JButton refreshButton = new JButton("Reload");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		refreshButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(refreshButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RiderInformation.this.processWindowEvent(new WindowEvent(RiderInformation.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(exitButton);
		
		JPanel infoPanel = new JPanel();
		contentPane.add(infoPanel, BorderLayout.CENTER);
		infoPanel.setLayout(null);
		
		JLabel lblRiderName = new JLabel("Rider Name:");
		lblRiderName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRiderName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRiderName.setBounds(65, 13, 110, 17);
		infoPanel.add(lblRiderName);
		
		riderNameComboBox = new JComboBox<String>();
		riderNameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isInitializingComboBox)
					return;
				
				raceRider = raceRidersDao.findRaceRiders(riderNameComboBox.getSelectedItem().toString());
				teamNameLabel.setText(raceRider.getTeamName());
				nationalityLabel.setText(raceRider.getNationality());
				winsLabel.setText(Integer.toString(raceRider.getNumOfWins()));
			}
		});
		riderNameComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		riderNameComboBox.setBounds(185, 11, 148, 20);
		infoPanel.add(riderNameComboBox);
		
		JLabel lblTeamName = new JLabel("Team Name:");
		lblTeamName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTeamName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTeamName.setBounds(68, 67, 110, 17);
		infoPanel.add(lblTeamName);
		
		teamNameLabel = new JLabel("");
		teamNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		teamNameLabel.setBounds(188, 67, 110, 17);
		infoPanel.add(teamNameLabel);
		
		JLabel lblNationality = new JLabel("Nationality:");
		lblNationality.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNationality.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNationality.setBounds(68, 110, 110, 17);
		infoPanel.add(lblNationality);
		
		nationalityLabel = new JLabel("");
		nationalityLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nationalityLabel.setBounds(188, 110, 110, 17);
		infoPanel.add(nationalityLabel);
		
		JLabel lblNumberOfWins = new JLabel("Number of Wins:");
		lblNumberOfWins.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumberOfWins.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumberOfWins.setBounds(50, 151, 128, 17);
		infoPanel.add(lblNumberOfWins);
		
		winsLabel = new JLabel("");
		winsLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		winsLabel.setBounds(188, 151, 110, 17);
		infoPanel.add(winsLabel);
	}

	private void initializeRiderNameComboBox() {
		isInitializingComboBox = true;
		
		riderNameComboBox.removeAllItems();
		ArrayList<RaceRiders> raceRiderList = (ArrayList<RaceRiders>)raceRidersDao.selectAllRacerRiders();
		int max = raceRiderList.size();
		RaceRiders tempRaceRider;
		
		for (int i = 0; i < max; i++)
		{
			tempRaceRider = raceRiderList.get(i);
			riderNameComboBox.addItem(tempRaceRider.getRiderName());
		}
		
		isInitializingComboBox = false;
	}
	
	private int showConfirmDialog() {
		
		String confirmMessage = new String("Are you sure you want to delete " + riderNameComboBox.getSelectedItem().toString() + " from race rider?");
		int response = JOptionPane.showConfirmDialog(null, confirmMessage);
		
		return response;
	}
	
	private void refresh() {
		initializeRiderNameComboBox();
	}
}
