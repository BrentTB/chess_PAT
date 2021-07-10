//Coded by Brent Butkow

package ChessGame;

/**
 *
 * @author butkowb
 */
public class signIn extends javax.swing.JFrame {

	public static String user = "";			//decrypted version of current user's username

	public static String encryptUser1 = "";	//the encrypted username stored in the database of user 1

	public static String encryptUser2 = "";	//the encrypted username stored in the database of user 2

	public static boolean inGame = false;	//indication of if this sign in is for a multiplayer game or a normal sign in

	/**
	 * Creates new form signIn
	 */
	public signIn() {

		initComponents();

		//if this sign in is for a multipayer game, let the user go back to the main menu
		backButton.setVisible(inGame);

	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        signinLabel = new javax.swing.JLabel();
        btnSignIn = new javax.swing.JToggleButton();
        btnSignUp = new javax.swing.JToggleButton();
        SignUpInstructionsLabel = new javax.swing.JLabel();
        UsernameLabel = new javax.swing.JLabel();
        PasswordLabel = new javax.swing.JLabel();
        usernameEnter = new javax.swing.JTextField();
        ErrorCheck = new javax.swing.JLabel();
        passwordEnter = new javax.swing.JPasswordField();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        signinLabel.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        signinLabel.setText(" SIGN IN TO PLAY  ");

        btnSignIn.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        btnSignIn.setText("Sign In");
        btnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignInActionPerformed(evt);
            }
        });

        btnSignUp.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        btnSignUp.setText("Sign Up");
        btnSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpActionPerformed(evt);
            }
        });

        SignUpInstructionsLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SignUpInstructionsLabel.setText("Don't have an account? Sign Up below!");

        UsernameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        UsernameLabel.setText("Username:");

        PasswordLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        PasswordLabel.setText("Password:");

        backButton.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        backButton.setText("Go Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSignUp)
                        .addGap(32, 32, 32)
                        .addComponent(backButton)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ErrorCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(135, 135, 135))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnSignIn)
                        .addGap(41, 41, 41)
                        .addComponent(SignUpInstructionsLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UsernameLabel)
                            .addComponent(PasswordLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usernameEnter, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(passwordEnter))))
                .addContainerGap(78, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(signinLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {passwordEnter, usernameEnter});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(signinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UsernameLabel)
                    .addComponent(usernameEnter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PasswordLabel)
                    .addComponent(passwordEnter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ErrorCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SignUpInstructionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSignIn))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSignUp)
                    .addComponent(backButton))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {passwordEnter, usernameEnter});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * check if there is a user with the entered values, and if so sign in with
	 * those credentials
	 */
    private void btnSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignInActionPerformed

		String username1 = usernameEnter.getText();     //the user's username
		String password1 = passwordEnter.getText();     //the users password

		//stores whether the sign in was succesful, or the error message
		String check = LogicHelp.signIn(username1, password1);

		if (check.equals("close")) {//if there was no error

			dispose();//close this JFrame
		} else {

			ErrorCheck.setText(check);//display the error message
		}

    }//GEN-LAST:event_btnSignInActionPerformed

	/**
	 * call the Sign Up JFrame and close this JFrame
	 */
    private void btnSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpActionPerformed

		signUp helper = new signUp();
		helper.setResizable(false);
		helper.setVisible(true);
		dispose();//close this JFrame

    }//GEN-LAST:event_btnSignUpActionPerformed

	/**
	 * call the Main Menu JFrame and close this JFrame
	 */
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed

		MainMenu help = new MainMenu();
		help.setResizable(false);
		help.setVisible(true);
		dispose();//close this JFrame

    }//GEN-LAST:event_backButtonActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/*
		 * Set the Nimbus look and feel
		 */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {

			System.out.println("Error: " + ex);//error message
		}
		//</editor-fold>
		//</editor-fold>

		/*
		 * Create and display the form
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

				new signIn().setVisible(true);

			}

		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ErrorCheck;
    private javax.swing.JLabel PasswordLabel;
    private javax.swing.JLabel SignUpInstructionsLabel;
    private javax.swing.JLabel UsernameLabel;
    private javax.swing.JButton backButton;
    private javax.swing.JToggleButton btnSignIn;
    private javax.swing.JToggleButton btnSignUp;
    private javax.swing.JPasswordField passwordEnter;
    private javax.swing.JLabel signinLabel;
    private javax.swing.JTextField usernameEnter;
    // End of variables declaration//GEN-END:variables

}
//Coded by Brent Butkow
