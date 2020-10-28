import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.toedter.calendar.JCalendar;
import com.toedter.components.JSpinField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;

public class VentanaEmpleado extends JFrame {

	private JPanel contentPane;
	private JTable tableIda;
	private Conexion cnxBD;
	private int legajo;
	private JComboBox cBoxCiuIda;
	private JComboBox cBoxCiuDes;
	private JLabel lblFechaVueloIda;
	private JLabel lblFechaVueloVuelta;
	private JDateChooser dateChIda;
	private JDateChooser dateChVuelta;
	private JRadioButton rdbtnIda;
	private JRadioButton rdbtnIdaVue;
	private JScrollPane scrollTableIda;
	private DefaultTableModel tableIdaModel;
	private DefaultTableModel tableVueltaModel;
	private JTable tableVuelta;
	private JPanel panelIda;
	private JPanel panelVuelta;
	private JTable tableClasesIda;
	private DefaultTableModel tableClasesIdaModel;
	private JPanel panelClasesIda;
	private JPanel panelClasesVuelta;
	private JScrollPane scrollTableClasesVuelta;
	private DefaultTableModel tableClasesVueltaModel;
	private JTable tableClasesVuelta;
	private ListSelectionModel model1;
	private ListSelectionModel model2;
	private ListSelectionModel model3;
	private ListSelectionModel model4;
	private JButton btnReservaIda;
	private JButton btnReservaIdaVuelta;
	
		

	{
	    try {
	        // select Look and Feel
	    	UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
	        // start application
	        
	    }
	    catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	/**
	 * Create the frame.
	 */
	public VentanaEmpleado(Conexion c, int leg) {
		super();
		cnxBD=c;
		legajo=leg;
		this.setResizable(false);
		initGUI();
	}
	
	private void initGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 1075);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 215, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelDatos = new JPanel();
		panelDatos.setBackground(new Color(255, 215, 0));
		panelDatos.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Datos Vuelo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDatos.setBounds(33, 13, 440, 423);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel lblCiudadOrigen = new JLabel("Ciudad Origen:");
		lblCiudadOrigen.setBounds(23, 58, 93, 29);
		panelDatos.add(lblCiudadOrigen);
		
		cBoxCiuIda = new JComboBox();
		cBoxCiuIda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		cBoxCiuIda.setBounds(148, 58, 268, 29);
		panelDatos.add(cBoxCiuIda);
		
		JLabel lblCiudadDestino = new JLabel("Ciudad Destino:");
		lblCiudadDestino.setBounds(23, 112, 93, 29);
		panelDatos.add(lblCiudadDestino);
		
		cBoxCiuDes = new JComboBox();
		cBoxCiuDes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		cBoxCiuDes.setBounds(148, 112, 268, 29);
		panelDatos.add(cBoxCiuDes);
		
		cargarCiudades();//cargo ciudades para ambos comboBox 
		
