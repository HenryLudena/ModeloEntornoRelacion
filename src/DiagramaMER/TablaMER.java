package DiagramaMER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class TablaMER {
    public static void main(String[] args) throws Exception {
        //Nombre de la base de datos
        String dataBaseURL = "TablaDatosVacia";
        //Ubicación del archivo .db dentro de la carpeta "database"
        String url = "jdbc:sqlite:database\\" + dataBaseURL + ".db";

        try {
        //Creación de la variable conn la cual conecta con la base de datos que se encuentra en la carpeta "database"
        Connection conn = DriverManager.getConnection(url);
        //Statement para enviar instrucciones a la base de datos si finaliza correctamente
        Statement statement = conn.createStatement();

        //Creación de la tabla de recursividad Rol con sus respectivas variables
        String createTableRol = "CREATE TABLE IF NOT EXISTS ROL (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Rol TEXT,\n"
                + "Estado TEXT,\n"
                + "Fecha_ingreso DATE,\n"
                + "Fecha_modificacion DATE,\n"
                + "id_RolPadre INTEGER,\n"
                + "FOREIGN KEY (id_RolPadre) REFERENCES ROL(id)\n"
                + ");";
                //Ejecución de la tabla Rol en la Base de Datos
        statement.execute(createTableRol);

        //Creación de la tabla de Usuario con sus respectivas variables con FK de Rol
        String createTableUsuario = "CREATE TABLE IF NOT EXISTS USUARIO (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Nombre TEXT,\n"
                + "Cedula TEXT UNIQUE,\n"
                + "Correo TEXT UNIQUE,\n"
                + "Telefono TEXT,\n"
                + "Estado TEXT,\n"
                + "id_Rol INTEGER,\n"
                + "FOREIGN KEY (id_Rol) REFERENCES ROL(id)\n"
                + ");";
        statement.execute(createTableUsuario);

        //Creación de la tabla de recursividad Curso_Catalogos con sus respectivas variables
        String createTableCursoCatalogos = "CREATE TABLE IF NOT EXISTS CURSO_CATALOGOS (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Costo TEXT,\n"
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT,\n"
                + "id_CursoCatalogosPadre INTEGER,\n"
                + "FOREIGN KEY (id_CursoCatalogosPadre) REFERENCES CURSO_CATALOGOS(id)\n"
                + ");";
        statement.execute(createTableCursoCatalogos);

        //Creación de la tabla de Curso con sus respectivas variables con FK hacia CursoCatalogos
        String createTableCurso = "CREATE TABLE IF NOT EXISTS CURSO (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Nombre TEXT,\n"
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT,\n"
                + "id_CursoCatalogos INTEGER,\n"
                + "FOREIGN KEY (id_CursoCatalogos) REFERENCES CURSO_CATALOGOS(id)\n"
                + ");";
        statement.execute(createTableCurso);

        //Creación de la tabla de Curso_Nomina con sus respectivas variables con FK hacia Curso
        String createTableCursoNomina = "CREATE TABLE IF NOT EXISTS CURSO_NOMINA (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT,\n"
                + "id_Curso INTEGER,\n"
                + "FOREIGN KEY (id_Curso) REFERENCES CURSO(id)\n"
                + ");";
        statement.execute(createTableCursoNomina);

        //Creación de la tabla de Forma_Pago con sus respectivas variables
        String createTableFormaPago = "CREATE TABLE IF NOT EXISTS FORMA_PAGO (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Forma TEXT,\n"
                + "Estado TEXT\n"
                + ");";
        statement.execute(createTableFormaPago);

        //Creación de la tabla de Credenciales con sus respectivas variables con FK hacia Curso
        String createTableCredenciales = "CREATE TABLE IF NOT EXISTS CREDENCIALES (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Email TEXT,\n"
                + "Password TEXT,\n"
                + "Usuario TEXT UNIQUE,\n"
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT,\n"
                + "id_Curso INTEGER,\n"
                + "FOREIGN KEY (id_Curso) REFERENCES CURSO(id)\n"
                + ");";
        statement.execute(createTableCredenciales);

        //Creación de la tabla de Catalogo_Soporte con sus respectivas variables
        String createTableCatalogoSoporte = "CREATE TABLE IF NOT EXISTS CATALOGO_SOPORTE (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Pregunta TEXT,\n"
                + "Estado TEXT\n"
                + ");";
        statement.execute(createTableCatalogoSoporte);

        //Creación de la tabla de Recordatorio con sus respectivas variables con FK hacia Curso_Catalogos y Credenciales
        String createTableRecordatorio = "CREATE TABLE IF NOT EXISTS RECORDATORIO (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "RespuestaUsuario TEXT,\n"
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT,\n"
                + "id_CursoCatalogos INTEGER,\n"
                + "id_Credenciales INTEGER,\n"
                + "FOREIGN KEY (id_CursoCatalogos) REFERENCES CURSO_CATALOGOS(id),\n"
                + "FOREIGN KEY (id_Credenciales) REFERENCES CREDENCIALES(id)\n"
                + ");";
        statement.execute(createTableRecordatorio);

        //Creación de la tabla de Registro_Ingreso con sus respectivas variables con FK hacia Credenciales
        String createTableRegistroIngreso = "CREATE TABLE IF NOT EXISTS REGISTRO_INGRESO (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT,\n"
                + "id_Credenciales INTEGER,\n"
                + "FOREIGN KEY (id_Credenciales) REFERENCES CREDENCIALES(id)\n"
                + ");";
        statement.execute(createTableRegistroIngreso);

        //Creación de la tabla de Factura con sus respectivas variables con FK hacia Forma_Pago y Curso
        String createTableFactura = "CREATE TABLE IF NOT EXISTS FACTURA (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Total TEXT,\n"
                + "NumeroFactura TEXT,\n"
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT,\n"
                + "id_FormaPago INTEGER,\n"
                + "id_Curso INTEGER,\n"
                + "FOREIGN KEY (id_FormaPago) REFERENCES FORMA_PAGO(id),\n"
                + "FOREIGN KEY (id_Curso) REFERENCES CURSO(id)\n"
                + ");";
        statement.execute(createTableFactura);

        //Creación de la tabla de Soporte_Catalogo con sus respectivas variables con FK
        String createTableSoporteCatalogo = "CREATE TABLE IF NOT EXISTS SOPORTE_CATALOGO (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Problema TEXT,\n"
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT\n"
                + ");";
        statement.execute(createTableSoporteCatalogo);

        //Creación de la tabla de Soporte_Tecnico con sus respectivas variables con FK hacia SoporteCatalogo y Usuario
        String createTableSoporteTecnico = "CREATE TABLE IF NOT EXISTS SOPORTE_TECNICO (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Solucion TEXT,\n"
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT,\n"
                + "id_SoporteCatalogo INTEGER,\n"
                + "id_Usuario INTEGER,\n"
                + "FOREIGN KEY (id_SoporteCatalogo) REFERENCES SOPORTE_CATALOGO(id),\n"
                + "FOREIGN KEY (id_Usuario) REFERENCES USUARIO(id)\n"
                + ");";
        statement.execute(createTableSoporteTecnico);

        //Creación de la tabla de Factura_Detalle con sus respectivas variables con FK hacia Factura y Curso
        String createTableFacturaDetalle = "CREATE TABLE IF NOT EXISTS FACTURA_DETALLE (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Costo TEXT,\n"
                + "NombreAlumno TEXT,\n"
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT,\n"
                + "id_Factura INTEGER,\n"
                + "id_Curso INTEGER,\n"
                + "FOREIGN KEY (id_Factura) REFERENCES FACTURA(id),\n"
                + "FOREIGN KEY (id_Curso) REFERENCES CURSO(id)\n"
                + ");";
        statement.execute(createTableFacturaDetalle);

        //Cierre de la conexión con la base de datos
        conn.close();
        //Impresión de mensaje para confirmar que se ha subido los datos a la base
        System.out.println("Tablas creadas exitosamente.");
    } catch (Exception e) {
        //En caso de algún error con el lenguaje se imprime cual es
        e.printStackTrace();
    }
            
    }
    
}
