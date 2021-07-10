//Coded by Brent Butkow

package ChessGame;

/**
 *
 * @author butkowb
 */
public class signUp extends javax.swing.JFrame {

	/**
	 * Creates new form signUp
	 */
	public signUp() {

		initComponents();
		ErrorDisplay.setEditable(false);

		//display the rules for creating an account
		ErrorDisplay.setText("  Usernames must have more than 4 but less than 21 characters, and no spaces are allowed\n\n  "
				+ "Please make sure that your password has:\n  "
				+ "Eight or more characters, at least one number and at least one special character\n\n");
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SignUpLabel = new javax.swing.JLabel();
        NameLabel = new javax.swing.JLabel();
        SurnameLabel = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        UsernameLabel = new javax.swing.JLabel();
        ConfirmPasswordLabel = new javax.swing.JLabel();
        newName = new javax.swing.JTextField();
        newSurname = new javax.swing.JTextField();
        newUsername = new javax.swing.JTextField();
        newAccount = new javax.swing.JToggleButton();
        newPassword = new javax.swing.JPasswordField();
        confirmPassword = new javax.swing.JPasswordField();
        EmailLabel = new javax.swing.JLabel();
        newEmail = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        ErrorDisplay = new javax.swing.JTextArea();
        backBTN = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        SignUpLabel.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        SignUpLabel.setText("           SIGN UP TO PLAY ");

        NameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        NameLabel.setText("Name:");

        SurnameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        SurnameLabel.setText("Surname:");

        PasswordLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        PasswordLabel.setText("Password:");

        UsernameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        UsernameLabel.setText("Username:");

        ConfirmPasswordLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ConfirmPasswordLabel.setText("Confirm Password:");

        newAccount.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        newAccount.setText("Create New Account");
        newAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newAccountActionPerformed(evt);
            }
        });

        EmailLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        EmailLabel.setText("Email Address:");

        ErrorDisplay.setColumns(20);
        ErrorDisplay.setRows(5);
        jScrollPane2.setViewportView(ErrorDisplay);

        backBTN.setText("Go Back");
        backBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(newAccount))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(SignUpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addComponent(backBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(NameLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(PasswordLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(UsernameLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ConfirmPasswordLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SurnameLabel, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(105, 105, 105)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(newSurname, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(newName)
                                    .addComponent(newUsername, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(newPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(confirmPassword)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(EmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(newEmail)))
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SignUpLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NameLabel)
                    .addComponent(newName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SurnameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UsernameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PasswordLabel)
                    .addComponent(newPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ConfirmPasswordLabel)
                    .addComponent(confirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EmailLabel)
                    .addComponent(newEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(backBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * check if all the values entered fit the conventions, and then add them to
	 * the database
	 */
    private void newAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newAccountActionPerformed

		String name = newName.getText();                    //the user's name
		String surname = newSurname.getText();              //the user's surname
		String username = newUsername.getText();            //the user's username
		String password = newPassword.getText();            //the user's password
		String confirmSPassword = confirmPassword.getText();//to check the user's password was inputted correct
		String email = newEmail.getText().toLowerCase();    //the user's email

		String check = LogicHelp.signUp(name, surname, username, password, confirmSPassword, email);

		if (check.equals("close")) {//if there was no error

			backBTNActionPerformed(evt);//press the back button

		} else {

			ErrorDisplay.setText(check);//display the error message
		}

    }//GEN-LAST:event_newAccountActionPerformed

	/**
	 * call the sign in JFrame and close this JFrame
	 */
    private void backBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBTNActionPerformed

		signIn help = new signIn();
		help.setResizable(false);
		help.setVisible(true);
		dispose(); //close this JFrame

    }//GEN-LAST:event_backBTNActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ConfirmPasswordLabel;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JTextArea ErrorDisplay;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JLabel SignUpLabel;
    private javax.swing.JLabel SurnameLabel;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JToggleButton backBTN;
    private javax.swing.JPasswordField confirmPassword;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton newAccount;
    private javax.swing.JTextField newEmail;
    private javax.swing.JTextField newName;
    private javax.swing.JPasswordField newPassword;
    private javax.swing.JTextField newSurname;
    private javax.swing.JTextField newUsername;
    // End of variables declaration//GEN-END:variables

}
//Coded by Brent Butkow
