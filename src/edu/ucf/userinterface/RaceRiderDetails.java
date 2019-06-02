package edu.ucf.userinterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.ucf.dao.DAOFactory;
import edu.ucf.racedao.RaceRidersDao;
import edu.ucf.racedao.RaceTeamsDao;
import edu.ucf.racetransferobjects.RaceRiders;
import edu.ucf.racetransferobjects.RaceTeams;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class RaceRiderDetails extends JFrame {

	private JPanel contentPane;
	private DAOFactory sqlFactory = DAOFactory.getDAOFactory(DAOFactory.ORACLE);
	private RaceRidersDao raceRidersDao = sqlFactory.getRaceRidersDAO();
	private RaceTeamsDao raceTeamsDao = sqlFactory.getRaceTeamsDAO();
	private RaceRiders newRaceRider, editedRaceRider;
	private JTextField riderNameTextField;
	private JTextField nationalityTextField;
	private JTextField winsTextField;
	private JPanel buttonPanel;
	private JButton exitButton;
	private JComboBox teamNameComboBox;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RaceRiderDetails frame = new RaceRiderDetails();
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
	public RaceRiderDetails() {
		initialize();
		RaceRiderDetails.this.setTitle("Add new rider");
		JButton saveButton = new JButton("Save");
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(saveButton);
		buttonPanel.add(exitButton);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (riderNameTextField.getText().isEmpty())
				{
					String message = new String("Please enter a rider name.");
					JOptionPane.showMessageDialog(null, message, "Data Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try
				{
					if (winsTextField.getText().isEmpty())
						winsTextField.setText("0");
					
					int checkWins = Integer.parseInt(winsTextField.getText());
				}
				catch (NumberFormatException ex)
				{
					String message = new String("Wins must be a valid number >= 0.");
					JOptionPane.showMessageDialog(null, message, "Data Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				newRaceRider = new RaceRiders(
						riderNameTextField.getText(),
						teamNameComboBox.getSelectedItem().toString(),
						nationalityTextField.getText(),
						Integer.parseInt(winsTextField.getText()));
				RaceRiders existingRaceRider = raceRidersDao.findRaceRiders(newRaceRider.getRiderName());
				
				if (existingRaceRider != null)
				{
					String message = new String("Race Rider alread exist.");
					JOptionPane.showMessageDialog(null, message, "Data Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					boolean result = raceRidersDao.insertRaceRiders(newRaceRider);
					
					if (!result)
					{
						String message = new String("Race Rider not added due to an error.");
						JOptionPane.showMessageDialog(null, message, "Insert failed", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						String message = new String("Race Rider added to the DataBase.");
						JOptionPane.showMessageDialog(null, message);
						RaceRiderDetails.this.processWindowEvent(new WindowEvent(RaceRiderDetails.this, WindowEvent.WINDOW_CLOSING));
					}
				}
			}
		});
	}
	
	public RaceRiderDetails(RaceRiders raceRider) {
		initialize();
		RaceRiderDetails.this.setTitle("Edit rider");
		riderNameTextField.setText(raceRider.getRiderName());
		riderNameTextField.enable(false);
		teamNameComboBox.setSelectedItem(raceRider.getTeamName());
		nationalityTextField.setText(raceRider.getNationality());
		winsTextField.setText(Integer.toString(raceRider.getNumOfWins()));
		JButton editButton = new JButton("Save Edits");
		editButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPanel.add(editButton);
		buttonPanel.add(exitButton);
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				editedRaceRider = new RaceRiders(
						riderNameTextField.getText(),
						teamNameComboBox.getSelectedItem().toString(),
						nationalityTextField.getText(),
						Integer.parseInt(winsTextField.getText()));
				RaceRiders existingRaceRider = raceRidersDao.findRaceRiders(editedRaceRider.getRiderName());
				
				if (existingRaceRider != null)
				{
					boolean result = raceRidersDao.updateRaceRiders(editedRaceRider);
					
					if (!result) {
						String message = new String("Race Rider not updated due to an error.");
						JOptionPane.showMessageDialog(null, message, "Update failed", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						String message = new String("Race Rider updated in the Database.");
						JOptionPane.showMessageDialog(null, message);
						RaceRiderDetails.this.processWindowEvent(new WindowEvent(RaceRiderDetails.this, WindowEvent.WINDOW_CLOSING));
					}
				}
				else
				{
					String message = new String("Race Rider does not exist.");
					JOptionPane.showMessageDialog(null, message, "Data Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		JPanel detailsPanel = new JPanel();
		contentPane.add(detailsPanel, BorderLayout.CENTER);
		detailsPanel.setLayout(null);
		
		JLabel lblRiderName = new JLabel("Rider Name:");
		lblRiderName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRiderName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRiderName.setBounds(58, 11, 110, 17);
		detailsPanel.add(lblRiderName);
		
		riderNameTextField = new JTextField();
		riderNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		riderNameTextField.setColumns(10);
		riderNameTextField.setBounds(178, 11, 154, 20);
		detailsPanel.add(riderNameTextField);
		
		JLabel lblTeamName = new JLabel("Team Name:");
		lblTeamName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTeamName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTeamName.setBounds(58, 65, 110, 17);
		detailsPanel.add(lblTeamName);
		
		JLabel lblNationality = new JLabel("Nationality:");
		lblNationality.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNationality.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNationality.setBounds(58, 117, 110, 17);
		detailsPanel.add(lblNationality);
		
		nationalityTextField = new JTextField();
		nationalityTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nationalityTextField.setColumns(10);
		nationalityTextField.setBounds(178, 117, 154, 20);
		detailsPanel.add(nationalityTextField);
		
		JLabel lblNumberOfWins = new JLabel("Number of Wins:");
		lblNumberOfWins.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumberOfWins.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumberOfWins.setBounds(58, 173, 110, 17);
		detailsPanel.add(lblNumberOfWins);
		
		winsTextField = new JTextField();
		winsTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		winsTextField.setColumns(10);
		winsTextField.setBounds(178, 173, 154, 20);
		detailsPanel.add(winsTextField);
		
		teamNameComboBox = new JComboBox();
		teamNameComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		teamNameComboBox.setBounds(178, 65, 154, 20);
		detailsPanel.add(teamNameComboBox);
		initializeTeamNameComboBox();
		
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RaceRiderDetails.this.processWindowEvent(new WindowEvent(RaceRiderDetails.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
	}
	
	private void initializeTeamNameComboBox() {
		
		teamNameComboBox.removeAllItems();
		ArrayList<RaceTeams> raceTeamList = (ArrayList<RaceTeams>)raceTeamsDao.selectAllRaceTeams();
		int max = raceTeamList.size();
		RaceTeams tempRaceTeam;
		
		for (int i = 0; i < max; i++)
		{
			tempRaceTeam = raceTeamList.get(i);
			teamNameComboBox.addItem(tempRaceTeam.getTeamName());
		}
	}
}
