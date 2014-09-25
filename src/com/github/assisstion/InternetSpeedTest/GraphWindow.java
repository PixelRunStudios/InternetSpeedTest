package com.github.assisstion.InternetSpeedTest;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import com.github.assisstion.Shared.Pair;

public class GraphWindow extends JFrame{

	/**
	 *
	 */
	private static final long serialVersionUID = 8002278492569073893L;
	private JPanel contentPane;
	public LineGraphPanel timePanel;
	public BarGraphPanel sitePanel;
	public LineGraphPanel runPanel;
	protected MainGUI gui;

	/**
	 * Create the frame.
	 */
	public GraphWindow(MainGUI gui){
		this.gui = gui;
		setTitle("Graph Window");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		reset();
	}

	public void reset(){
		timePanel = new LineGraphPanel(new TimeHolder(), true);
		sitePanel = new BarGraphPanel(new SiteHolder());
		runPanel = new LineGraphPanel(new TimeHolder(), false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		tabbedPane.addTab("By Time", timePanel);
		tabbedPane.addTab("By Run", runPanel);
		tabbedPane.addTab("By Website", sitePanel);

		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				try{
					GraphWindow frame = new GraphWindow(new MainGUI());
					frame.setVisible(true);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	public class TimeHolder implements MapHolder<Long, Double, TreeMap<Long, Double>>{

		@Override
		public TreeMap<Long, Double> getMap(){
			if(gui.getProcess() == null){
				return new TreeMap<Long, Double>();
			}
			return gui.getProcess().timeMap;
		}

	}

	public class SiteHolder implements MapHolder<String, Pair<Long, Long>,
	LinkedHashMap<String, Pair<Long, Long>>>{

		@Override
		public LinkedHashMap<String, Pair<Long, Long>> getMap(){
			if(gui.getProcess() == null){
				return new LinkedHashMap<String, Pair<Long, Long>>();
			}
			return gui.getProcess().siteMap;
		}

	}

	public class RuneHolder implements MapHolder<Long, Double, TreeMap<Long, Double>>{

		@Override
		public TreeMap<Long, Double> getMap(){
			if(gui.getProcess() == null){
				return new TreeMap<Long, Double>();
			}
			return gui.getProcess().runMap;
		}

	}

}
