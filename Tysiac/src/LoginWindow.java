import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class LoginWindow extends JFrame implements ActionListener
{
	private JButton bLogin, bRegistry;
	private JLabel lLogin, lPass;
	private JTextField text1; 
	private JPasswordField text2; 
	private String login;
	private String password;
	
	public LoginWindow()
	{	
		Toolkit kit = Toolkit.getDefaultToolkit(); //zbadanie wymiarow ekranu do ustawienia okna
		Dimension screenSize = kit.getScreenSize();
		int sHeight = screenSize.height;
		int sWidth = screenSize.width;
		
		setBounds((sWidth/2)-400/2,(sHeight/2)-350/2,400,350);
		setLayout(null);
		
		lLogin = new JLabel("Login:");
		lPass = new JLabel("Password:");
		
		lLogin.setForeground(Color.white);
		lPass.setForeground(Color.white);
		
		add(lLogin);
		add(lPass);
		
		lLogin.setBounds(40,50,100,40);
		lPass.setBounds(40,100,100,40);
		
		
		bLogin = new JButton("Zaloguj");
		bRegistry = new JButton("Rejestracja");
		
		add(bLogin);
		add(bRegistry);
		
		bLogin.setBounds(140,160, 120,30);
		bRegistry.setBounds(140,220, 120,30);
		
		
		text1 = new JTextField("");
		text2 = new JPasswordField("");
		
		text1.setBounds(140,50,150,30);
		text2.setBounds(140,100,150,30);
		
		add(text1);
		add(text2);
		
		bRegistry.addActionListener(this);
		bLogin.addActionListener(this);
	}

	public static void main(String[] args)
	{
		LoginWindow LogW = new LoginWindow();
		Color stol = new Color(0,102,0);
		
		LogW.setVisible(true);
		//LogW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LogW.getContentPane().setBackground(stol);
		LogW.setTitle("Tysiac");
		LogW.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if(source==bLogin)
		{
			
			int checkL = -10;
			//int checkLP = -10;
			
			this.setLogin(text1.getText());

			if(checkL > 0)
			{
				JOptionPane.showMessageDialog(null,"Zalogowano poprawnie." );
				
				JoinCreateTWindow JCWin = new JoinCreateTWindow();
				Color stol = new Color(0,102,0);
				
				JCWin.setVisible(true);
				JCWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JCWin.getContentPane().setBackground(stol);
				JCWin.setTitle("Tysiąc");
				JCWin.setResizable(false);
				
				setVisible(false);   //niewidoczne okno login window
				dispose();  //usuwa obiekt login window
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Zły login lub hasło." );
			}

		}
		else if(source==bRegistry)
		{
			Desktop browser = Desktop.getDesktop();
			
			try 
			{
				browser.browse(new URI("http://tysiac.5v.pl/index.php?display=login"));
			}
			catch(IOException err)
			{
				
			}
			catch(URISyntaxException err)
			{
				
			}
			
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) 
	{
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}
}
