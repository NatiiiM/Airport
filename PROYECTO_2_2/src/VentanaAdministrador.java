import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import java.awt.Button;
import java.awt.Label;
import javax.swing.JScrollBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JButton;
import java.awt.Font;

public class VentanaAdministrador extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel tableResultadosModel;
	private JTable tableResultados;
	private Conexion cnxBD;
	private JTextArea txtConsulta;
	private JList listaTablas;
	private DefaultListModel model;
	private DefaultListModel modelListaTablas;
	private DefaultListModel modelListaAtributos;
	private JList listaAtributos;
	private JPanel panelListaAtributos;
	private JPanel panelConsultas;
	private JButton button;
	
	/*
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
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
	public VentanaAdministrador(Conexion c) {
		super();
		cnxBD=c;
		this.setResizable(false);
		initGUI();
		cargarNombresTablas();
	}
	
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 215, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelConsultas = new JPanel();
		panelConsultas.setBackground(new Color(255, 215, 0));
		panelConsultas.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Consultas SQL ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelConsultas.setBounds(12, 13, 750, 927);
		contentPane.add(panelConsultas);
		
		txtConsulta = new JTextArea();
		button = new JButton("EJECUTAR SENTENCIA SQL");
		button.setFont(new Font("Dialog", Font.BOLD, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableResultadosModel.setRowCount(0); 
				btnActionPerformed(e);
			}

		});
		
		JScrollPane scrollTableResultados = new JScrollPane();
		scrollTableResultados.getViewport().setBackground(Color.WHITE);
		tableResultadosModel = new DefaultTableModel();
		tableResultados = new JTable();
		tableResultados.setAutoCreateRowSorter(true);
		resizeColumnWidth(tableResultados);
		tableResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//-------------------------------------------------------------------------------------------
		//resizeColumnWidth2(tableResultados);
		//-------------------------------------------------------------------------------------------
		tableResultados.doLayout();
		scrollTableResultados.setViewportView(tableResultados);
		
		GroupLayout gl_panelConsultas = new GroupLayout(panelConsultas);
		gl_panelConsultas.setHorizontalGroup(
			gl_panelConsultas.createParallelGroup(Alignment.LEADING)
				.addComponent(txtConsulta, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
				.addComponent(button, Alignment.CENTER, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
				.addComponent(scrollTableResultados, GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
		);
		gl_panelConsultas.setVerticalGroup(
			gl_panelConsultas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelConsultas.createSequentialGroup()
					.addComponent(txtConsulta, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollTableResultados, GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
		);
		panelConsultas.setLayout(gl_panelConsultas);	
		
		JPanel panelListaTablas = new JPanel();
		panelListaTablas.setBackground(new Color(255, 215, 0));
		panelListaTablas.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Tablas BD Vuelos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelListaTablas.setBounds(854, 13, 333, 428);
		contentPane.add(panelListaTablas);
		
		JScrollPane scrollListaTablas = new JScrollPane();
		scrollListaTablas.getViewport().setBackground(Color.WHITE);
		
		GroupLayout gl_panelListaTablas = new GroupLayout(panelListaTablas);
		gl_panelListaTablas.setHorizontalGroup(
			gl_panelListaTablas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelListaTablas.createSequentialGroup()
					.addComponent(scrollListaTablas, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(263, Short.MAX_VALUE))
		);
		gl_panelListaTablas.setVerticalGroup(
			gl_panelListaTablas.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollListaTablas, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
		);
		
		modelListaTablas = new DefaultListModel<>();
		listaTablas = new JList(modelListaTablas);
		listaTablas.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(!panelListaAtributos.isVisible())
					panelListaAtributos.setVisible(true);
				cargarListaAtributos();
			}
		});
		listaTablas.setBackground(Color.WHITE);
		scrollListaTablas.setViewportView(listaTablas);
		panelListaTablas.setLayout(gl_panelListaTablas);
		
		panelListaAtributos = new JPanel();
		panelListaAtributos.setBackground(new Color(255, 215, 0));
		panelListaAtributos.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Atributos Tabla Seleccionada", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelListaAtributos.setBounds(854, 493, 333, 447);
		contentPane.add(panelListaAtributos);
		 
		
		JScrollPane scrollListaAtributos = new JScrollPane();
		GroupLayout gl_panelAtributosTabla = new GroupLayout(panelListaAtributos);
		gl_panelAtributosTabla.setHorizontalGroup(
			gl_panelAtributosTabla.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollListaAtributos, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
		);
		gl_panelAtributosTabla.setVerticalGroup(
			gl_panelAtributosTabla.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollListaAtributos, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
		);
		
		modelListaAtributos = new DefaultListModel<>();
		listaAtributos = new JList(modelListaAtributos);
		listaAtributos.setBackground(Color.WHITE);
		scrollListaAtributos.setViewportView(listaAtributos);
		panelListaAtributos.setLayout(gl_panelAtributosTabla);
		
		JButton btnSalir = new JButton("Cerrar Sesion Administrador");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSalirActionPerformed(e);
			}

		});
		btnSalir.setBounds(1232, 438, 214, 60);
		contentPane.add(btnSalir);
		panelListaAtributos.setVisible(false);
		
	
	}
	
	private boolean consultaModificacion(String consultas) {
			// TODO Auto-generated method stub
		return consultas.contains("INSERT") || consultas.contains("UPDATE") || consultas.contains("DELETE") 
		|| consultas.contains("CREATE") || consultas.contains("GRANT") || consultas.contains("DROP") || consultas.contains("ALTER");
	}

    private String showUpdateResult(String consultas) {
        String[] aux= consultas.split(" ");
        int c=0;
        boolean termine=false;
        
        if(aux[0].equals("DROP"))
        	return "SHOW TABLES;";
        
        while(!termine){
            if(!aux[c].equals("FROM") & !aux[c].equals("INTO") & !aux[c].equals("UPDATE") & !aux[c].equals("TABLE") & !aux[c].equals("VIEW"))
                c++;
            else
                termine=true;
        }
        String tabla=aux[c+1];
        return "SELECT * FROM "+tabla+";";
    }
    
	private void btnActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		try{
			// se crea una sentencia o comando jdbc para realizar la consulta
	        Statement stmt = cnxBD.getCnx().createStatement();

	        // se ejecuta la sentencia y se recibe un resultset
	        String consultas= this.txtConsulta.getText().trim().toUpperCase();
	        boolean isUpdate= consultaModificacion(consultas);
	        ResultSet rs;
	         
	        if(isUpdate){
	        	stmt.executeUpdate(this.txtConsulta.getText().trim());
	        	JOptionPane.showMessageDialog(this,"La modificacion se realizo de manera correcta.","Informacion",JOptionPane.INFORMATION_MESSAGE);
	            consultas=showUpdateResult(consultas); //si la consulta no esta bien escrita para este momento tuvo que haber fallado la ejecucion   
	        }
	         
	         rs= stmt.executeQuery(consultas);
	         
	         insertarResulsetEnTablaModel(rs,tableResultadosModel);
	         tableResultados.setModel(tableResultadosModel);

	         rs.close();
	         stmt.close();
	         
	         cargarNombresTablas();//mostrar nuevamente lista nombres tablas por si se modifico BD
	         
		}catch (SQLException ex){
	         // en caso de error, se muestra la causa en la consola
	    	  
	    	 String estado=ex.getSQLState();
	    	 if(estado.equals("23000"))
	    		 JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
	                     "No se pueden borrar tablas que corresponden al modelo \n de la base de datos.", 
	                     "Error.",
	                     JOptionPane.ERROR_MESSAGE);
	    	 else {
	    		 System.out.println("SQLException: " + ex.getMessage());
	    		 System.out.println("SQLState: " + ex.getSQLState());
	    		 System.out.println("VendorError: " + ex.getErrorCode());
	    		 JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
	                                       ex.getMessage() + "\n", 
	                                       "Error al ejecutar la consulta.",
	                                       JOptionPane.ERROR_MESSAGE);
	    	 }
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
	
	private void cargarNombresTablas(){
		try {
			modelListaTablas.removeAllElements();
			if (cnxBD.getCnx().isValid(10)){
				java.sql.Statement st1= cnxBD.getCnx().createStatement();
				String instruccion="SHOW TABLES";
				java.sql.ResultSet rs=st1.executeQuery(instruccion);
				while (rs.next()){
					modelListaTablas.addElement(rs.getString("Tables_in_vuelos"));
				}
				
				rs.close();
				st1.close();
			} else {
					JOptionPane.showMessageDialog(null,"La sesion se ha vencido. Por favor, vuelva a ingresar.","Error", JOptionPane.PLAIN_MESSAGE);
			}
		}catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,"Ha ocurrido un error en la operacion.","Error", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	private void cargarListaAtributos(){
		try {
			modelListaAtributos.removeAllElements();
			if (cnxBD.getCnx().isValid(10)){
				
				java.sql.Statement st1= cnxBD.getCnx().createStatement();
				
				if (listaTablas.getSelectedValue()!=null){
					String instruccion="DESCRIBE "+listaTablas.getSelectedValue().toString();
					java.sql.ResultSet rs=st1.executeQuery(instruccion);
					while (rs.next()){
						modelListaAtributos.addElement(rs.getString("Field"));
					}
					rs.close();
					st1.close();	
				}
			}
			else {
				JOptionPane.showMessageDialog(null,"La sesion se ha vencido. Por favor, vuelva a ingresar.","Error", JOptionPane.PLAIN_MESSAGE);
			}
		} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null,"Ha ocurrido un error en la operacion.","Error", JOptionPane.PLAIN_MESSAGE);
		}
	}  

	private void btnSalirActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		cnxBD.desconectarBD();
		Login frameUsuario= new Login();
		frameUsuario.setLocationRelativeTo(null);//centra el frame 
		frameUsuario.setVisible(true);
		this.dispose();
	}
	
	public void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 250; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        if(width > 300)
	            width=300;
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
	
	public void resizeColumnWidth2(JTable table){
		for (int column = 0; column < table.getColumnCount(); column++){
			TableColumn tableColumn = table.getColumnModel().getColumn(column);
			int preferredWidth = tableColumn.getMinWidth();
			int maxWidth = tableColumn.getMaxWidth();

			for (int row = 0; row < table.getRowCount(); row++)
			{
				TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
				Component c = table.prepareRenderer(cellRenderer, row, column);
				int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
				preferredWidth = Math.max(preferredWidth, width);

				//  We've exceeded the maximum width, no need to check other rows

				if (preferredWidth >= maxWidth)
				{
					preferredWidth = maxWidth;
					break;
				}
			}

			tableColumn.setPreferredWidth( preferredWidth );
		}
	}
}
