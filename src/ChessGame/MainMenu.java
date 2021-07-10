//Coded by Brent Butkow

package ChessGame;

import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author butkowb
 */
public class MainMenu extends javax.swing.JFrame {

	static DataBase DB = new DataBase();		//to interact with the database

	static boolean playingwhite = true;			//if the current user is going to play white or black

	public static int LoadedGameNum = 0;		//the number of the current game, to get the correct game from the database

	public static String helpMenu = "";			//the part of the help menu that explains how this program works

	public static String instructionsMenu = "";	//the part of the help menu that explains how chess works

	public static char difficulty = 'E';		//the difficulty of the bot, if it is a singleplayer game

	public static String[] gameNums;			//all the game numbers of games that the current user is playing in

	public static String[] userName1;			//all the usernames of the player playing white in games that the current user is playing in

	public static String[] userName2;			//all the usernames of the player playing black in games that the current user is playing in

	public static String[] whoTurn;				//whos turn it is in the games that the current user is playing in

	public static int[] validNums;				//stores all the gamenumbers where the current user is playing

	public static int[] place;					//converts the index of the game selector combo box into the array element

	public static int counter = 0;				//the number of games the user is currently playing

	public static String FirstName;				//the users encrypted name

	public static String Surname;				//the users encrypted surname

	public static String Email;					//the users encrypted email

