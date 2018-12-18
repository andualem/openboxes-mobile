package org.health.supplychain.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encryption {

	
//	public static String MD5(String text)
//            throws NoSuchAlgorithmException, UnsupportedEncodingException {
//        MessageDigest md;
//        md = MessageDigest.getInstance("MD5");
//        byte[] key = md.digest(text.getBytes());
//
//        StringBuffer hash = new StringBuffer("");
//        for (byte b : key) {
//            hash.append(b);
//        }
//        return hash.toString();
//    }


    //Amare's Implementation
    public static final String MD5(final String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(toEncrypt.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return ""; // Impossibru!
        }
    }

	
//	String encryPassword = MD5Encryption.MD5(password);//encrypting password
	
	
	/*public boolean checkLoggedInUser( String connURL) throws ClassNotFoundException {

        ResultSet rs = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@" + connURL, "ubscc", "ubscc");
            String sql = " select * from MM_GEN_USER where upper(LOGIN_ID)=upper('" + this.getUserName() + "') AND upper(PASSWD) = upper('" + this.getPassword() + "')";
            PreparedStatement cs = con.prepareStatement(sql);//U_TD_CUTTOFF_EEPCO_USER
            rs = cs.executeQuery();

            while (rs.next()) {
                this.setPassword(rs.getString("PASSWD"));
                this.setUserName(rs.getString("LOGIN_ID"));
                this.setFlag(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManagerCuttOff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.getFlag();
    }*/

	
	
}
