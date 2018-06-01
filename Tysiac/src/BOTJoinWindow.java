import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class BOTJoinWindow extends JFrame implements ActionListener
{
	private JButton bPlay;
	private JTextField TJoin;
	private JLabel text;
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

		add(bPlay);
		add(text);
	
		
		bPlay.setBounds(240,240,70,30);
		text.setBounds(30,50, 140,30);
		text.setForeground(Color.white);
		
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