	public MainMenu() {

		initComponents();

		//to make sure that the user cannot change the textboxes
		instructionPane.setEditable(false);
		showLeaderboard.setEditable(false);
		CreditShow.setEditable(false);
		SettingsErrors.setEditable(false);
		Difficulty.setEditable(false);
		LoadGame.setEditable(false);
		LoadGameErrors.setEditable(false);
		DetailsShower.setEditable(false);

		Difficulty.setSelectedIndex(1);//show hard difficulty as the default bot difficulty

		showLeaderboard.setText(LogicHelp.showLeaderboard());	//display the leaderboard

		String user = signIn.user;

		//center the username when displaying it on the JFrame by adding spaces
		for (int i = 0; i < Math.ceil(15 - (signIn.user.length() / 2)); i++) {
			user = " " + user;
		}
		UsernameLabel.setText(user);

		//show all the games of the current user
		String[] gameShower = LogicHelp.MainMenu();
		for (int i = 0; i < counter; i++) {
			LoadGame.addItem(gameShower[i]);
		}

		if (counter == 0) {//if the user currently has no saved games

			LoadGame.addItem("There are no games to open");
		}

		//set the instructions panel to be the instructions
		instructionPane.setText(helpMenu + "" + instructionsMenu);

		//display the users personal details
		DetailsShower.setText("Here are your current details:\n"
				+ "\nName:      \t" + encrypt.decryptString(FirstName)
				+ "\nSurname:   \t" + encrypt.decryptString(Surname)
				+ "\nEmail:     \t" + encrypt.decryptString(Email)
		);

		//display the users score
		String[] scores = DB.selectQuery("SELECT Score FROM Users WHERE Username = \"" + signIn.encryptUser1 + "\"");
		ShowScoreLabel.setText("Your score: " + scores[0]);

	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainMenuTab = new javax.swing.JTabbedPane();
        StartGamePanel = new javax.swing.JPanel();
        MultiplayerLabel = new javax.swing.JLabel();
        SingleplayerLabel = new javax.swing.JLabel();
        MultiplayerWhite = new javax.swing.JButton();
        MultiplayerBlack = new javax.swing.JButton();
        SingleplayerBlack = new javax.swing.JButton();
        SingleplayerWhite = new javax.swing.JButton();
        Difficulty = new javax.swing.JComboBox();
        ShowScoreLabel = new javax.swing.JLabel();
        LoadGameLabel = new javax.swing.JLabel();
        LoadGame = new javax.swing.JComboBox();
        StartLoadedGame = new javax.swing.JButton();
        ChessLabel = new javax.swing.JLabel();
        BotDifficultyLabel = new javax.swing.JLabel();
        PlayAsLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        LoadGameErrors = new javax.swing.JTextArea();
        UsernameLabel = new javax.swing.JLabel();
        InstructionsPanel = new javax.swing.JPanel();
        InstructionsScrollPane = new javax.swing.JScrollPane();
        instructionPane = new javax.swing.JTextArea();
        LeaderboardPanel = new javax.swing.JPanel();
        LeaderboardScrollPane = new javax.swing.JScrollPane();
        showLeaderboard = new javax.swing.JTextArea();
        CreditsPanel = new javax.swing.JPanel();
        CreditsScrollPane = new javax.swing.JScrollPane();
        CreditShow = new javax.swing.JTextArea();
        SettingsPanel = new javax.swing.JPanel();
        LogOutButton = new javax.swing.JButton();
        newPassword = new javax.swing.JPasswordField();
        confirmPassword = new javax.swing.JPasswordField();
        NameLabel = new javax.swing.JLabel();
        EmailLabel = new javax.swing.JLabel();
        SurnameLabel = new javax.swing.JLabel();
        newEmail = new javax.swing.JTextField();
        PasswordLabel = new javax.swing.JLabel();
        ConfirmPasswordLabel = new javax.swing.JLabel();
        newName = new javax.swing.JTextField();
        newSurname = new javax.swing.JTextField();
        SettingsLabel = new javax.swing.JLabel();
        changeValues = new javax.swing.JButton();
        SettingsScrollPane = new javax.swing.JScrollPane();
        SettingsErrors = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        DetailsShower = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MainMenuTab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                F1pressed(evt);
            }
        });

        MultiplayerLabel.setText("MULTIPLAYER");

        SingleplayerLabel.setText("SINGLE PLAYER");

        MultiplayerWhite.setText("Play White");
        MultiplayerWhite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MultiplayerWhiteActionPerformed(evt);
            }
        });

        MultiplayerBlack.setText("Play Black");
        MultiplayerBlack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MultiplayerBlackActionPerformed(evt);
            }
        });

        SingleplayerBlack.setText("Play Black");
        SingleplayerBlack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SingleplayerBlackActionPerformed(evt);
            }
        });

        SingleplayerWhite.setText("Play White");
        SingleplayerWhite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SingleplayerWhiteActionPerformed(evt);
            }
        });

        Difficulty.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Easy", "Hard" }));

        ShowScoreLabel.setText("Your Score: 1034");

        LoadGameLabel.setText("LOAD GAME");

        LoadGame.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Choose a Game" }));

        StartLoadedGame.setText("Start Loaded Game");
        StartLoadedGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartLoadedGameActionPerformed(evt);
            }
        });

        ChessLabel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        ChessLabel.setText("CHESS");

        BotDifficultyLabel.setText("Bot Difficulty:");

        PlayAsLabel.setText("Play as:");

        LoadGameErrors.setColumns(20);
        LoadGameErrors.setRows(5);
        jScrollPane1.setViewportView(LoadGameErrors);

        UsernameLabel.setText("             test1");
        UsernameLabel.setToolTipText("");
        UsernameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout StartGamePanelLayout = new javax.swing.GroupLayout(StartGamePanel);
        StartGamePanel.setLayout(StartGamePanelLayout);
        StartGamePanelLayout.setHorizontalGroup(
            StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StartGamePanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StartGamePanelLayout.createSequentialGroup()
                        .addGroup(StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(StartGamePanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(BotDifficultyLabel))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, StartGamePanelLayout.createSequentialGroup()
                                .addGroup(StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(MultiplayerWhite, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(MultiplayerBlack, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, StartGamePanelLayout.createSequentialGroup()
                                        .addComponent(PlayAsLabel)
                                        .addGap(18, 18, 18)
                                        .addGroup(StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(StartLoadedGame, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(LoadGame, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, StartGamePanelLayout.createSequentialGroup()
                                .addComponent(MultiplayerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(152, 152, 152)
                                .addComponent(ShowScoreLabel)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SingleplayerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SingleplayerBlack)
                            .addComponent(SingleplayerWhite)
                            .addComponent(Difficulty, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(107, 107, 107))
                    .addGroup(StartGamePanelLayout.createSequentialGroup()
                        .addGap(262, 262, 262)
                        .addComponent(ChessLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(StartGamePanelLayout.createSequentialGroup()
                .addGroup(StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(StartGamePanelLayout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(LoadGameLabel))
                    .addGroup(StartGamePanelLayout.createSequentialGroup()
                        .addGap(261, 261, 261)
                        .addComponent(UsernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        StartGamePanelLayout.setVerticalGroup(
            StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StartGamePanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(ChessLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(StartGamePanelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MultiplayerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SingleplayerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StartGamePanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UsernameLabel)
                        .addGap(18, 18, 18)
                        .addComponent(ShowScoreLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(StartGamePanelLayout.createSequentialGroup()
                        .addComponent(MultiplayerWhite)
                        .addGap(18, 18, 18)
                        .addComponent(MultiplayerBlack))
                    .addGroup(StartGamePanelLayout.createSequentialGroup()
                        .addComponent(SingleplayerWhite)
                        .addGap(18, 18, 18)
                        .addComponent(SingleplayerBlack)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Difficulty, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotDifficultyLabel))
                .addGap(28, 28, 28)
                .addComponent(LoadGameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(StartGamePanelLayout.createSequentialGroup()
                        .addGroup(StartGamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LoadGame, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PlayAsLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(StartLoadedGame, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(128, Short.MAX_VALUE))
        );

        MainMenuTab.addTab("Start Game", StartGamePanel);

        instructionPane.setColumns(20);
        instructionPane.setRows(5);
        InstructionsScrollPane.setViewportView(instructionPane);

        javax.swing.GroupLayout InstructionsPanelLayout = new javax.swing.GroupLayout(InstructionsPanel);
        InstructionsPanel.setLayout(InstructionsPanelLayout);
        InstructionsPanelLayout.setHorizontalGroup(
            InstructionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InstructionsPanelLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(InstructionsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(220, Short.MAX_VALUE))
        );
        InstructionsPanelLayout.setVerticalGroup(
            InstructionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InstructionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(InstructionsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                .addGap(56, 56, 56))
        );

        MainMenuTab.addTab("Instructions", InstructionsPanel);

        showLeaderboard.setColumns(20);
        showLeaderboard.setRows(5);
        LeaderboardScrollPane.setViewportView(showLeaderboard);

        javax.swing.GroupLayout LeaderboardPanelLayout = new javax.swing.GroupLayout(LeaderboardPanel);
        LeaderboardPanel.setLayout(LeaderboardPanelLayout);
        LeaderboardPanelLayout.setHorizontalGroup(
            LeaderboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeaderboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LeaderboardScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
                .addContainerGap())
        );
        LeaderboardPanelLayout.setVerticalGroup(
            LeaderboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeaderboardPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LeaderboardScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addGap(43, 43, 43))
        );

        MainMenuTab.addTab("Leaderboard", LeaderboardPanel);

        CreditShow.setColumns(20);
        CreditShow.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15)); // NOI18N
        CreditShow.setRows(5);
        CreditShow.setText("This program was designed and created by Brent Butkow for his 2020 \nInformation Technology PAT project. The game chess was designed in India \nwith no known creator. \nCredit goes to Jonathan Utian, Kayla Butkow and Stackoverflow.");
        CreditsScrollPane.setViewportView(CreditShow);

        javax.swing.GroupLayout CreditsPanelLayout = new javax.swing.GroupLayout(CreditsPanel);
        CreditsPanel.setLayout(CreditsPanelLayout);
        CreditsPanelLayout.setHorizontalGroup(
            CreditsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CreditsPanelLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(CreditsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 744, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );
        CreditsPanelLayout.setVerticalGroup(
            CreditsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CreditsPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(CreditsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(394, Short.MAX_VALUE))
        );

        MainMenuTab.addTab("Credits", CreditsPanel);

        LogOutButton.setText("Log Out");
        LogOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutButtonActionPerformed(evt);
            }
        });

        NameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        NameLabel.setText("New Name:");

        EmailLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        EmailLabel.setText("New Email Address:");

        SurnameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SurnameLabel.setText("New Surname:");

        PasswordLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        PasswordLabel.setText("New Password:");

        ConfirmPasswordLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ConfirmPasswordLabel.setText("Confirm Password:");

        SettingsLabel.setText("Did you enter incorrect data or want to change your password? Do so below!");

        changeValues.setText("Confirm Changes");
        changeValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeValuesActionPerformed(evt);
            }
        });

        SettingsErrors.setColumns(20);
        SettingsErrors.setRows(5);
        SettingsErrors.setText("Only Enter the fields above that you\nwant to change. The same naming rules\napply from when creating an account");
        SettingsScrollPane.setViewportView(SettingsErrors);

        DetailsShower.setColumns(20);
        DetailsShower.setRows(5);
        jScrollPane2.setViewportView(DetailsShower);

        javax.swing.GroupLayout SettingsPanelLayout = new javax.swing.GroupLayout(SettingsPanel);
        SettingsPanel.setLayout(SettingsPanelLayout);
        SettingsPanelLayout.setHorizontalGroup(
            SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SettingsPanelLayout.createSequentialGroup()
                .addGap(0, 808, Short.MAX_VALUE)
                .addComponent(LogOutButton))
            .addGroup(SettingsPanelLayout.createSequentialGroup()
                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SettingsPanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(SettingsLabel)
                        .addGap(0, 376, Short.MAX_VALUE))
                    .addGroup(SettingsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SettingsPanelLayout.createSequentialGroup()
                                .addComponent(NameLabel)
                                .addGap(150, 150, 150)
                                .addComponent(newName))
                            .addGroup(SettingsPanelLayout.createSequentialGroup()
                                .addComponent(SurnameLabel)
                                .addGap(130, 130, 130)
                                .addComponent(newSurname))
                            .addGroup(SettingsPanelLayout.createSequentialGroup()
                                .addComponent(PasswordLabel)
                                .addGap(127, 127, 127)
                                .addComponent(newPassword))
                            .addGroup(SettingsPanelLayout.createSequentialGroup()
                                .addComponent(SettingsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jScrollPane2)
                                .addGap(18, 18, 18)
                                .addComponent(changeValues))
                            .addGroup(SettingsPanelLayout.createSequentialGroup()
                                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ConfirmPasswordLabel)
                                    .addComponent(EmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(55, 55, 55)
                                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(newEmail)
                                    .addComponent(confirmPassword))))))
                .addContainerGap())
        );
        SettingsPanelLayout.setVerticalGroup(
            SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SettingsLabel)
                .addGap(28, 28, 28)
                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NameLabel)
                    .addComponent(newName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SurnameLabel)
                    .addComponent(newSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PasswordLabel)
                    .addComponent(newPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ConfirmPasswordLabel)
                    .addComponent(confirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EmailLabel)
                    .addComponent(newEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SettingsPanelLayout.createSequentialGroup()
                        .addComponent(changeValues)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LogOutButton))
                    .addGroup(SettingsPanelLayout.createSequentialGroup()
                        .addComponent(SettingsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(SettingsPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        MainMenuTab.addTab("Settings", SettingsPanel);

        getContentPane().add(MainMenuTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 540));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * Update the user's personal details if the new details for the
	 * requirements
	 */
    private void changeValuesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeValuesActionPerformed

		String name = newName.getText();					//the user's name
		String surname = newSurname.getText();				//the user's surname
		String password = newPassword.getText();			//the user's password
		String confirmPassword1 = confirmPassword.getText();//to check the user's password was inputted correct
		String email = newEmail.getText().toLowerCase();	//the user's email

		String check = LogicHelp.changeValues(name, surname, password, confirmPassword1, email);

		if (check.contains("correct")) {//if there was no error

			//display the correct new details of the user and display the success message
			DetailsShower.setText(check.substring(7));
			SettingsErrors.setText("SUCCESS. Your details have been updated");

		} else {

			SettingsErrors.setText(check);//display the error message
		}

    }//GEN-LAST:event_changeValuesActionPerformed

	/**
	 * call the sign in JFrame and close this JFrame
	 */
    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutButtonActionPerformed

		signIn.inGame = false;//if the user logs out, they are no longer in game
		signIn.user = "";
		signIn helper = new signIn();
		helper.setResizable(false);
		helper.setVisible(true);
		dispose();//close this JFrame

    }//GEN-LAST:event_LogOutButtonActionPerformed

	/**
	 * load the chessgame JFrame with the user playing singleplayer and white
	 */
    private void SingleplayerWhiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SingleplayerWhiteActionPerformed

		//get the bpts difficulty from the difficulty JComboBox
		difficulty = Difficulty.getSelectedItem().toString().charAt(0);
		LoadedGameNum = 0;
		LogicCenter.curTurnW = true;
		LogicHelp.startGame(signIn.user, difficulty + "");
		dispose();//close this JFrame

    }//GEN-LAST:event_SingleplayerWhiteActionPerformed

	/**
	 * load the chessgame JFrame with the user playing singleplayer and black
	 */
    private void SingleplayerBlackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SingleplayerBlackActionPerformed

		//get the bpts difficulty from the difficulty JComboBox
		difficulty = Difficulty.getSelectedItem().toString().charAt(0);
		LoadedGameNum = 0;
		LogicCenter.curTurnW = true;
		LogicHelp.startGame(difficulty + "", signIn.user);
		dispose();//close this JFrame

    }//GEN-LAST:event_SingleplayerBlackActionPerformed

	/**
	 * load the chessgame JFrame with one of the saved games. If the user is
	 * playing another user, make the second user enter his/her password
	 */
    private void StartLoadedGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartLoadedGameActionPerformed

		int temp = LoadGame.getSelectedIndex() - 1;//the index in the comboBox that the user has selected
		String check = LogicHelp.startLoadedGame(temp, place);

		if (check.equals("correct")) {//if there was no error

			LogicCenter.curTurnW = whoTurn[place[temp]].equals("w");
			LoadedGameNum = validNums[temp];
			LogicHelp.startGame(userName1[place[temp]], userName2[place[temp]]);
			dispose();//close this JFrame

		} else {

			LoadGameErrors.setText(check);//display the error message
		}

    }//GEN-LAST:event_StartLoadedGameActionPerformed

	/**
	 * load the sign in JFrame with the user playing multiplayer and white so
	 * that the second player can sign in
	 */
    private void MultiplayerWhiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MultiplayerWhiteActionPerformed

		LoadedGameNum = 0;
		playingwhite = true;
		signIn help = new signIn();
		help.setResizable(false);
		help.setVisible(true);
		dispose();//close this JFrame

    }//GEN-LAST:event_MultiplayerWhiteActionPerformed

	/**
	 * load the sign in JFrame with the user playing multiplayer and black so
	 * that the second player can sign in
	 */
    private void MultiplayerBlackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MultiplayerBlackActionPerformed

		LoadedGameNum = 0;
		playingwhite = false;
		signIn help = new signIn();
		help.setResizable(false);
		help.setVisible(true);
		dispose();//close this JFrame

    }//GEN-LAST:event_MultiplayerBlackActionPerformed

	/**
	 * display a help JOptionPane when the user presses their F1 key
	 */
    private void F1pressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_F1pressed

		int key = evt.getKeyCode();
		if (key == KeyEvent.VK_F1) {//if the key pressed was F1

			JOptionPane.showMessageDialog(null, helpMenu);//dispay the first part of the help menu
			JOptionPane.showMessageDialog(null, instructionsMenu);//display the chess rules
		}

    }//GEN-LAST:event_F1pressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BotDifficultyLabel;
    private javax.swing.JLabel ChessLabel;
    private javax.swing.JLabel ConfirmPasswordLabel;
    private javax.swing.JTextArea CreditShow;
    private javax.swing.JPanel CreditsPanel;
    private javax.swing.JScrollPane CreditsScrollPane;
    private javax.swing.JTextArea DetailsShower;
    private javax.swing.JComboBox Difficulty;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JPanel InstructionsPanel;
    private javax.swing.JScrollPane InstructionsScrollPane;
    private javax.swing.JPanel LeaderboardPanel;
    private javax.swing.JScrollPane LeaderboardScrollPane;
    private javax.swing.JComboBox LoadGame;
    private javax.swing.JTextArea LoadGameErrors;
    private javax.swing.JLabel LoadGameLabel;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JTabbedPane MainMenuTab;
    private javax.swing.JButton MultiplayerBlack;
    private javax.swing.JLabel MultiplayerLabel;
    private javax.swing.JButton MultiplayerWhite;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JLabel PlayAsLabel;
    private javax.swing.JTextArea SettingsErrors;
    private javax.swing.JLabel SettingsLabel;
    private javax.swing.JPanel SettingsPanel;
    private javax.swing.JScrollPane SettingsScrollPane;
    private javax.swing.JLabel ShowScoreLabel;
    private javax.swing.JButton SingleplayerBlack;
    private javax.swing.JLabel SingleplayerLabel;
    private javax.swing.JButton SingleplayerWhite;
    private javax.swing.JPanel StartGamePanel;
    private javax.swing.JButton StartLoadedGame;
    private javax.swing.JLabel SurnameLabel;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JButton changeValues;
    private javax.swing.JPasswordField confirmPassword;
    private javax.swing.JTextArea instructionPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField newEmail;
    private javax.swing.JTextField newName;
    private javax.swing.JPasswordField newPassword;
    private javax.swing.JTextField newSurname;
    private javax.swing.JTextArea showLeaderboard;
    // End of variables declaration//GEN-END:variables

}
//Coded by Brent Butkow
