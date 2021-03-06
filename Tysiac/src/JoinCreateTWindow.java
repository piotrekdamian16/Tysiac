import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class JoinCreateTWindow extends JFrame implements ActionListener
{
	private JButton bJoin, bCreate, bPlay;
	private JTextField TJoin;
	private ButtonGroup bgAmountPlayers;
	private JRadioButton rbPlayer3, rbPlayer4;
	private GameData GaD;

	
	public JoinCreateTWindow(GameData a)
	{	
		GaD = a;
		
		Toolkit kit = Toolkit.getDefaultToolkit(); //zbadanie wymiarow ekranu do ustawienia okna
		Dimension screenSize = kit.getScreenSize();
		int sHeight = screenSize.height;
		int sWidth = screenSize.width;
		
		setBounds((sWidth/2)-400/2,(sHeight/2)-350/2,400,350);
		setLayout(null);
		
		bCreate = new JButton("Stwórz");
		bJoin = new JButton("Dołącz");
		bPlay= new JButton("Graj");
		
		add(bCreate);
		add(bJoin);
		add(bPlay);
	
		bJoin.setBounds(30,50, 120,30);
		bCreate.setBounds(30,120, 120,30);
		bPlay.setBounds(240,240,70,30);
		
		
		bJoin.addActionListener(this);
		bCreate.addActionListener(this);	
		bPlay.addActionListener(this);		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if(source==bCreate)
		{		
			GaD.setPlayClick2(1);
			bJoin.setEnabled(false);
			
			bgAmountPlayers = new ButtonGroup();

			rbPlayer3 = new JRadioButton("3",true);
			rbPlayer4 = new JRadioButton("4 Ilość Graczy",true);
			
			rbPlayer3.setBounds(170,170,40,20);
			rbPlayer4.setBounds(210,170,150,20);
			
			bgAmountPlayers.add(rbPlayer3);
			bgAmountPlayers.add(rbPlayer4);
			
			add(rbPlayer3);
			add(rbPlayer4);
			
			rbPlayer3.setBackground(new Color(0,102,0));
			rbPlayer4.setBackground(new Color(0,102,0));
			
			rbPlayer3.setForeground(new Color(255,255,255));
			rbPlayer4.setForeground(new Color(255,255,255));
		}
		else if(source==bJoin)
		{
			GaD.setPlayClick2(2);
			bCreate.setEnabled(false);
			
			TJoin = new JTextField("");
			add(TJoin);
			TJoin.setBounds(170,50,150,30);
		}
		else if(source==bPlay)
		{
			if(GaD.getPlayClick2() == 0)
			{
				JOptionPane.showMessageDialog(null,"Nie wybrano `Stwórz` lub `Dołącz`");
			}
			else if(GaD.getPlayClick2() == 1)
			{
				if(rbPlayer3.isSelected()) GaD.setTypeTable(3);
				if(rbPlayer4.isSelected()) GaD.setTypeTable(4);

				GaD.setPlayClick(1);
			}
			else if(GaD.getPlayClick2() == 2)
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