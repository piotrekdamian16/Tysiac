import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class BOTJoinWindow extends JFrame implements ActionListener
{
	private JButton bPlay;
	private JCheckBox showGameWindow;
	private JTextField TJoin;
	private JLabel text, text2;
	private GameData GaD;

	
	public BOTJoinWindow(GameData a)
	{	
		GaD = a;
		
		Toolkit kit = Toolkit.getDefaultToolkit(); //zbadanie wymiarow ekranu do ustawienia okna
		Dimension screenSize = kit.getScreenSize();
		int sHeight = screenSize.height;
		int sWidth = screenSize.width;
		
		setBounds((sWidth/2)-400/2,(sHeight/2)-350/2,400,350);
		setLayout(null);
		
		bPlay = new JButton("Graj");
		text = new JLabel("Kod dostępu: ");
		text2 = new JLabel("Pokaż okno gry: ");
		showGameWindow = new JCheckBox();

		add(bPlay);
		add(text);
		add(text2);
		add(showGameWindow);
	
		
		bPlay.setBounds(240,240,70,30);
		text.setBounds(30,50, 140,30);
		text.setForeground(Color.white);
		
		text2.setBounds(30,90, 140,30);
		text2.setForeground(Color.white);
		
		showGameWindow.setBounds(170,95, 20,20);
		showGameWindow.setBackground(new Color(0,102,0));
		
		
		bPlay.addActionListener(this);	
		

		GaD.setPlayClick2(1);
		
		TJoin = new JTextField("");
		add(TJoin);
		TJoin.setBounds(170,50,150,30);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if(source==bPlay)
		{
			if(TJoin.getText() != "")
			{
				if(showGameWindow.isSelected()) GaD.setBOTShowGameWindow(true);
				else GaD.setBOTShowGameWindow(false);
				
				GaD.setAccessCode(TJoin.getText());
				GaD.setPlayClick(1);
			}
			else 
			{
				JOptionPane.showMessageDialog(null,"Kod dostępu jest pusty");
			}
		}
	}


	public GameData getGameData() 
	{
		return GaD;
	}
	public void closeWindow()
	{
		dispose();
	}
}