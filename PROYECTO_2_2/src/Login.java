

//import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;
//import com.jtattoo.*;


public class Login extends JFrame {

	private JPanel contentPane;
	
	private Conexion cnxBD;
	
	//JButton btnIngresar
	private JTextField textBox; 
	private JPasswordField passBox;
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
	
	public static void main(String[] args) {
	    try {
	        // select Look and Feel
	    	UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
	        // start application
	        
	    }
	    catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public Login() {
		super();
		this.setTitle("Ventana Usuarios");	
		this.setResizable(false);
		cnxBD=new Conexion();
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
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnIngresarActionPerformed(evt);
			}
			
		});
		btnIngresar.setBounds(88, 195, 110, 40);
		contentPane.add(btnIngresar);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsuario.setBounds(106, 54, 76, 26);
		contentPane.add(lblUsuario);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPassword.setBounds(106, 122, 76, 26);
		contentPane.add(lblPassword);
		
		textBox = new JTextField();
		textBox.setBounds(221, 52, 129, 31);
		contentPane.add(textBox);
		textBox.setColumns(10);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {				
				btnSalirActionPerformed(evt);
			}
		});
		btnSalir.setBounds(240, 195, 110, 40);
		contentPane.add(btnSalir);
		
		passBox = new JPasswordField();
		passBox.setBounds(221, 120, 129, 31);
		contentPane.add(passBox);
		
		
	}
	
	private void btnSalirActionPerformed(ActionEvent evt) {
		this.dispose();
	}
	
	private void btnIngresarActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		String usuario = textBox.getText().toLowerCase();
		String password = new String (passBox.getPassword()).toLowerCase();

		if(usuario.equals("") || password.equals(""))
			JOptionPane.showMessageDialog(this,"El campo de Usuario y/o Password no puede estar vacio." ,"Error", JOptionPane.ERROR_MESSAGE);
		else{
			if(usuario.equals("admin") && !password.equals("admin")){
				JOptionPane.showMessageDialog(this,"El Password de Administrador no es correcto. Intente nuevamente." ,"Error", JOptionPane.ERROR_MESSAGE);
				passBox.setText("");
			}
			else{
				//if(usuario.equals("admin") && password.equals("admin")){
				if(usuario.equals("admin")){
					cnxBD.conectarAdminBD();
					VentanaAdministrador frameAdmin= new VentanaAdministrador(cnxBD);
					frameAdmin.setTitle("Ventana Administrador");
					frameAdmin.setLocationRelativeTo(null);//centra el frame
					frameAdmin.setVisible(true);
					this.dispose();
				}
				else{
					if(usuario.equals("empleado") && !password.equals("empleado")){
						JOptionPane.showMessageDialog(this,"El Password de Empleado no es correcto. Intente nuevamente." ,"Error", JOptionPane.ERROR_MESSAGE);
						passBox.setText("");
					}
					else {
						if(usuario.equals("empleado")){

							LoginEmpleado frameValidar= new LoginEmpleado(cnxBD);
							frameValidar.setLocationRelativeTo(null);//centra el frame
							frameValidar.setVisible(true);
							this.dispose();
						}
						else {
							if(usuario.equals("cliente") && !password.equals("cliente")){
								JOptionPane.showMessageDialog(this,"El Password de Cliente no es correcto. Intente nuevamente." ,"Error", JOptionPane.ERROR_MESSAGE);
								passBox.setText("");
							}
							else {
								if(usuario.equals("cliente")){
									cnxBD.conectarClienteBD();
									JOptionPane.showMessageDialog(this,"Se conecto exitosamente como usuario cliente." ,"Conexion Exitosa!", JOptionPane.INFORMATION_MESSAGE);
									this.dispose();
								}
								else {
									JOptionPane.showMessageDialog(this,"El Usuario que intenta ingresar no existe para esta Base de Datos. Intente nuevamente." ,"Error", JOptionPane.ERROR_MESSAGE);
									textBox.setText("");
									passBox.setText("");
								}
							}
						}
					}	
				}		
			}	
		}//else usuarios
	}//else action boton
}
