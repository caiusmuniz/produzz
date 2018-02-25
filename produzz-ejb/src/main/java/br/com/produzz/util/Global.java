package br.com.produzz.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class Global {
	private static final Logger LOGGER = Logger.getLogger(Global.class);

	public static String LOGPROPERTIES = "log.properties";

	public static void WriteFile(String filename, String content) {
		FileOutputStream fop = null;
		File file;
		Writer out = null;

		try {
			file = new File(filename);
			fop = new FileOutputStream(file);
			out = new BufferedWriter(new OutputStreamWriter(fop, "UTF-8"));

			if (!file.exists()) {
				file.createNewFile();
			}

			out.write(content);
			out.flush();
			out.close();
			LOGGER.info("Persistence data is done");

		} catch (final IOException e) {
			LOGGER.error(e);

		} finally {
			try {
				if (out != null) {
					out.close();
				}

			} catch (final IOException e) {
				LOGGER.error(e);
			}
		}
	}

	public static void WriteFile(File file, byte[] content) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content);
			fos.close();

		} catch (final Exception e) {
			LOGGER.error(e);
		}
	}	
	
	public String loadPersistence(String filename) {
		String result = null;

		try {
			InputStream input = null;

			File file = new File(filename);

			if (file.exists()) {
				input = new FileInputStream(file);
			}

			if (input == null) {
				input = this.getClass().getClassLoader().getResourceAsStream(filename);
			}

			result = Global.getStringFromInputStream(input);

			input.close();

			if (result == null) throw new Exception("O template " + filename + " não foi localizado");

		} catch (final Exception e) {
			LOGGER.error("Arquivo de properties de configuracao nao encontrado.", e);
		}
		return result;
	}

	public static String processVariables(String text, HashMap<String,String> variables) {
		String result = text;

		for (Map.Entry<String, String> entry : variables.entrySet()) {
			result = result.replace(entry.getKey(), entry.getValue());
		}

		return result;
	}

	public static String readFile(String filename) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;

		try {
			File file = new File(filename);
			FileInputStream is = new FileInputStream(file);		

			br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (final IOException e) {
			LOGGER.error(e);

		} finally {
			if (br != null) {
				try {
					br.close();

				} catch (final IOException e) {
					LOGGER.error(e);
				}
			}
		}

		return sb.toString();
	}	

	public static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;

		try {
			br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (final IOException e) {
			LOGGER.error(e);

		} finally {
			if (br != null) {
				try {
					br.close();

				} catch (final IOException e) {
					LOGGER.error(e);
				}
			}
		}

		return sb.toString();
	}

	public static int randInt(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	public static String sha1(String value) {
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("SHA-1");

		} catch (final NoSuchAlgorithmException e) {
			LOGGER.error(e);
		}

		return stringHexa(md.digest(value.getBytes()));
	}

	private static String stringHexa(byte[] bytes) {
		StringBuilder s = new StringBuilder();

		for (int i = 0; i < bytes.length; i++) {
			int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
			int parteBaixa = bytes[i] & 0xf;

			if (parteAlta == 0) s.append('0');

			s.append(Integer.toHexString(parteAlta | parteBaixa));
		}

		return s.toString();
	}

	public static String GenerateRandomString(int minLength, int maxLength, int minLCaseCount, int minUCaseCount, int minNumCount, int minSpecialCount) {
	    char[] randomString;

	    String LCaseChars = "abcdefgijkmnopqrstwxyz";
	    String UCaseChars = "ABCDEFGHJKLMNPQRSTWXYZ";
	    String NumericChars = "23456789";
	    String SpecialChars = "*$-+_&=!{}/";

	    Map<String,Integer> charGroupsUsed = new HashMap<String,Integer>();
	    charGroupsUsed.put("lcase", minLCaseCount);
	    charGroupsUsed.put("ucase", minUCaseCount);
	    charGroupsUsed.put("num", minNumCount);
	    charGroupsUsed.put("special", minSpecialCount);

	    byte[] randomBytes = new byte[4];

	    new Random().nextBytes(randomBytes);

	    int seed = (randomBytes[0] & 0x7f) << 24 |
	                randomBytes[1] << 16 |
	                randomBytes[2] << 8 |
	                randomBytes[3];

	    Random random = new Random(seed);

	    int randomIndex = -1;

	    if (minLength < maxLength) {
	        randomIndex = random.nextInt((maxLength-minLength)+1)+minLength;
	        randomString = new char[randomIndex];

	    } else {
	        randomString = new char[minLength];
	    }

	    int requiredCharactersLeft = minLCaseCount + minUCaseCount + minNumCount + minSpecialCount;

	    for (int i = 0; i < randomString.length; i++) {
	        StringBuilder selectableChars = new StringBuilder();

	        if (requiredCharactersLeft < randomString.length - i){
	        	selectableChars.append(LCaseChars).append(UCaseChars).append(NumericChars).append(SpecialChars);	            
	        } else {
	            for (Map.Entry<String, Integer> charGroup : charGroupsUsed.entrySet()) {
	                if ((int)charGroup.getValue() > 0) {
	                    if ("lcase".equals(charGroup.getKey()) ) {
	                    	selectableChars.append(LCaseChars);	                        

	                    } else if ("ucase".equals(charGroup.getKey())) {
	                    	selectableChars.append(UCaseChars);	                        

	                    } else if ("num".equals(charGroup.getKey())) {
	                    	selectableChars.append(NumericChars);	                        

	                    } else if ("special".equals(charGroup.getKey())) {
	                    	selectableChars.append(SpecialChars);	                        
	                    }
	                }
	            }
	        }

	        randomIndex = random.nextInt((selectableChars.length())-1);
	        char nextChar = selectableChars.charAt(randomIndex);

	        randomString[i] = nextChar;

	        if (LCaseChars.indexOf(nextChar) > -1) {
	            charGroupsUsed.put("lcase",charGroupsUsed.get("lcase") - 1);

	            if (charGroupsUsed.get("lcase") >= 0) {
	                requiredCharactersLeft--;
	            }

	        } else if (UCaseChars.indexOf(nextChar) > -1) {
	            charGroupsUsed.put("ucase",charGroupsUsed.get("ucase") - 1);

	            if (charGroupsUsed.get("ucase") >= 0) {
	                requiredCharactersLeft--;
	            }

	        } else if (NumericChars.indexOf(nextChar) > -1) {
	            charGroupsUsed.put("num", charGroupsUsed.get("num") - 1);

	            if (charGroupsUsed.get("num") >= 0) {
	                requiredCharactersLeft--;
	            }

	        } else if (SpecialChars.indexOf(nextChar) > -1) {
	            charGroupsUsed.put("special",charGroupsUsed.get("special") - 1);

	            if (charGroupsUsed.get("special") >= 0) {
	                requiredCharactersLeft--;
	            }
	        }
	    }

	    return new String(randomString);
	}

	public static boolean isNumeric(String value) {
		boolean result = false;

		try {
			Integer.parseInt(value);
			result = true;
		} catch (final NumberFormatException ignore) { }

		return result;
	}

	public static void sendEmail(String toAddress, String title, String content) throws Exception {
		LOGGER.info("sendEmail(" + toAddress  + "," + title + ",[content])");

	    Context initCtx = new InitialContext();  
	    Session session = (Session) initCtx.lookup("java:jboss/mail/gmail");

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("servidor.produzz@gmail.com"));

		Address[] copyTo = InternetAddress.parse(toAddress);
		message.setRecipients(Message.RecipientType.TO, copyTo);

		message.setSubject(title);
		message.setContent(content, "text/html; charset=utf-8");			

		Transport.send(message);
		LOGGER.info("Email enviado");
	}
}
