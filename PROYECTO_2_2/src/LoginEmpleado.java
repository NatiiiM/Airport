import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;

public class LoginEmpleado extends JFrame {

	private JPanel contentPane;
	
	private Conexion cnxBD;
	
	private JButton btnSalir;
	private JButton btnIngresar;
	private JPasswordField passBox;
	private JTextField txtBox;
	/**
	 * Launch the application.
	 */
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
	
	public LoginEmpleado(Conexion cnx) {
		super();
		this.setTitle("Ventana Login Empleados");
		this.setResizable(false);
		cnxBD=cnx;
		initGUI();
	}

	/**
	 * Create the frame.
	 */
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 215, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnIngresarActionPerformed(evt);
			}
		});
		btnIngresar.setBounds(88, 195, 110, 41);
		contentPane.add(btnIngresar);
		
		JLabel lblEmpleado = new JLabel("Legajo:");
		lblEmpleado.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEmpleado.setBounds(102, 59, 66, 24);
		contentPane.add(lblEmpleado);
		
		JLabel lblLegajo = new JLabel("Password:");
		lblLegajo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLegajo.setBounds(102, 126, 77, 19);
		contentPane.add(lblLegajo);
		
		txtBox = new JTextField();
		txtBox.setBounds(221, 52, 129, 31);
		contentPane.add(txtBox);
		txtBox.setColumns(10);
		
		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnSalirActionPerformed(evt);
			}
		});
		btnSalir.setBounds(240, 195, 110, 41);
		contentPane.add(btnSalir);
		
		passBox = new JPasswordField();
		passBox.setBounds(221, 120, 129, 31);
		contentPane.add(passBox);
	}

	private void btnSalirActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		cnxBD.desconectarBD();
		Login frameUsuario= new Login();
		frameUsuario.setLocationRelativeTo(null);
		frameUsuario.setVisible(true);
		this.dispose();
	}

	private void btnIngresarActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		String legajo = txtBox.getText();
		String password = new String (passBox.getPassword());
		
		if(legajo.equals("") || password.equals(""))
			JOptionPane.showMessageDialog(this,"El campo Legajo y/o Password no puede estar vacio." ,"Error", JOptionPane.ERROR_MESSAGE);
		else{
			
				boolean esNro=false; int nroLegajo=0;
				try{
					nroLegajo=Integer.parseInt(legajo);
					esNro=true;
				}
				catch(NumberFormatException e){
					JOptionPane.showMessageDialog(this,"El campo Legajo debe ser un numero natural." ,"Error", JOptionPane.ERROR_MESSAGE);
				}
				//Luego de entrar al catch continua ejecucion por lo que esNro=false
				//Si retorna true entonces abre ventana empleado sino debe permitir volver a intentar
				//System.out.println(Integer.parseInt(usuario)); lo hace bien
				if(esNro && cnxBD.conectarEmpleadoBD(nroLegajo,password)){
					VentanaEmpleado frameEmpleado= new VentanaEmpleado(cnxBD,nroLegajo);
					frameEmpleado.setTitle("Ventana Empleado - Sesion Usuario: "+nroLegajo);
					frameEmpleado.setLocationRelativeTo(null);
					frameEmpleado.setVisible(true);
					this.dispose();
				}
				else{
				//cuando el legajo y/o password es incorrecto limpia cajas. 
				//mensaje de error para legajo no numero y en caso de no poder realizar la conexion
					
					txtBox.setText("");
					passBox.setText("");
				}	
			
		}
	}
	
}