		rdbtnIda = new JRadioButton("Vuelos Ida");
		rdbtnIda.setBackground(new Color(255, 215, 0));
		rdbtnIda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarDateChoosers();
			}
		});
		rdbtnIda.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnIda.setBounds(48, 173, 127, 25);
		panelDatos.add(rdbtnIda);
		
		rdbtnIdaVue = new JRadioButton("Vuelos Ida y Vuelta");
		rdbtnIdaVue.setBackground(new Color(255, 215, 0));
		rdbtnIdaVue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarDateChoosers();
			}
		});
		rdbtnIdaVue.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnIdaVue.setBounds(227, 173, 154, 25);
		panelDatos.add(rdbtnIdaVue);
		
		ButtonGroup idaOidaVue= new ButtonGroup();
		idaOidaVue.add(rdbtnIda);
		idaOidaVue.add(rdbtnIdaVue); 
		
		lblFechaVueloIda = new JLabel("Fecha Vuelo Ida:");
		lblFechaVueloIda.setBounds(23, 232, 121, 29);
		panelDatos.add(lblFechaVueloIda);
		lblFechaVueloIda.setVisible(false);
		
		lblFechaVueloVuelta = new JLabel("Fecha Vuelo Vuelta:");
		lblFechaVueloVuelta.setBounds(23, 287, 121, 29);
		panelDatos.add(lblFechaVueloVuelta);
		lblFechaVueloVuelta.setVisible(false);
		
		dateChIda = new JDateChooser();
		dateChIda.setBounds(148, 232, 187, 29);
		panelDatos.add(dateChIda);
		dateChIda.setVisible(false);
		
		dateChVuelta = new JDateChooser();
		dateChVuelta.setBounds(148, 287, 187, 29);
		panelDatos.add(dateChVuelta);
		dateChVuelta.setVisible(false);
					
		JButton btnBuscarVuelos = new JButton("Buscar Vuelos Disponibles");
		btnBuscarVuelos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableIdaModel.setRowCount(0);//limpio tabla ida
				tableVueltaModel.setRowCount(0);
				tableClasesIdaModel.setRowCount(0);
				tableClasesVueltaModel.setRowCount(0);
				btnBuscarVuelosDisponiblesActionPerformed(e);
			}

		});
		btnBuscarVuelos.setBounds(124, 347, 193, 50);
		panelDatos.add(btnBuscarVuelos);
		
		panelIda = new JPanel();
		panelIda.setBackground(new Color(255, 215, 0));
		panelIda.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Vuelos Disponibles Ida", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelIda.setBounds(33, 449, 701, 491);
		contentPane.add(panelIda);
		panelIda.setPreferredSize(getSize());
		panelIda.setVisible(true);

		scrollTableIda = new JScrollPane();
		scrollTableIda.getViewport().setBackground(Color.WHITE);
		tableIdaModel = new DefaultTableModel();
		tableIda = new JTable();
		tableIda.setAutoCreateRowSorter(true);
		tableIda.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableIda.doLayout();
			
		scrollTableIda.setViewportView(tableIda);	
		
		model1= tableIda.getSelectionModel();
		model1.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				if(!model1.isSelectionEmpty()) {
					panelClasesIda.setVisible(true);
					btnReservaIda.setVisible(true);
					tableClasesIdaModel.setRowCount(0);
					DefaultTableModel modelTableIda = (DefaultTableModel)tableIda.getModel();
					int selectedRowIndex = tableIda.getSelectedRow();
					String vuelo= modelTableIda.getValueAt(selectedRowIndex,0).toString();
			
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String fecha= format.format(dateChIda.getDate());
					try {
						java.sql.Statement stm = cnxBD.getCnx().createStatement();
						String instruccion= "SELECT clase,asientos_disponibles,precio "
											+"FROM vuelos_disponibles "
											+"WHERE vuelo='"+vuelo+"' AND fecha='"+fecha+"'";
						java.sql.ResultSet rs=stm.executeQuery(instruccion);
						insertarResulsetEnTablaModel(rs,tableClasesIdaModel);				
						tableClasesIda.setModel(tableClasesIdaModel); 			
						
						rs.close();
						stm.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}	
				}
			}
			 
		});
		
		GroupLayout gl_panelIda = new GroupLayout(panelIda);
		gl_panelIda.setHorizontalGroup(
			gl_panelIda.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollTableIda, GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
		);
		gl_panelIda.setVerticalGroup(
			gl_panelIda.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollTableIda, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
		);
		panelIda.setLayout(gl_panelIda);
		panelIda.setVisible(false);

		
		panelVuelta = new JPanel();
		panelVuelta.setBackground(new Color(255, 215, 0));
		panelVuelta.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Vuelos Disponibles Vuelta", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelVuelta.setBounds(756, 449, 701, 491);
		contentPane.add(panelVuelta);
		
		JScrollPane scrollTableVuelta = new JScrollPane();
		scrollTableVuelta.getViewport().setBackground(Color.WHITE);
		tableVueltaModel = new DefaultTableModel();
		tableVuelta = new JTable();
		tableVuelta.setAutoCreateRowSorter(true);
		tableVuelta.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableVuelta.doLayout();

		scrollTableVuelta.setViewportView(tableVuelta);	

		model2= tableVuelta.getSelectionModel();
		model2.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				if(!model2.isSelectionEmpty()) {
					panelClasesVuelta.setVisible(true);
					btnReservaIdaVuelta.setVisible(true);  
					tableClasesVueltaModel.setRowCount(0);
					DefaultTableModel modelTableVuelta = (DefaultTableModel)tableVuelta.getModel();
					int selectedRowIndex = tableVuelta.getSelectedRow();
					String vuelo= modelTableVuelta.getValueAt(selectedRowIndex,0).toString();
				
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String fecha= format.format(dateChVuelta.getDate());
					
					try {
						java.sql.Statement stm = cnxBD.getCnx().createStatement();
						String instruccion= "SELECT clase,asientos_disponibles,precio "
											+ "FROM vuelos_disponibles "
											+"WHERE vuelo='"+vuelo+"' AND fecha='"+fecha+"'";
						java.sql.ResultSet rs=stm.executeQuery(instruccion);
						insertarResulsetEnTablaModel(rs,tableClasesVueltaModel);				
						tableClasesVuelta.setModel(tableClasesVueltaModel); 			
						
						rs.close();
						stm.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}
			}
		});

		GroupLayout gl_panelVuelta = new GroupLayout(panelVuelta);
		gl_panelVuelta.setHorizontalGroup(
			gl_panelVuelta.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollTableVuelta, GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
		);
		gl_panelVuelta.setVerticalGroup(
			gl_panelVuelta.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollTableVuelta, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
		);
		panelVuelta.setLayout(gl_panelVuelta);
		panelVuelta.setVisible(false);
		
		panelClasesIda = new JPanel();
		panelClasesIda.setBackground(new Color(255, 215, 0));
		panelClasesIda.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Clases Disponibles Vuelos Ida", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelClasesIda.setBounds(525, 13, 440, 295);
		contentPane.add(panelClasesIda);
		
		JScrollPane scrollTableClasesIda = new JScrollPane();
		scrollTableClasesIda.getViewport().setBackground(Color.WHITE); 
		tableClasesIdaModel = new DefaultTableModel();
		tableClasesIda = new JTable();
		tableClasesIda.setAutoCreateRowSorter(true);//
		tableClasesIda.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//
		tableClasesIda.doLayout();//
		scrollTableClasesIda.setViewportView(tableClasesIda);//	
		
		model3= tableClasesIda.getSelectionModel();
		
		GroupLayout gl_panelClasesIda = new GroupLayout(panelClasesIda);
		gl_panelClasesIda.setHorizontalGroup(
			gl_panelClasesIda.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollTableClasesIda, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
		);
		gl_panelClasesIda.setVerticalGroup(
			gl_panelClasesIda.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollTableClasesIda, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
		);
		panelClasesIda.setLayout(gl_panelClasesIda);
		
		panelClasesVuelta = new JPanel();
		panelClasesVuelta.setBackground(new Color(255, 215, 0));
		panelClasesVuelta.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Clases Disponibles Vuelos Vuelta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelClasesVuelta.setBounds(1017, 13, 440, 295);
		contentPane.add(panelClasesVuelta);
		
		JScrollPane scrollTableClasesVuelta = new JScrollPane();
		scrollTableClasesVuelta.getViewport().setBackground(Color.WHITE); 
		tableClasesVueltaModel = new DefaultTableModel();
		tableClasesVuelta = new JTable();
		tableClasesVuelta.setAutoCreateRowSorter(true);//
		tableClasesVuelta.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//
		tableClasesVuelta.doLayout();//
		scrollTableClasesVuelta.setViewportView(tableClasesVuelta);//	
		
		model4= tableClasesVuelta.getSelectionModel();

		GroupLayout gl_panelClasesVuelta = new GroupLayout(panelClasesVuelta);
		gl_panelClasesVuelta.setHorizontalGroup(
			gl_panelClasesVuelta.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollTableClasesVuelta, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
		);
		gl_panelClasesVuelta.setVerticalGroup(
			gl_panelClasesVuelta.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollTableClasesVuelta, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
		);
		panelClasesVuelta.setLayout(gl_panelClasesVuelta);
		
		JButton btnCerrarSesion = new JButton("Cerrar Sesion Empleado ");
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCerrarSesionActionPerformed(e);
			}

		});
		btnCerrarSesion.setBounds(614, 968, 261, 59);
		contentPane.add(btnCerrarSesion);
		
		btnReservaIda = new JButton("Reservar Vuelo Ida");
		btnReservaIda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnReservaIdaActionPerformed(evt);
			}

		});
		btnReservaIda.setBounds(627, 365, 236, 49);
		contentPane.add(btnReservaIda);
		
		btnReservaIdaVuelta = new JButton("Reservar Vuelos Ida y Vuelta");
		btnReservaIdaVuelta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnReservaIdaVueltaActionPerformed(evt);
			}
	
		});
		btnReservaIdaVuelta.setBounds(1120, 365, 234, 49);
		contentPane.add(btnReservaIdaVuelta);
		
		panelClasesIda.setVisible(false);
		panelClasesVuelta.setVisible(false);
		btnReservaIda.setVisible(false); 
		btnReservaIdaVuelta.setVisible(false); 
	}

	private void btnReservaIdaActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		if(!model1.isSelectionEmpty() && !model3.isSelectionEmpty()) {
			//System.out.println("Esta seleccionado una fila");
			DefaultTableModel modelTableIda = (DefaultTableModel)tableIda.getModel();
			int selectedRowIndex1 = tableIda.getSelectedRow();
			DefaultTableModel modelTableClasesIda = (DefaultTableModel)tableClasesIda.getModel();
			int selectedRowIndex2 = tableClasesIda.getSelectedRow();
			
			String vuelo= modelTableIda.getValueAt(selectedRowIndex1,0).toString();
			Date fechaIda= dateChIda.getDate();
			String clase= modelTableClasesIda.getValueAt(selectedRowIndex2,0).toString();
			
			VentanaPasajero frameDatosPasajero= new VentanaPasajero(cnxBD,vuelo,fechaIda,clase,null,null,null,legajo,this);
			frameDatosPasajero.setTitle("Datos Pasajero");
			frameDatosPasajero.setLocationRelativeTo(null);
			frameDatosPasajero.setVisible(true);

			
		}
		else {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un vuelo de ida y una clase para el vuelo de ida antes de realizar la reserva.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void btnReservaIdaVueltaActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		if(!model1.isSelectionEmpty() && !model3.isSelectionEmpty()
		   && !model2.isSelectionEmpty() && !model4.isSelectionEmpty()) {
			
			DefaultTableModel modelTableIda = (DefaultTableModel)tableIda.getModel();
			int selectedRowIndex1 = tableIda.getSelectedRow();
			DefaultTableModel modelTableClasesIda = (DefaultTableModel)tableClasesIda.getModel();
			int selectedRowIndex2 = tableClasesIda.getSelectedRow();
			DefaultTableModel modelTableVuelta = (DefaultTableModel)tableVuelta.getModel();
			int selectedRowIndex3 = tableVuelta.getSelectedRow();
			DefaultTableModel modelTableClasesVuelta = (DefaultTableModel)tableClasesVuelta.getModel();
			int selectedRowIndex4 = tableClasesVuelta.getSelectedRow();
			
			String vueloIda= modelTableIda.getValueAt(selectedRowIndex1,0).toString();
			String vueloVuelta= modelTableVuelta.getValueAt(selectedRowIndex3,0).toString();
			Date fechaIda= dateChIda.getDate();
			Date fechaVuelta= dateChVuelta.getDate();
			String claseIda= modelTableClasesIda.getValueAt(selectedRowIndex2,0).toString();
			String claseVuelta= modelTableClasesVuelta.getValueAt(selectedRowIndex4,0).toString();
			
			VentanaPasajero frameDatosPasajero= new VentanaPasajero(cnxBD,vueloIda,fechaIda,claseIda,vueloVuelta,fechaVuelta,claseVuelta,legajo,this);
			frameDatosPasajero.setTitle("Datos Pasajero");
			frameDatosPasajero.setLocationRelativeTo(null); 
			frameDatosPasajero.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un vuelo de ida y una clase para el vuelo de ida, ademas de un vuelo de vuelta y una clase para el vuelo de vuelta antes de realizar la reserva.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void cargarCiudades() {
		// TODO Auto-generated method stub
		try {
			java.sql.Statement stm = cnxBD.getCnx().createStatement();
			String instruccion= "SELECT pais,estado,ciudad FROM ubicaciones";
			java.sql.ResultSet rs=stm.executeQuery(instruccion);
			String ciudad="";
			while(rs.next()){
				ciudad=rs.getString("ciudad")+", "+rs.getString("estado")+", "+rs.getString("pais");
				cBoxCiuIda.addItem(ciudad);
				cBoxCiuDes.addItem(ciudad);
			}
			rs.close();
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void mostrarDateChoosers() {
		// TODO Auto-generated method stub
		if(rdbtnIda.isSelected()) {
			lblFechaVueloIda.setVisible(true);
			dateChIda.setVisible(true);
			lblFechaVueloVuelta.setVisible(false);
			dateChVuelta.setVisible(false);
		}
		else {
			lblFechaVueloIda.setVisible(true);
			dateChIda.setVisible(true);
			lblFechaVueloVuelta.setVisible(true);
			dateChVuelta.setVisible(true);
		}			
	}
	
	private void btnBuscarVuelosDisponiblesActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		panelClasesIda.setVisible(false); 
		panelClasesVuelta.setVisible(false); 
		panelIda.setVisible(false);
		panelVuelta.setVisible(false); 
		btnReservaIda.setVisible(false); 
		btnReservaIdaVuelta.setVisible(false); 
		
		String ciuSalida= (String)cBoxCiuIda.getSelectedItem();
		String[] ciudadEstadoPais= ciuSalida.split(", ",3);
		String ciudadSal= ciudadEstadoPais[0];
		
		String ciuDestino= (String)cBoxCiuDes.getSelectedItem();
		ciudadEstadoPais= ciuDestino.split(", ",3);
		String ciudadDes= ciudadEstadoPais[0];

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaIda= dateChIda.getDate();
		Date fechaVuelta= dateChVuelta.getDate();		
		
		if(!ciuSalida.equals(ciuDestino)) {	
	
			try{		
				java.sql.Statement stm = cnxBD.getCnx().createStatement();
				java.sql.ResultSet rs=null;
		
				if(!rdbtnIda.isSelected() && !rdbtnIdaVue.isSelected()) {
					JOptionPane.showMessageDialog(this,"Seleccione si desea saber disponibilidad solo de vuelos de ida o de vuelos de ida y vuelos de vuelta.","Datos incompletos.",JOptionPane.ERROR_MESSAGE);
				}
				else {
					if(rdbtnIda.isSelected()) {
						if(dateChIda.getDate()==null) {
							JOptionPane.showMessageDialog(this,"Debe seleccionar fecha deseada para vuelo de ida.","Datos incompletos.",JOptionPane.ERROR_MESSAGE);
						}
						else {//saco fecha de ida para consulta y ciu salida y ciu destino
							panelVuelta.setVisible(false);
							panelIda.setVisible(true);
							String stringDate = format.format(fechaIda);
							String instruccion= "SELECT DISTINCT vuelo AS Numero_de_vuelo,"
								+ "aeropuerto_salida AS Aeropuerto_salida,"
								+ "hora_sale AS Hora_salida,"
								+ "aeropuerto_llegada AS Aeropuerto_llegada,"
								+ "hora_llega AS Hora_llegada,"
								+ "modelo_avion AS Modelo_de_avion,"
								+ "tiempo_estimado_de_vuelo AS Tiempo_estimado "
								+ "FROM vuelos_disponibles "
								+ "WHERE ciudad_aero_salida='"+ciudadSal+"' AND ciudad_aero_llegada='"+ciudadDes+"' AND fecha='"+stringDate+"'";
							rs=stm.executeQuery(instruccion);
							insertarResulsetEnTablaModel(rs,tableIdaModel);				
							tableIda.setModel(tableIdaModel);
							
						}
					}
					else {//estaria seleccionado rdbtnIdaVue. Checkear que ambas fechas sean distintas de NULL
						if(dateChIda.getDate()==null || dateChVuelta.getDate()==null) {
							JOptionPane.showMessageDialog(this,"Debe seleccionar fecha deseada para vuelo de ida y fecha deseada para vuelo de vuelta.","Datos incompletos.",JOptionPane.ERROR_MESSAGE);
						}
					
						else {//saco ambas fechas para consulta PERO comparo que fechaIda<=fechaVuelta
						
							String stringFechaIda = format.format(fechaIda);
							String stringFechaVuelta= format.format(fechaVuelta);
					
							if(stringFechaIda.compareTo(stringFechaVuelta) < 0 || stringFechaIda.compareTo(stringFechaVuelta) == 0 	) {
								
								panelVuelta.setVisible(true);
								panelIda.setVisible(true);
							
								String instruccion= "SELECT DISTINCT vuelo AS Numero_de_vuelo,"
									+ "aeropuerto_salida AS Aeropuerto_salida,"
									+ "hora_sale AS Hora_salida,"
									+ "aeropuerto_llegada AS Aeropuerto_llegada,"
									+ "hora_llega AS Hora_llegada,"
									+ "modelo_avion AS Modelo_de_avion,"
									+ "tiempo_estimado_de_vuelo AS Tiempo_estimado "
									+ "FROM vuelos_disponibles "
									+ "WHERE ciudad_aero_salida='"+ciudadSal+"' AND ciudad_aero_llegada='"+ciudadDes+"' AND fecha='"+stringFechaIda+"'";
								rs=stm.executeQuery(instruccion);
								insertarResulsetEnTablaModel(rs,tableIdaModel);				
								tableIda.setModel(tableIdaModel);
						
								instruccion= "SELECT DISTINCT vuelo AS Numero_de_vuelo,"
								+ "aeropuerto_salida AS Aeropuerto_salida,"
								+ "hora_sale AS Hora_salida,"
								+ "aeropuerto_llegada AS Aeropuerto_llegada,"
								+ "hora_llega AS Hora_llegada,"
								+ "modelo_avion AS Modelo_de_avion,"
								+ "tiempo_estimado_de_vuelo AS Tiempo_estimado "
								+ "FROM vuelos_disponibles "
								+ "WHERE ciudad_aero_salida='"+ciudadDes+"' AND ciudad_aero_llegada='"+ciudadSal+"' AND fecha='"+stringFechaVuelta+"'";
								rs=stm.executeQuery(instruccion);
								insertarResulsetEnTablaModel(rs,tableVueltaModel);				
								tableVuelta.setModel(tableVueltaModel);
							}
							else {//error con fechas ingresadas. fecha de vuelta anterior a fecha de ida
								panelVuelta.setVisible(false);
								panelIda.setVisible(false);
								JOptionPane.showMessageDialog(this,"La fecha de vuelo de vuelta no puede ser anterior a la fecha de vuelo de ida.","Datos Incorrectos.",JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
				if(rs!=null)
					rs.close(); 
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {//se eligieron misma ciudad para origen y destino
			JOptionPane.showMessageDialog(this,"Debe seleccionar una ciudad destino de vuelo distinta a la ciudad origen de vuelo.","Datos Incorrectos.",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void insertarResulsetEnTablaModel(ResultSet rs, DefaultTableModel tabla) {
		try {
			ResultSetMetaData metaDatos = (ResultSetMetaData) rs.getMetaData();
			// Se obtiene el n�mero de columnas.
			int numeroColumnas = metaDatos.getColumnCount();
			// Se crea un array de etiquetas para rellenar
			Object[] etiquetasColumnas = new Object[numeroColumnas];
			// Se obtiene cada una de las etiquetas para cada columna
			for (int i = 0; i < numeroColumnas; i++){
				// Nuevamente, para ResultSetMetaData la primera columna es la 1. 
				etiquetasColumnas[i] = metaDatos.getColumnLabel(i + 1); 
			}
			tabla.setColumnIdentifiers(etiquetasColumnas);
		
			//Bucle para cada resultado en la consulta
			while (rs.next()){
				// Se crea un array que ser� una de las filas de la tabla. 
				Object [] fila = new Object[numeroColumnas]; 

				// Se rellena cada posici�n del array con una de las columnas de la tabla en base de datos.
				for (int i=0;i<numeroColumnas;i++)
					fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.

				// Se a�ade al modelo la fila completa.
				tabla.addRow(fila); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void btnCerrarSesionActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		cnxBD.desconectarBD();
		cnxBD=new Conexion();
		LoginEmpleado frameLoginEmpleado= new LoginEmpleado(cnxBD);
		frameLoginEmpleado.setLocationRelativeTo(null);
		frameLoginEmpleado.setVisible(true);
		this.dispose();
	}
	
	public DefaultTableModel getTableClasesIdaModel() {
		return tableClasesIdaModel;
	}
	public JTable getTableClasesIda() {
		return tableClasesIda;
	}
	public DefaultTableModel getTableClasesVueltaModel() {
		return tableClasesVueltaModel;
	}
	public JTable getTableClasesVuelta() {
		return tableClasesVuelta;
	}
}
