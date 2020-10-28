import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class VentanaPasajero extends JFrame {

	private JPanel contentPane;
	private JTextField txtBoxTipoDoc;
	private JTextField txtBoxNroDoc;
	private Conexion cnxBD;
	private String nroVueloIda;
	private Date fechaIda;
	private String claseIda;
	private String nroVueloVuelta;
	private Date fechaVuelta;
	private String claseVuelta;
	private int legajo;
	private VentanaEmpleado ve;

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
	
	public VentanaPasajero(Conexion c, String vi, Date fIda, String cIda, String vv, Date fVuelta, String cVuelta, int leg, VentanaEmpleado ve) {
		super();
		cnxBD=c;
		nroVueloIda=vi;
		fechaIda=fIda;
		claseIda=cIda;
		nroVueloVuelta=vv;
		fechaVuelta=fVuelta;
		claseVuelta=cVuelta;
		legajo=leg;
		this.ve=ve;
		this.setResizable(false);
		initGUI();
	}
	
	private void initGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 215, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTipoDoc = new JLabel("Tipo Documento :");
		lblTipoDoc.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTipoDoc.setBounds(82, 47, 119, 28);
		contentPane.add(lblTipoDoc);
		
		JLabel lblNroDoc = new JLabel("Nro. Documento :");
		lblNroDoc.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNroDoc.setBounds(82, 120, 119, 28);
		contentPane.add(lblNroDoc);
		
		txtBoxTipoDoc = new JTextField();
		txtBoxTipoDoc.setBounds(248, 43, 125, 36);
		contentPane.add(txtBoxTipoDoc);
		txtBoxTipoDoc.setColumns(10);
		
		txtBoxNroDoc = new JTextField();
		txtBoxNroDoc.setColumns(10);
		txtBoxNroDoc.setBounds(248, 116, 125, 36);
		contentPane.add(txtBoxNroDoc);
		
		JButton btnConfirmar = new JButton("Confirmar Reserva");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnConfirmarActionPerformed(evt);
			}
	
		});
		btnConfirmar.setBounds(49, 197, 162, 42);
		contentPane.add(btnConfirmar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnCancelarActionPerformed(evt);				
			}
			
		});
		btnCancelar.setBounds(234, 197, 162, 42);
		contentPane.add(btnCancelar);
	}
	private void btnConfirmarActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		String tipoDoc = txtBoxTipoDoc.getText();
		String nroDoc = txtBoxNroDoc.getText();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String stringDateIda= format.format(fechaIda);

		
		String stringLegajo= Integer.toString(legajo);
		
		if(tipoDoc.equals("") || nroDoc.equals(""))
			JOptionPane.showMessageDialog(this,"El campo Tipo documento y/o Numero documento no puede estar vacio." ,"Error", JOptionPane.ERROR_MESSAGE);
		else{
			try {
				
				int nroDocumento=Integer.parseInt(nroDoc);
				
				java.sql.Statement stm = cnxBD.getCnx().createStatement();
				String instruccion= "SELECT doc_tipo,doc_nro FROM pasajeros WHERE doc_tipo='"+tipoDoc+"' AND doc_nro="+nroDocumento;
				java.sql.ResultSet rs=stm.executeQuery(instruccion);
				
				if (!rs.next()) {
					JOptionPane.showMessageDialog(this,"El pasajero ingresado no existe en la base de datos. Intente nuevamente." ,"Error", JOptionPane.ERROR_MESSAGE);
					txtBoxTipoDoc.setText("");
					txtBoxNroDoc.setText("");
				}
				else {
				
						if(nroVueloVuelta==null) {//caso donde solo tengo datos IDA
							
							instruccion= "CALL realizarReservaSoloIda('"+
									nroVueloIda+
									"','"+stringDateIda+
									"','"+claseIda+
									"','"+tipoDoc+
									"',"+nroDoc+
									","+stringLegajo+");";
							
							//System.out.println("CALL realizarReservaSoloIda('"+nroVueloIda+"','"+ stringDateIda +"','"+ claseIda +"','"+ tipoDoc +"',"+nroDoc+","+ stringLegajo +");");
							
							rs=stm.executeQuery(instruccion);
								
								if (!rs.next())
									JOptionPane.showMessageDialog(this,"ERROR: procedure no retorna nada." ,"Error", JOptionPane.ERROR_MESSAGE);
			            		else{//saco mensaje de la unica fila que retorna.
			            				
			            			String resultado= "Operacion: "+rs.getString("Resultado")+". "+rs.getString("Mensaje");
			            			
			            			JOptionPane.showMessageDialog(this,resultado, "Informacion", JOptionPane.INFORMATION_MESSAGE);
			            		}
								
						//caso ida y vuelta
						}else {
							String stringDateVuelta= format.format(fechaVuelta);
							
							instruccion= "CALL realizarReservaIdaVuelta('"+
									nroVueloIda+
									"','"+stringDateIda+
									"','"+claseIda+
									"','"+nroVueloVuelta+
									"','"+stringDateVuelta+
									"','"+claseVuelta+
									"','"+tipoDoc+
									"',"+nroDoc+
									","+stringLegajo +");";
							
							rs=stm.executeQuery(instruccion);
							
							if (!rs.next())
								JOptionPane.showMessageDialog(this,"ERROR: procedure no retorna nada." ,"Error", JOptionPane.ERROR_MESSAGE);
		            		
							else{//saco mensaje de la unica fila que retorna.
		      
		            			String resultado= "Operacion: "+rs.getString("Resultado")+". "+rs.getString("Mensaje");		            			
		            			JOptionPane.showMessageDialog(this,resultado, "Informacion", JOptionPane.INFORMATION_MESSAGE);
		            			
		            		}
							//solo aca tengo nroVueloVuelta
							updateTableClasesVuelta(ve.getTableClasesVueltaModel(),ve.getTableClasesVuelta());
						}	
						//siempre va haber nroVueloida
						updateTableClasesIda(ve.getTableClasesIdaModel(),ve.getTableClasesIda());
						this.dispose();
						
				}//fin if que checkea si el pasajero existe en la BD
				
				rs.close();
				stm.close();
				
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(this,"El campo numero de documento debe ser un numero natural." ,"Error", JOptionPane.ERROR_MESSAGE);
				txtBoxNroDoc.setText("");
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}//fin if textBoxs no estan vacias
	}//fin action performed btnConfimarReserva
	
	
	private void updateTableClasesIda(DefaultTableModel tableClasesIdaModel, JTable tableClasesIda) {
		// TODO Auto-generated method stub
		tableClasesIdaModel.setRowCount(0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String stringDateIda= format.format(fechaIda);
		try {
			java.sql.Statement stm = cnxBD.getCnx().createStatement();
			String instruccion= "SELECT clase,asientos_disponibles,precio "
								+"FROM vuelos_disponibles "
								+"WHERE vuelo='"+nroVueloIda+"' AND fecha='"+stringDateIda+"'";
			java.sql.ResultSet rs=stm.executeQuery(instruccion);
			insertarResulsetEnTablaModel(rs,tableClasesIdaModel);				
			tableClasesIda.setModel(tableClasesIdaModel); 			
			
			rs.close();
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void updateTableClasesVuelta(DefaultTableModel tableClasesVueltaModel, JTable tableClasesVuelta) {
		// TODO Auto-generated method stub
		tableClasesVueltaModel.setRowCount(0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String stringDateVuelta= format.format(fechaVuelta);
		try {
			java.sql.Statement stm = cnxBD.getCnx().createStatement();
			String instruccion= "SELECT clase,asientos_disponibles,precio "
								+"FROM vuelos_disponibles "
								+"WHERE vuelo='"+nroVueloVuelta+"' AND fecha='"+stringDateVuelta+"'";
			java.sql.ResultSet rs=stm.executeQuery(instruccion);
			insertarResulsetEnTablaModel(rs,tableClasesVueltaModel);				
			tableClasesVuelta.setModel(tableClasesVueltaModel); 			
			
			rs.close();
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertarResulsetEnTablaModel(ResultSet rs, DefaultTableModel tabla) {
		try {
			ResultSetMetaData metaDatos = (ResultSetMetaData) rs.getMetaData();
			// Se obtiene el numero de columnas.
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
				// Se crea un array que seria una de las filas de la tabla. 
				Object [] fila = new Object[numeroColumnas]; 

				// Se rellena cada posicion del array con una de las columnas de la tabla en base de datos.
				for (int i=0;i<numeroColumnas;i++)
					fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.

				// Se añade al modelo la fila completa.
				tabla.addRow(fila); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private void btnCancelarActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		this.dispose();
	}

}
