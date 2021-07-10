//Coded by Brent Butkow

package ChessGame;

/**
 *
 * @author Brent
 */
public class encrypt {

	/**
	 * encrypt a string using two randomly generated keys
	 *
	 * @param plainText the decrypted string
	 * @return the now encrypted string
	 */
	public static String encrypString(String plainText) {
		char codeA, codeB, codeC;

		//generating 2 random keys as chars
		codeA = (char) Math.round(Math.random() * 600 + 161);
		codeB = (char) Math.round(Math.random() * 600 + 161);

		//a char to represent the length of the string + 161 (to ensure all chars
		//are out of the normal letters range, for more security)
		codeC = (char) (plainText.length() + 161);

		//make sure the length char is in the desired char range
		while (codeC > 761) {
			codeC -= 600;
		}

		//the desired char range is chars with Askii values from 161 to 761 inclusive
		//encrypt the string with the two random keys and the length
		String temp = codeA + "" + codeB + "" + codeC + help(plainText, codeA, codeB);

		//to add security, a random amount of random chars are added to the end (these
		//are not decrypted, and are just here to make the encryption harder to break)
		int end = (int) Math.ceil(Math.random() * (plainText.length() / 10) + 2);
		for (int i = 0; i < end; i++) {
			temp += (char) (Math.round(Math.random() * 600 + 160));
		}

		return temp;
	}

	/**
	 * decrypt a string
	 *
	 * @param encrypted the encrypted string
	 * @return the now decrypted string
	 */
	public static String decryptString(String encrypted) {

		//getting rid of the random chars at the end of the String
		int end = encrypted.charAt(2) - 158;
		while (encrypted.length() - end > 600) {
			end += 600;
		}

		//decrypting the string
		return help(encrypted.substring(3, end), encrypted.charAt(0), encrypted.charAt(1));
	}

	/**
	 * decrypt and encrypt using my own unique symmetrical encryption algorithm
	 * that uses Xor-ing
	 *
	 * @param sentence the decrypted or encrypted string
	 * @param codeA the first key
	 * @param codeB the second key
	 * @return either the now encrypted string or the now decrypted string
	 */
	private static String help(String sentence, char codeA, char codeB) {

		String temp = "";	//the changed string
		int change = 0;		//used for if the chars go over the desired char range

		//iterate for every char in the string
		for (int i = 0; i < sentence.length(); i++) {

			//the key is made using the two random keys generated in the encryption step
			int key = (codeA + codeB * i + 160) - 400 * change;
			while (key > 670) {//keeping the key in the desired range
				change++;
				key = (codeA + codeB * i) - 400 * change;
			}

			//encrypting using the Xor operation ^ of every char with a key
			temp += (char) (sentence.charAt(i) ^ key);
		}

		return temp;
	}

}
//Coded by Brent Butkow
