import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexion {
	private String user,pass,url;
	private Connection cnx;
	
	/**
	 * Constructor que inicializa los atributos con valor null.
	 */
	public Conexion() {
		cnx=null;
		user=null;
		pass=null;
		url= "jdbc:mysql://localhost:3306/Vuelos?serverTimezone=America/Argentina/Buenos_Aires";
	}

	
	/**
	 * Getters y setters de conexionBD
	 */
	
	public String getUser(){
		return user;
	}

	public void setUser(String user){
		this.user = user;
	}

	public String getPass(){
		return pass;
	}

	public void setPass(String pass){
		this.pass = pass;
	}

	public Connection getCnx(){
		return cnx;
	}

	public void setCnx(Connection cnx){
		this.cnx = cnx;
	}
	
	/**
	 * Obtiene una conexion como admin con la base de datos.
	 * @throws SQLException en caso de que no se pueda establecer la conexion.
	 */
	public void conectarAdminBD(){
		user="admin";
		pass="admin";
		try{
			cnx= java.sql.DriverManager.getConnection(url, user, pass);
			java.sql.DriverManager.setLoginTimeout(900);
		}catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,"Error inesperado, no se pudo concretar la conexion de usuario Administrador.","Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * Obtiene una conexion como empleado con la base de datos.
	 * @param usuario legajo del empleado.
	 * @param Password password del empleado.
	 * @return True si se logro realizar la conexion, falso en caso contrario.
	 * @throws SQLException en caso de que no se pueda realizar correctamente la conexi�n.(LA SAQUE PORQUE YA TIENE TRY-CATCH)
	 */
	public boolean conectarEmpleadoBD(int legajo, String password){
		user="empleado";
		pass="empleado";
		try {
			cnx= java.sql.DriverManager.getConnection(url, user, pass);
			java.sql.DriverManager.setLoginTimeout(900);
		}catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,"Error inesperado, no se pudo concretar la conexion con usuario Empleado." ,"Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
			return false;
		}

		if(cnx != null) {
			try {
				java.sql.Statement stm= cnx.createStatement();
				String instruccion= "SELECT legajo,password FROM empleados WHERE "+legajo+"=legajo and md5('"+password+"')=password";
				java.sql.ResultSet rs=stm.executeQuery(instruccion);
				
				boolean encontreEmpleado=false;

				if(rs.next())
					encontreEmpleado=true;
				
				rs.close();
				stm.close();
				
				if(!encontreEmpleado) {
					JOptionPane.showMessageDialog(null,"Acceso denegado para el usuario: "+legajo+". Intente nuevamente.\nEl usuario no existe o la contraseña es incorrecta." ,"Error", JOptionPane.ERROR_MESSAGE);
					cnx.close();
					return false;
				}
			}catch (SQLException ex2) {
				JOptionPane.showMessageDialog(null,"Error inesperado, no se pudo acceder a la base de datos." ,"Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Obtiene una conexion como vuelos con la base de datos.
	 * @throws SQLException en caso de que no se pueda establecer la conexion.
	 */
	public void conectarClienteBD(){
		user="cliente";
		pass="cliente";
		try{
			cnx= java.sql.DriverManager.getConnection(url, user, pass);
			java.sql.DriverManager.setLoginTimeout(900);
		}catch (SQLException ex) {
			JOptionPane.showMessageDialog(null,"Error inesperado, no se pudo concretar la conexion de usuario Cliente." ,"Error", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
	
	/**
	 * Metodo encargado de desconectar a empleado o admin de la base de datos.
	 */
	public void desconectarBD(){
		if (cnx != null){
			try{
				cnx.close();
	        	cnx=null;
	        }
			catch (SQLException ex){
	            System.out.println("SQLException: " + ex.getMessage());
	            System.out.println("SQLState: " + ex.getSQLState());
	            System.out.println("Vendorerror: " + ex.getErrorCode());
	        }
	     }
	}
}
