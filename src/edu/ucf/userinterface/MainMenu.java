package edu.ucf.userinterface;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import edu.ucf.dao.DAOFactory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu {

	private JFrame frmRacingMainMenu;
	private BikeInformation bikeInfo;
	private RiderInformation riderInfo;
	private TeamInformation teamInfo;
	private WinnerInformation winnerInfo;
	
	// Database connection class variables
	private Connection con = null;
	private ResultSet rs = null;
	private Statement stmt = null;
	private final String USER_NAME = "SYSTEM";
	private final String PASSWORD = "mr_smooth504";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frmRacingMainMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRacingMainMenu = new JFrame();
		frmRacingMainMenu.setTitle("Racing Main Menu");
		frmRacingMainMenu.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if (bikeInfo != null)
					bikeInfo.dispose();
				if (riderInfo != null)
					riderInfo.dispose();
				if (teamInfo != null)
					teamInfo.dispose();
				if (winnerInfo != null)
					winnerInfo.dispose();
			}
		});
		frmRacingMainMenu.setBounds(100, 100, 450, 300);
		frmRacingMainMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel headerLabel = new JLabel();
		headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		headerLabel.setText("Racing Maintenance");
		frmRacingMainMenu.getContentPane().add(headerLabel, BorderLayout.NORTH);
		
		JPanel mainMenuPanel = new JPanel();
		frmRacingMainMenu.getContentPane().add(mainMenuPanel, BorderLayout.CENTER);
		mainMenuPanel.setLayout(null);
		
		JButton bikeButton = new JButton("Bike");
		bikeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				bikeInfo = new BikeInformation();
				bikeInfo.setVisible(true);
			}
		});
		bikeButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bikeButton.setBounds(10, 28, 89, 23);
		mainMenuPanel.add(bikeButton);
		
		JLabel bikeLabel = new JLabel("Add, Edit, Delete Bike Information");
		bikeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		bikeLabel.setBounds(109, 28, 240, 23);
		mainMenuPanel.add(bikeLabel);
		
		JButton riderButton = new JButton("Rider");
		riderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				riderInfo = new RiderInformation();
				riderInfo.setVisible(true);
			}
		});
		riderButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		riderButton.setBounds(10, 77, 89, 23);
		mainMenuPanel.add(riderButton);
		
		JLabel riderLabel = new JLabel("Add, Edit, Delete Rider Information");
		riderLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		riderLabel.setBounds(109, 77, 240, 23);
		mainMenuPanel.add(riderLabel);
		
		JButton teamButton = new JButton("Team");
		teamButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				teamInfo = new TeamInformation();
				teamInfo.setVisible(true);
			}
		});
		teamButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		teamButton.setBounds(10, 124, 89, 23);
		mainMenuPanel.add(teamButton);
		
		JLabel teamLabel = new JLabel("Add, Edit, Delete Team Information");
		teamLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		teamLabel.setBounds(109, 124, 240, 23);
		mainMenuPanel.add(teamLabel);
		
		JButton winnerButton = new JButton("Winner");
		winnerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				winnerInfo = new WinnerInformation();
				winnerInfo.setVisible(true);
			}
		});
		winnerButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		winnerButton.setBounds(10, 171, 89, 23);
		mainMenuPanel.add(winnerButton);
		
		JLabel winnerLabel = new JLabel("Add, Edit, Delete Winner Information");
		winnerLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		winnerLabel.setBounds(109, 171, 269, 23);
		mainMenuPanel.add(winnerLabel);
	}
}
