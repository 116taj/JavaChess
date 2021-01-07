//Name: Taj Randhawa
//Date: January 17th, 2020
//Game: Chess WWIII Edition
//Purpose: To create a grid game using 2D Arrays and correlating functions.

//importing libraries necessary for game
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class chess extends Applet implements ActionListener, KeyListener
{
	//random chess,check, captured at top/bottom
	Panel p_card;  //to hold all of the screens
	Panel card1, card2, card3, card4, card5, card6; //the six screens
	CardLayout cdLayout = new CardLayout ();
	AudioClip soundFile;
	//grid
	int row = 8;
	int col = 8;
	// p is previous n value; kx and ky are the king coordinates that are used for the check() method, more specifically checkking() method.
	int p,kx,ky  = -1;
	// current turn and replay turn to display
	char t = 'w';
	char rt = 'w';
	/* replay is used in the 3D array where it tracks the move number of the game
	 * replayturn tracks the move being viewed in the replay screen
	 * undo count prevents undos from changing teams
	 * ignorer ignores updating the replay array, when user undos an action
	 * ignorereset ignores resetting board, for when board is randomized
	 */ 
	int replay,replayturn,undocount,ignorer, ignorereset = 0;
	// JFrames for instructions, multiple as research showed I needed to use the type Graphics
	JFrame instruct = new JFrame("Instructions");
	JFrame spawn= new JFrame("Instructions");
	JFrame srook= new JFrame("Instructions");
	JFrame sknight= new JFrame("Instructions");
	JFrame sbishop= new JFrame("Instructions");
	JFrame squeen= new JFrame("Instructions");
	JFrame sking= new JFrame("Instructions");
	JFrame sadd= new JFrame("Instructions");
	JFrame schess= new JFrame("Instructions");
	JFrame scontrol = new JFrame("Instructions");
	// declaring widgets globally so they adapt as non pre determined things change
	JButton undo = new JButton ("Undo");
	JButton random = new JButton ("Randomize");
	JLabel winner = new JLabel ("e");
	JButton disableundo = new JButton ("Disable Undo");
	//declaring main JButton array, and replay JButton array
	JButton a[] = new JButton [row * col];
	JButton b[] = new JButton [row * col];
	//declaring arrays that will be combined to make board
	char bg[] [] = {{'y','g','y','g', 'y', 'g', 'y', 'g'}, 
			{'g','y','g', 'y', 'g', 'y', 'g', 'y'}, 
			{'y','g','y','g', 'y', 'g', 'y', 'g'}, 
			{'g','y','g', 'y', 'g', 'y', 'g', 'y'}, 
			{'y','g','y','g', 'y', 'g', 'y', 'g'}, 
			{'g','y','g', 'y', 'g', 'y', 'g', 'y'}, 
			{'y','g','y','g', 'y', 'g', 'y', 'g'}, 
			{'g','y','g', 'y', 'g', 'y', 'g', 'y'}};
	char piece[] [] = {{'r','n','b','q', 'k', 'b', 'n', 'r'}, 
			{'p','p','p','p', 'p', 'p', 'p', 'p'}, 
			{'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a'}, 
			{'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a'}, 
			{'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a'}, 
			{'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a'}, 
			{'p','p','p','p', 'p', 'p', 'p', 'p'}, 
			{'r','n','b','q', 'k', 'b', 'n', 'r'}};
	char team[] [] = {{'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b'}, 
			{'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b'}, 
			{'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a'}, 
			{'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a'}, 
			{'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a'}, 
			{'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a'}, 
			{'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'}, 
			{'w', 'w', 'w', 'w', 'w', 'w', 'w', 'w'}};
	char selected[] [] = {{'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u'}, 
			{'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u'}, 
			{'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u'}, 
			{'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u'}, 
			{'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u'}, 
			{'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u'}, 
			{'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u'}, 
			{'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u'}};
	//declaring replay and undo variants
	char rpiece[][] = new char [8][8];
	char rteam[][] = new char [8][8];
	char upiece[][] = new char [8][8];
	char  uteam[][] = new char [8][8];
	char replayteam[][][] = new char[1000][8][8];
	char replaypiece[][][] = new char[1000][8][8];
	//creating images that will show on the other panels
	JLabel turn = new JLabel (createImageIcon("white.png"));
	JLabel rturn = new JLabel (createImageIcon("white.png"));
	JLabel ipawn = new JLabel (createImageIcon("ipawn.png"));
	JLabel iknight = new JLabel (createImageIcon("iknight.png"));
	JLabel irook = new JLabel (createImageIcon("irook.png"));
	JLabel ibishop = new JLabel (createImageIcon("ibishop.png"));
	JLabel iqueen = new JLabel (createImageIcon("iqueen.png"));
	JLabel iking = new JLabel (createImageIcon("iking.png"));
	JLabel iadd = new JLabel (createImageIcon("iadd.png"));
	JLabel idef = new JLabel (createImageIcon("idef.png"));
	JLabel ichess = new JLabel (createImageIcon("ichess.png"));
	JLabel icontrol = new JLabel (createImageIcon("icontrol.png"));
	//init method
	public void init ()
	{
		// initializing the panels
		instruct.getContentPane().add(idef, BorderLayout.CENTER);
		instruct.pack();
		instruct.setVisible(false);
		schess.getContentPane().add(ichess, BorderLayout.CENTER);
		schess.pack();
		schess.setVisible(false);
		spawn.getContentPane().add(ipawn, BorderLayout.CENTER);
		spawn.pack();
		spawn.setVisible(false);
		srook.getContentPane().add(irook, BorderLayout.CENTER);
		srook.pack();
		srook.setVisible(false);
		sknight.getContentPane().add(iknight, BorderLayout.CENTER);
		sknight.pack();
		sknight.setVisible(false);
		sbishop.getContentPane().add(ibishop, BorderLayout.CENTER);
		sbishop.pack();
		sbishop.setVisible(false);
		squeen.getContentPane().add(iqueen, BorderLayout.CENTER);
		squeen.pack();
		squeen.setVisible(false);
		sking.getContentPane().add(iking, BorderLayout.CENTER);
		sking.pack();
		sking.setVisible(false);
		sadd.getContentPane().add(iadd, BorderLayout.CENTER);
		sadd.pack();
		sadd.setVisible(false);
		scontrol.getContentPane().add(icontrol, BorderLayout.CENTER);
		scontrol.pack();
		scontrol.setVisible(false);
		copy();
		// copying regular arays into the 1st move of replay
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				replayteam[0][i][j] = team[i][j];
				replaypiece[0][i][j] = piece[i][j];
			}
		}
		//setting up all screens
		p_card = new Panel ();
		p_card.setLayout (cdLayout);
		title ();
		instructions ();
		settings();
		game ();
		replayscreen ();
		gameover ();
		resize (450, 570);
		setLayout (new BorderLayout ());
		add ("Center", p_card);
		//initializing sound effect
		soundFile = getAudioClip (getDocumentBase (), "move.snd");
	}

	public void title ()
	{ //title is set up.
		card1 = new Panel ();
		card1.setBackground (new Color(126, 179, 216));
		JLabel menu = new JLabel (createImageIcon("chess.png"));
		JTextField key = new JTextField("Type here to select an option!");
		key.addKeyListener(this);
		card1.add(key);
		card1.add (menu);
		p_card.add ("1", card1);
	}


	public void instructions ()
	{ //instructions screen/hub is set up.
		card2 = new Panel ();
		card2.setBackground (new Color(126, 179, 216));
		JLabel title = new JLabel ("  Instructions  ");
		title.setFont (new Font ("Bree Serif", Font.BOLD, 80));
		title.setForeground(Color.BLACK);
		JButton back = new JButton ("Back");
		back.setPreferredSize(new Dimension(400, 40));
		back.setFont (new Font ("Bree Serif", Font.BOLD, 30));
		back.setBackground(Color.BLACK);
		back.setForeground(Color.WHITE);
		back.setActionCommand ("title");
		back.addActionListener (this);
		JButton pawn = new JButton ("Pawn");
		pawn.setPreferredSize(new Dimension(400, 40));
		pawn.setFont (new Font ("Bree Serif", Font.BOLD, 30));
		pawn.setBackground(Color.BLACK);
		pawn.setForeground(Color.WHITE);
		pawn.setActionCommand ("pawn");
		pawn.addActionListener (this);
		JButton rook = new JButton ("Rook");
		rook.setPreferredSize(new Dimension(400, 40));
		rook.setFont (new Font ("Bree Serif", Font.BOLD, 30));
		rook.setBackground(Color.BLACK);
		rook.setForeground(Color.WHITE);
		rook.setActionCommand ("rook");
		rook.addActionListener (this);
		JButton knight = new JButton ("Knight");
		knight.setPreferredSize(new Dimension(400, 40));
		knight.setFont (new Font ("Bree Serif", Font.BOLD, 30));
		knight.setBackground(Color.BLACK);
		knight.setForeground(Color.WHITE);
		knight.setActionCommand ("knight");
		knight.addActionListener (this);
		JButton bishop = new JButton ("Bishop");
		bishop.setPreferredSize(new Dimension(400, 40));
		bishop.setFont (new Font ("Bree Serif", Font.BOLD, 30));
		bishop.setBackground(Color.BLACK);
		bishop.setForeground(Color.WHITE);
		bishop.setActionCommand ("bishop");
		bishop.addActionListener (this);
		JButton queen = new JButton ("Queen");
		queen.setPreferredSize(new Dimension(400, 40));
		queen.setFont (new Font ("Bree Serif", Font.BOLD, 30));
		queen.setBackground(Color.BLACK);
		queen.setForeground(Color.WHITE);
		queen.setActionCommand ("queen");
		queen.addActionListener (this);
		JButton king = new JButton ("King");
		king.setPreferredSize(new Dimension(400, 40));
		king.setFont (new Font ("Bree Serif", Font.BOLD, 30));
		king.setBackground(Color.BLACK);
		king.setForeground(Color.WHITE);
		king.setActionCommand ("king");
		king.addActionListener (this);
		JButton add = new JButton ("Additionnal");
		add.setPreferredSize(new Dimension(400, 40));
		add.setFont (new Font ("Bree Serif", Font.BOLD, 30));
		add.setBackground(Color.BLACK);
		add.setForeground(Color.WHITE);
		add.setActionCommand ("add");
		add.addActionListener (this);
		JButton chess = new JButton ("General");
		chess.setPreferredSize(new Dimension(400, 40));
		chess.setFont (new Font ("Bree Serif", Font.BOLD, 30));
		chess.setBackground(Color.BLACK);
		chess.setForeground(Color.WHITE);
		chess.setActionCommand ("chess");
		chess.addActionListener (this);
		JButton control = new JButton ("Controls");
		control.setPreferredSize(new Dimension(400, 40));
		control.setFont (new Font ("Bree Serif", Font.BOLD, 30));
		control.setBackground(Color.BLACK);
		control.setForeground(Color.WHITE);
		control.setActionCommand ("control");
		control.addActionListener (this);
		card2.add (title);
		card2.add (chess);
		card2.add(control);
		card2.add (pawn);
		card2.add (rook);
		card2.add (knight);
		card2.add (bishop);
		card2.add (queen);
		card2.add (king);
		card2.add (add);
		card2.add (back);
		p_card.add ("2", card2);
	}
	public void settings ()
	{ //settings screen is set up.
		card6 = new Panel ();
		card6.setBackground (new Color(126, 179, 216));
		JLabel title = new JLabel ("  Settings  ");
		title.setFont (new Font ("Bree Serif", Font.BOLD, 80));
		title.setForeground(Color.BLACK);
		//random is global to allow randomizing and resetting at the settings screen.
		random.setPreferredSize(new Dimension(400, 100));
		random.setFont (new Font ("Bree Serif", Font.BOLD, 60));
		random.setBackground(Color.BLACK);
		random.setForeground(Color.WHITE);
		random.setActionCommand ("random");
		random.addActionListener (this);
		//disableundo is global to allow enabling and disabling within the same button.
		disableundo.setPreferredSize(new Dimension(400, 100));
		disableundo.setFont (new Font ("Bree Serif", Font.BOLD, 50));
		disableundo.setBackground(Color.BLACK);
		disableundo.setForeground(Color.WHITE);
		disableundo.setActionCommand ("disundo");
		disableundo.addActionListener (this);
		JButton back = new JButton ("Back");
		back.setPreferredSize(new Dimension(400, 100));
		back.setFont (new Font ("Bree Serif", Font.BOLD, 70));
		back.setBackground(Color.BLACK);
		back.setForeground(Color.WHITE);
		back.setActionCommand ("title");
		back.addActionListener (this);
		card6.add (title);
		card6.add (random);
		card6.add(disableundo);
		card6.add(back);
		p_card.add ("6", card6);
	}

	public void game ()
	{ //game screen is set up.
		card3 = new Panel ();
		card3.setBackground (new Color(126, 179, 216));
		JLabel title = new JLabel (" Current Turn:  ");
		title.setFont (new Font ("Bree Serif", Font.BOLD, 30));
		title.setForeground(Color.BLACK);
		JButton reset = new JButton ("Reset");
		reset.setActionCommand ("reset");
		reset.addActionListener (this);
		reset.setBackground(Color.BLACK);
		reset.setForeground(Color.WHITE);
		reset.setFont (new Font ("Bree Serif", Font.BOLD, 14));
		//undo is global to allow it to be disabled.
		undo.setActionCommand ("undo");
		undo.addActionListener (this);
		undo.setBackground(Color.BLACK);
		undo.setForeground(Color.WHITE);
		undo.setFont (new Font ("Bree Serif", Font.BOLD, 14));
		JButton back = new JButton ("Back");
		back.setFont (new Font ("Bree Serif", Font.BOLD, 14));
		back.setBackground(Color.BLACK);
		back.setForeground(Color.WHITE);
		back.setActionCommand ("title");
		back.addActionListener (this);
		//Setting up grid
		Panel p = new Panel (new GridLayout (row, col));
		int move = 0;
		for (int i = 0 ; i < row ; i++)
		{
			for (int j = 0 ; j < col ; j++)
			{ 
				a [move] = new JButton (createImageIcon (String.valueOf(bg [i] [j]) + String.valueOf(piece [i] [j]) + String.valueOf(team [i] [j])+ String.valueOf(selected [i] [j]) + ".png"));
				a [move].setPreferredSize (new Dimension (54, 54));
				a [move].addActionListener (this);
				a [move].setActionCommand ("" + move);
				p.add (a [move]);
				move++;
			}
		}
		card3.add (title);
		card3.add(turn);
		card3.add (p);
		card3.add(undo);
		card3.add(reset);
		card3.add(back);
		p_card.add ("3", card3);
	}


	public void replayscreen ()
	{ //replay screen is set up.
		card4 = new Panel ();
		card4.setBackground (new Color(126, 179, 216));
		JLabel title = new JLabel ("Replay -");
		title.setFont (new Font ("Bree Serif", Font.BOLD, 30));
		title.setForeground(Color.BLACK);
		JLabel currentturn = new JLabel (" Current Turn:  ");
		currentturn.setFont (new Font ("Bree Serif", Font.BOLD, 30));
		currentturn.setForeground(Color.BLACK);
		JButton next = new JButton ("Done");
		next.setActionCommand ("title");
		next.setBackground(Color.BLACK);
		next.setForeground(Color.WHITE);
		next.addActionListener (this);
		JButton replaynext = new JButton ("Next Turn");
		replaynext.setActionCommand ("replaynext");
		replaynext.addActionListener (this);
		replaynext.setBackground(Color.BLACK);
		replaynext.setForeground(Color.WHITE);
		JButton replayback = new JButton ("Previous Turn");
		replayback.setActionCommand ("replayback");
		replayback.addActionListener (this);
		replayback.setBackground(Color.BLACK);
		replayback.setForeground(Color.WHITE);
		//setting up the replay grid
		Panel p = new Panel (new GridLayout (row, col));
		int move = 0;
		for (int i = 0 ; i < row ; i++)
		{
			for (int j = 0 ; j < col ; j++)
			{ 
				b [move] = new JButton (createImageIcon (replaybg(i,j) + String.valueOf(replaypiece [replayturn][i] [j]) + String.valueOf(replayteam [replayturn] [i] [j])+ 'u' + ".png"));
				b [move].setPreferredSize (new Dimension (54, 54));
				p.add (b [move]);
				move++;
			}
		}
		card4.add (title);
		card4.add(currentturn);
		card4.add(rturn);
		card4.add(p);
		card4.add (next);
		card4.add(replaynext);
		card4.add(replayback);
		p_card.add ("4", card4);
	}


	public void gameover ()
	{ //game over screen is set up.
		card5 = new Panel ();
		card5.setBackground ((new Color(126, 179, 216)));
		//winner is global as the winner is unknown.
		winner.setFont (new Font ("Bree Serif", Font.BOLD, 70));
		winner.setForeground(Color.BLACK);
		winner.setPreferredSize(new Dimension(400, 100));
		JButton back = new JButton ("Back to Title");
		back.setPreferredSize(new Dimension(400, 100));
		back.setFont (new Font ("Bree Serif", Font.BOLD, 50));
		back.setBackground(Color.BLACK);
		back.setForeground(Color.WHITE);
		back.setActionCommand ("title");
		back.addActionListener (this);
		JButton replay = new JButton ("View Replay");
		replay.setPreferredSize(new Dimension(400, 100));
		replay.setFont (new Font ("Bree Serif", Font.BOLD, 60));
		replay.setBackground(Color.BLACK);
		replay.setForeground(Color.WHITE);
		replay.setActionCommand ("replay");
		replay.addActionListener (this);
		JButton end = new JButton ("Quit");
		end.setPreferredSize(new Dimension(400, 100));
		end.setFont (new Font ("Bree Serif", Font.BOLD, 50));
		end.setBackground(Color.BLACK);
		end.setForeground(Color.WHITE);
		end.setActionCommand ("s6");
		end.addActionListener (this);
		card5.add (winner);
		card5.add (back);
		card5.add(replay);
		card5.add (end);
		p_card.add ("5", card5);

	}


	protected static ImageIcon createImageIcon (String path)
	{ //creates images, not own code
		java.net.URL imgURL = chess.class.getResource (path);
		if (imgURL != null)
		{
			return new ImageIcon (imgURL);
		}
		else
		{
			System.err.println ("Couldn't find file: " + path);
			return null;
		}
	}


	public void redraw ()
	{
		//redraws the game grid
		int move = 0;
		for (int i = 0 ; i < row ; i++)
		{
			for (int j = 0 ; j < col ; j++)
			{
				a [move].setIcon (createImageIcon (String.valueOf(bg [i] [j]) + String.valueOf(piece [i] [j]) + String.valueOf(team [i] [j])+ String.valueOf(selected [i] [j]) + ".png"));
				move++;
			}
		}
	}


	public void actionPerformed (ActionEvent e)

	{ //ignoret ignores team changing
		int ignoret = 0;
		//changes the screens
		//goes to title
		if (e.getActionCommand ().equals ("title")) {
			cdLayout.show (p_card, "1");
			disableall();
			if (ignorereset == 0)
				reset();
			ignorereset = 0;
		}
		//goes to replay
		else if (e.getActionCommand ().equals ("replay"))
			cdLayout.show (p_card, "4");
		//quits the game
		else if (e.getActionCommand ().equals ("s6"))
			System.exit (0);
		//resets board
		else if (e.getActionCommand ().equals ("reset"))
			reset();
		// changes global variables when buttons are clicked, so that the button effect works
		//randomizes board
		else if (e.getActionCommand ().equals ("random")) {
			randomize();
			ignorereset = 1;
			random.setFont (new Font ("Bree Serif", Font.BOLD, 50));
			random.setText("Unrandomize");
			random.setActionCommand("unrandom");
		}
		//unrandomizes board
		else if (e.getActionCommand ().equals ("unrandom")) {
			reset();
			ignorereset = 0;
			random.setFont (new Font ("Bree Serif", Font.BOLD, 60));
			random.setText("Randomize");
			random.setActionCommand("random");
		}
		//undoes previous move
		else if (e.getActionCommand ().equals ("undo")) {
			if (undocount == 0) {
				changeteam(ignoret);
				undo();
				ignorer = 1;
			}
		}
		//moves to next turn in replay
		else if (e.getActionCommand ().equals ("replaynext")) {
			if (replayturn < replay) {
				replayturn++;
				changerteam();
			}
			else {
				showStatus("That was the last turn!");
			}
			replay();
		}
		//moves to previous turn in replay
		else if (e.getActionCommand ().equals ("replayback")) {
			if (replayturn > 0) {
				replayturn--;
				changerteam();
			}
			else {
				showStatus("That was the first turn!");
			}
			replay();
		}
		//disables undo
		else if (e.getActionCommand ().equals ("disundo")) {
			undo.setVisible(false);
			showStatus("Undo disabled!");
			disableundo.setText("Enable Undo");
			disableundo.setActionCommand("enundo");
		}
		//enables undo
		else if (e.getActionCommand ().equals ("enundo")) {
			showStatus("Undo enabled!");
			undo.setVisible(true);
			disableundo.setText("Disable Undo");
			disableundo.setActionCommand("disundo");
		}
		//shows instruction screen related to action command
		else if (e.getActionCommand ().equals ("chess")) {
			disablepanels(schess);
		}
		else if (e.getActionCommand ().equals ("pawn")) {
			disablepanels(spawn);
		}
		else if (e.getActionCommand ().equals ("rook")) {
			disablepanels(srook);
		}
		else if (e.getActionCommand ().equals ("knight")) {
			disablepanels(sknight);
		}
		else if (e.getActionCommand ().equals ("bishop")) {
			disablepanels(sbishop);
		}
		else if (e.getActionCommand ().equals ("queen")) {
			disablepanels(squeen);
		}
		else if (e.getActionCommand ().equals ("king")) {
			disablepanels(sking);
		}
		else if (e.getActionCommand ().equals ("add")) {
			disablepanels(sadd);
		}
		else if (e.getActionCommand ().equals ("control")) {
			disablepanels(scontrol);
		}
		else
		{ //code to handle the game
			int n = Integer.parseInt (e.getActionCommand ());
			int x = n / col;
			int y = n % col;
			if (selected[x][y] != 's' && p != -1) {
				resetselect();
				p = -1;
				redraw();
				showStatus("Reset selection. You can now select another piece if you wish.");
			}
			else {
				//handles selection of pieces
				if (p == -1) {
					updateundo();
					if (team[x][y] == t) {
						if (piece[x][y] == 'p') {
							pawn(x,y);
						}
						else if (piece[x][y] == 'r'){
							rook(x,y);
						}
						else if (piece[x][y] == 'n') {
							knight(x,y);
						}
						else if (piece[x][y] == 'b') {
							bishop(x,y);
						}
						else if (piece[x][y] == 'q') {
							bishop(x,y);
							rook(x,y);
						}
						else if (piece[x][y] == 'k') {
							king(x,y);	
						}
						p = n;
						redraw();
					}
					else if (team[x][y] == 'a') {
						showStatus ("There are no pieces in this position!");
					}

					else {		
						showStatus("It is not your turn!");
					}
					showStatus("");
				}
				else {
					//handles movement of pieces, after selection
					int px = p/col;
					int py = p%col;
					if (selected[x][y] == 's') {

						if (piece[x][y] == 'k' && team[x][y] != t) {
							if (t == 'w') {
								winner.setText("White Wins!");
								cdLayout.show (p_card, "5");
							}
							else {
								winner.setText("Black Wins!");
								cdLayout.show (p_card, "5");
							}
						}
						if (piece[px][py] == 'p') {
							if (team[px][py] == 'w' && x == 0) {
								piece[px][py] = pawnpromo();
							}
							else if (team[px][py] == 'b' && x == 7) {
								piece[px][py] = pawnpromo();
							}
						}	
						piece[x][y] = piece[px][py];
						piece[px][py] = 'a';
						team[x][y] = team[px][py];
						team[px][py] = 'a';
					}
					if (check(x,y) != 'e'){
						if (check(x,y) == 'w') {
							JOptionPane.showMessageDialog (null, "White king is in check!","Check",JOptionPane.ERROR_MESSAGE);	
						}
						else if (check(x,y) == 'b') {
							JOptionPane.showMessageDialog (null, "Black king is in check!","Check",JOptionPane.ERROR_MESSAGE);	
						}
					}
					// things to update after every move
					changeteam(ignoret);
					soundFile.play ();
					showStatus("");
					redraw();
					if (ignorer == 0) {
						updatereplay();
					}
					ignorer = 0;
					p = -1;
				}
			}
		}
	}
	//pawn movement
	public void pawn(int x, int y) {
		if (team[x][y] == 'b' && x < 7) {
			if (piece[x+1][y] == 'a') {
				selected[x+1][y] = 's';
				if (x == 1 && piece[x+2][y] == 'a') {
					selected[x+2][y] = 's';
				}
			}
			if (x != 7 && y!=7) {
				if (piece[x+1][y+1] != 'a' && team[x+1][y+1] == 'w') {
					selected[x+1][y+1] = 's';	
				}
			}
			if (x != 7 && y!=0) {
				if (piece[x+1][y-1] != 'a' && team[x+1][y-1] == 'w') {
					selected[x+1][y-1] = 's';	
				}
			}
		}
		else if (team[x][y] == 'w' && x > 0) {
			if (piece[x-1][y] == 'a') {
				selected[x-1][y] = 's';
				if (x == 6 && piece[x-2][y] == 'a') {	
					selected[x-2][y] = 's';
				}
			}
			if (x != 0 && y!=7) {
				if (piece[x-1][y+1] != 'a' && team[x-1][y+1] == 'b') {
					selected[x-1][y+1] = 's';	
				}
			}
			if (x != 0 && y!=0) {
				if (piece[x-1][y-1] != 'a' && team[x-1][y-1] == 'b') {
					selected[x-1][y-1] = 's';	
				}
			}
		}
	}
	//rook movement
	public void rook(int x, int y) {
		// s = stop the for loop, when the next square has something inside
		int s = 0;
		for (int i = 1; i < 8; i++) {
			if (x+i < 8) {
				if (team[x+i][y] != t && s == 0) {
					selected[x+i][y] = 's';
				}
				if (piece[x+i][y] != 'a')
					s = 1;
			}
		}
		s = 0;
		for (int i = 1; i < 8; i++) {
			if (x-i >= 0) {
				if (team[x-i][y] != t && s == 0) {
					selected[x-i][y] = 's';
				}
				if (piece[x-i][y] != 'a')
					s = 1;
			}
		}
		s = 0;
		for (int i = 1; i < 8; i++) {
			if (y+i < 8) {
				if (team[x][y+i] != t && s == 0) {
					selected[x][y+i] = 's';
				}
				if (piece[x][y+i] != 'a')
					s = 1;
			}
		}
		s = 0;
		for (int i = 1; i < 8; i++) {
			if (y-i >= 0) {
				if (team[x][y-i] != t && s == 0) {
					selected[x][y-i] = 's';
				}
				if (piece[x][y-i] != 'a')
					s = 1;
			}
		}
	}
	//knight movement
	public void knight(int x, int y) {

		if(x-2 >= 0 && y-1 >=0) {
			if (team[x-2][y-1] != t)
				selected[x-2][y-1] = 's';
		}
		if(x-2 >= 0 && y+1 < 8) {
			if (team[x-2][y+1] != t)
				selected[x-2][y+1] = 's';
		}
		if(x+2 < 8 && y-1 >=0) {
			if (team[x+2][y-1] != t)
				selected[x+2][y-1] = 's';
		}
		if(x+2 < 8 && y+1 < 8) {
			if (team[x+2][y+1] != t)
				selected[x+2][y+1] = 's';
		}
		if(y-2 >= 0 && x-1 >=0) {
			if (team[x-1][y-2] != t)
				selected[x-1][y-2] = 's';
		}
		if(y-2 >= 0 && x+1 < 8) {
			if (team[x+1][y-2] != t)
				selected[x+1][y-2] = 's';
		}
		if(y+2 < 8 && x-1 >=0) {
			if (team[x-1][y+2] != t)
				selected[x-1][y+2] = 's';
		}
		if(y+2 < 8 && x+1 < 8) {
			if (team[x+1][y+2] != t)
				selected[x+1][y+2] = 's';
		}
	}
	//bishop movement
	public void bishop(int x, int y) {
		// s = stop the for loop, when the next square has something inside
		int s = 0;
		for (int i = 1; i < 8; i++) {
			if (x+i < 8 && y+i < 8) {
				if (team[x+i][y+i] != t && s == 0) {
					selected[x+i][y+i] = 's';
				}
				if (piece[x+i][y+i] != 'a')
					s = 1;
			}
		}
		s = 0;
		for (int i = 1; i < 8; i++) {
			if (x-i >= 0 && y-i >= 0) {
				if (team[x-i][y-i] != t && s == 0) {
					selected[x-i][y-i] = 's';
				}
				if (piece[x-i][y-i] != 'a')
					s = 1;
			}
		}
		s = 0;
		for (int i = 1; i < 8; i++) {
			if (y+i < 8 && x-i>=0) {
				if (team[x-i][y+i] != t && s == 0) {
					selected[x-i][y+i] = 's';
				}
				if (piece[x-i][y+i] != 'a')
					s = 1;
			}
		}
		s = 0;
		for (int i = 1; i < 8; i++) {
			if (y-i >= 0 && x+i < 8) {
				if (team[x+i][y-i] != t && s == 0) {
					selected[x+i][y-i] = 's';
				}
				if (piece[x+i][y-i] != 'a')
					s = 1;
			}
		}
	}
	public void king(int x, int y) {
		//king movement
		if(x < 7) {
			if (team[x+1][y] != t)
				selected[x+1][y] = 's';
		} if(x > 0) {
			if (team[x-1][y] != t)
				selected[x-1][y] = 's';
		}if( x < 7 && y < 7) {
			if (team[x+1][y+1] != t)
				selected[x+1][y+1] = 's';
		}if(x > 0 && y < 7) {
			if (team[x-1][y+1] != t)
				selected[x-1][y+1] = 's';
		}if(x > 0 && y > 0) {
			if (team[x-1][y-1] != t)
				selected[x-1][y-1] = 's';
		}if(x < 7 && y > 0) {
			if (team[x+1][y-1] != t)
				selected[x+1][y-1] = 's';
		}if(y > 0) {
			if (team[x][y-1] != t)
				selected[x][y-1] = 's';
		}if(y < 7) {
			if (team[x][y+1] != t)
				selected[x][y+1] = 's';
		}
	}
	//check method, checks if the king is about to be captured
	// returns team of king in check or e if none are under attack
	public char check(int x, int y) {
		switch (piece[x][y])
		{
		case 'p':
			pawn(x,y);
			if (checkking() == true) {
				resetselect();
				return team[kx][ky];
			}
			break;
		case 'n':
			knight(x,y);
			if (checkking() == true) {
				resetselect();
				return team[kx][ky];
			}
			break;
		case 'r':
			rook(x,y);
			if (checkking() == true) {
				resetselect();
				return team[kx][ky];
			}
			break;
		case 'b':
			bishop(x,y);
			if (checkking() == true) {
				resetselect();
				return team[kx][ky];
			}
			break;
		case 'q':
			rook(x,y);
			bishop(x,y);
			if (checkking() == true) {
				resetselect();
				return team[kx][ky];
			}
			break;
		}

		resetselect();
		return 'e';
	}
	//determines king position, returns true if king is under attack
	public boolean checkking() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (piece[i][j] == 'k' && selected[i][j] == 's') {
					kx = i;
					ky = j;
					return true;
				}

			}
		}
		return false;
	}
	//resets the selected array
	public void resetselect() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				selected[i][j] = 'u';
			}
		}
	}
	//updates the undo array to the last turn
	public void updateundo() {
		undocount = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				uteam[i][j] = team[i][j];
				upiece[i][j] = piece[i][j];
			}
		}
	}
	//undoes previous move
	public void undo() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				team[i][j] = uteam[i][j];
				piece[i][j] = upiece[i][j];
			}
		}
		undocount = 1;
		redraw();
	}
	//resets everything that was apart of the game, and the replay
	public void reset ()
	{ 
		t = 'w';
		rt = 'w';
		replay = 0;
		turn.setIcon(createImageIcon("white.png"));
		rturn.setIcon(createImageIcon("white.png"));
		p = -1;
		for (int i = 0 ; i < row ; i++) {
			for (int j = 0 ; j < col ; j++) {
				piece[i] [j] = rpiece[i][j];
				team[i] [j] = rteam[i][j];
				upiece[i] [j] = rpiece[i][j];
				uteam[i] [j] = rteam[i][j];
			}
		}
		resetselect();
		redraw ();
	}
	//randomizes the kings and sets up the loop for the randomizer, skipping the empty positions
	public void randomize() {
		int x1 = (int)Math.random()*2;
		int y1 = (int)Math.random()*8;
		int x2 = (int)Math.random()*2 + 6;
		int y2 = (int)Math.random()*8;
		piece[x1][y1] = 'k';
		piece[x2][y2] = 'k';
		for(int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				if (x1 != i && y1 != j || x2 != i && y2 != j)
					piece[i][j] = randomizer();
			}
			if (i == 1) {
				i = 6;
			}
		}
		showStatus ("Randomized!");
		redraw();
	}
	//randomizes individual pieces
	public char randomizer() {
		int x = (int)(Math.random() * 5);
		if (x == 0) {
			return 'p';
		}
		else if (x == 1) {
			return 'r';
		}
		else if (x == 2) {
			return 'n';
		}
		else if (x == 3) {
			return 'b';
		}
		else {
			return 'q';
		}
	}
	// lets the pawns be promoted and provides the choice
	public char pawnpromo() {
		String [] choices = {"rook", "knight", "bishop","queen"};
		String choosed = (String) JOptionPane.showInputDialog (null,
				"Choose one", "Input", JOptionPane.INFORMATION_MESSAGE, null,choices, choices[0]);
		char s = choosed.charAt(0);
		return s;
	}
	//updates the replay array
	public void updatereplay() {
		replay++;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				replayteam[replay][i][j] = team[i][j];
				replaypiece[replay][i][j] = piece[i][j];
			}
		}
	}
	//redraws the replay chess board
	public void replay() {
		int move = 0;
		for (int i = 0 ; i < row ; i++)
		{
			for (int j = 0 ; j < col ; j++)
			{
				b [move].setIcon (createImageIcon (replaybg(i,j) + String.valueOf(replaypiece [replayturn][i] [j]) + String.valueOf(replayteam [replayturn] [i] [j])+ 'u' + ".png"));
				move++;
			}
		}
	}
	// generates the replay background, was bugging when using original bg array
	public char replaybg(int i,int j) {
		if (i%2 == 1) {
			if (j%2 == 1) 
				return 'y';
			else
				return 'g';
		}
		else {
			if (j%2 == 1) 
				return 'g';
			else
				return 'y';
		}
	}
	// changes the team display in replay
	public void changerteam() {
		if (rt == 'b') {
			rt = 'w';
			rturn.setIcon(createImageIcon("white.png"));
		}
		else if (rt == 'w') {
			rt = 'b';
			rturn.setIcon(createImageIcon("black.png"));
		}
	}
	//copies original arrays into reset and undo arrays
	public void copy() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				upiece[i][j] = piece[i][j];
				rpiece[i][j] = piece[i][j];
				uteam[i][j] = team[i][j];
				rteam[i][j] = team[i][j];
			}
		}
	}
	//switches turns
	public void changeteam(int ignoret) {
		if (t == 'w' && ignoret == 0) {
			t = 'b';
			turn.setIcon(createImageIcon("black.png"));
		}
		else if (t == 'b' && ignoret == 0) {
			t = 'w';
			turn.setIcon(createImageIcon("white.png"));
		}
	}
	//disables all frames, except for exception, which is the JFrame correlating to the button clicked.
	public void disablepanels(JFrame exception) {
		instruct.setVisible(false);
		spawn.setVisible(false);
		srook.setVisible(false);
		sknight.setVisible(false);
		sbishop.setVisible(false);
		squeen.setVisible(false);
		sking.setVisible(false);
		sadd.setVisible(false);
		schess.setVisible(false);
		exception.setVisible(true);
	}
	//disables every frame
	public void disableall() {
		instruct.setVisible(false);
		spawn.setVisible(false);
		srook.setVisible(false);
		sknight.setVisible(false);
		sbishop.setVisible(false);
		squeen.setVisible(false);
		sking.setVisible(false);
		sadd.setVisible(false);
		schess.setVisible(false);
	}
	// keyboard commands for the title
	public void keyPressed( KeyEvent e ) {
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_P:
			cdLayout.show (p_card, "3");
			break;
		case KeyEvent.VK_I:
			cdLayout.show (p_card, "2");
			instruct.setVisible(true);
			break;
		case KeyEvent.VK_S:
			cdLayout.show (p_card, "6");
			break;
		}
	}

	//forced methods to be able to use keyboard
	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
	}
}