package DiagramaMER;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TablaMERDatos {
    public static void main(String[] args) throws Exception {
        String dataBaseURL = "TablaDatos";
        String url = "jdbc:sqlite:database\\" + dataBaseURL + ".db";

        //Creación de la variable que marca el estado de las filas
        String Estado = "A"; 
        //Datos de las fechas de ingreso de datos del usuario
        String[] Fecha_ingreso = {"2023-08-25", "2023-07-22", "2023-06-19", "2023-06-18", "2023-05-25"};
        //Fechas si ha existido alguna modificación en los datos ingresados previamente
        String[] Fecha_modificacion = {"2023-09-25", "2023-08-21", "2023-07-19", "2023-07-03", "2023-06-25"};
        try {
        Connection conn = DriverManager.getConnection(url);
        Statement statement = conn.createStatement();

        String createTableRol = "CREATE TABLE IF NOT EXISTS ROL (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Rol TEXT,\n"
                + "Estado TEXT,\n"
                + "Fecha_ingreso DATE,\n"
                + "Fecha_modificacion DATE,\n"
                + "id_RolPadre INTEGER,\n"
                + "FOREIGN KEY (id_RolPadre) REFERENCES ROL(id)\n"
                + ");";
        statement.execute(createTableRol);
        //Roles de las personas parte del proyecto
        String[] Rol = {"Estudiante", "Profesor", "Administrador", "Usuario", "Guest"};
        //Ingreso de los valores con QUERY donde se destaca el nombre de cada columna y los valores donde se va a colocar cada dato "?"
        String insertQuery = "INSERT INTO ROL (Rol, Estado, Fecha_ingreso, Fecha_modificacion) VALUES (?, ?, ?, ?)";
        //Statement para conectar con la base de datos
            PreparedStatement preparedStatementRol = conn.prepareStatement(insertQuery);
            //Ciclo for con limite en algún Array que pertenece a la tabla
            for (int i=0 ;i<Rol.length;i++){
                //Actualización de los datos de cada columna con los datos de los Array
                preparedStatementRol.setString(1, Rol[i]);
                preparedStatementRol.setString(2, Estado);
                preparedStatementRol.setString(3, Fecha_ingreso[i]);
                preparedStatementRol.setString(4, Fecha_modificacion[i]);
                //Subir los datos a la DB
                preparedStatementRol.executeUpdate();
            }
        
        String createTableUsuario = "CREATE TABLE IF NOT EXISTS USUARIO (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Nombre TEXT,\n"
                + "Cedula TEXT ,\n" //UNIQUE
                + "Correo TEXT ,\n" //UNIQUE
                + "Telefono TEXT,\n"
                + "Estado TEXT,\n"
                + "id_Rol INTEGER,\n"
                + "FOREIGN KEY (id_Rol) REFERENCES ROL(id)\n"
                + ");";
        statement.execute(createTableUsuario);
        //Nombres de las personas 
        String[] Nombre = {"Pepe", "Juan", "Jorge", "Luis", "Leonardo"};
        //Cedula de cada individuo
        String[] Cedula = {"1150121427", "1155421427", "1150121434", "1150122327", "1150121237"};
        //Correo de cada persona registrada
        String[] Correo = {"alguien1234@gmail.com", "alguien1232@gmail.com", "alguien356@gmail.com", "alguien1532@gmail.com", "alguien4234@gmail.com"};
        //Telefono de cada usuario
        String[] Telefono = {"0949374789", "0949374239", "0949374589", "0949456789", "0949374539"};
        insertQuery = "INSERT INTO USUARIO (Nombre, Cedula, Correo, Telefono, Estado, id_Rol) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementUsuario = conn.prepareStatement(insertQuery);
            for (int i=0 ;i<Nombre.length;i++){
                preparedStatementUsuario.setString(1, Nombre[i]);
                preparedStatementUsuario.setString(2, Cedula[i]);
                preparedStatementUsuario.setString(3, Correo[i]);
                preparedStatementUsuario.setString(4, Telefono[i]);
                preparedStatementUsuario.setString(5, Estado);
                preparedStatementUsuario.setInt(6, i+1);
                preparedStatementUsuario.executeUpdate();
            }
        
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
        //Costo de cada curso
        String[] Costo = {"$250", "$400", "$300", "$500", "$100"};
        insertQuery = "INSERT INTO CURSO_CATALOGOS (Costo, FechaIngreso, FechaModificacion, Estado, id_CursoCatalogosPadre) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatementCursoCatalogos = conn.prepareStatement(insertQuery);
            for (int i=0 ;i<Costo.length;i++){
                preparedStatementCursoCatalogos.setString(1, Costo[i]);
                preparedStatementCursoCatalogos.setString(2, Fecha_ingreso[i]);
                preparedStatementCursoCatalogos.setString(3, Fecha_modificacion[i]);
                preparedStatementCursoCatalogos.setString(4, Estado);
                preparedStatementCursoCatalogos.setInt(5, i+1);
                preparedStatementCursoCatalogos.executeUpdate();
            }
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
        //Nombre de las materias
        String[] NombreMateria = {"Matemática", "Programación", "Ciencias", "Física", "Ecuaciones Diferenciales"};
        insertQuery = "INSERT INTO CURSO (Nombre, FechaIngreso, FechaModificacion, Estado, id_CursoCatalogos) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatementCatalogos = conn.prepareStatement(insertQuery);
            for (int i=0 ;i<NombreMateria.length;i++){
                preparedStatementCatalogos.setString(1, NombreMateria[i]);
                preparedStatementCatalogos.setString(2, Fecha_ingreso[i]);
                preparedStatementCatalogos.setString(3, Fecha_modificacion[i]);
                preparedStatementCatalogos.setString(4, Estado);
                preparedStatementCatalogos.setInt(5, i+1);
                preparedStatementCatalogos.executeUpdate();
            }
        
        String createTableCursoNomina = "CREATE TABLE IF NOT EXISTS CURSO_NOMINA (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT,\n"
                + "id_Curso INTEGER,\n"
                + "FOREIGN KEY (id_Curso) REFERENCES CURSO(id)\n"
                + ");";
        statement.execute(createTableCursoNomina);
        insertQuery = "INSERT INTO CURSO_NOMINA (FechaIngreso, FechaModificacion, Estado, id_Curso) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatementCursoNomina = conn.prepareStatement(insertQuery);
            for (int i=0 ;i<Fecha_ingreso.length;i++){
                preparedStatementCursoNomina.setString(1, Fecha_ingreso[i]);
                preparedStatementCursoNomina.setString(2, Fecha_modificacion[i]);
                preparedStatementCursoNomina.setString(3, Estado);
                preparedStatementCursoNomina.setInt(4, i+1);
                preparedStatementCursoNomina.executeUpdate();
            }
        
        String createTableFormaPago = "CREATE TABLE IF NOT EXISTS FORMA_PAGO (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Forma TEXT,\n"
                + "Estado TEXT\n"
                + ");";
        statement.execute(createTableFormaPago);
        //Formas de pago de los cursos
        String[] FormaPago = {"Efectivo", "Transferencia", "Tarjeta Crédito", "Tarjeta Débito", "Plazos"};
        insertQuery = "INSERT INTO FORMA_PAGO (Forma, Estado) VALUES (?, ?)";
        PreparedStatement preparedStatementFormaPago = conn.prepareStatement(insertQuery);
            for (int i=0 ;i<FormaPago.length;i++){
                preparedStatementFormaPago.setString(1, FormaPago[i]);
                preparedStatementFormaPago.setString(2, Estado);
                preparedStatementFormaPago.executeUpdate();
            }

        String createTableCredenciales = "CREATE TABLE IF NOT EXISTS CREDENCIALES (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Email TEXT,\n"
                + "Password TEXT,\n"
                + "Usuario TEXT,\n" //UNIQUE
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT,\n"
                + "id_Curso INTEGER,\n"
                + "FOREIGN KEY (id_Curso) REFERENCES CURSO(id)\n"
                + ");";
        statement.execute(createTableCredenciales);
        //Contraseña de cada usuario para el login
        String[] Password = {"02020", "02025", "75928", "85892", "85839"};
        //Nombre de cada usuario en el perfil 
        String[] Usuario = {"pepe1234", "gamer47573", "holao32", "juan6573", "ramon567"};
        insertQuery = "INSERT INTO CREDENCIALES (Email, Password, Usuario, FechaIngreso, FechaModificacion, Estado, id_Curso) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatementCredenciales = conn.prepareStatement(insertQuery);
            for (int i=0 ;i<Usuario.length;i++){
                preparedStatementCredenciales.setString(1, Correo[i]);
                preparedStatementCredenciales.setString(2, Password[i]);
                preparedStatementCredenciales.setString(3, Usuario[i]);
                preparedStatementCredenciales.setString(4, Fecha_ingreso[i]);
                preparedStatementCredenciales.setString(5, Fecha_modificacion[i]);
                preparedStatementCredenciales.setString(6, Estado);
                preparedStatementCredenciales.setInt(7, i+1);
                preparedStatementCredenciales.executeUpdate();
            }

        String createTableCatalogoSoporte = "CREATE TABLE IF NOT EXISTS CATALOGO_SOPORTE (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Pregunta TEXT,\n"
                + "Estado TEXT\n"
                + ");";
        statement.execute(createTableCatalogoSoporte);
        //Catalogo de preguntas para recuperar contraseña
        String[] Pregunta = {"Nombre Mascota", "Comida Favorita", "Color Favorito", "Nombre Ex", "Deporte Preferido"};
        insertQuery = "INSERT INTO CATALOGO_SOPORTE (Pregunta, Estado) VALUES (?, ?)";
        PreparedStatement preparedStatementCatalogoSoporte = conn.prepareStatement(insertQuery);
            for (int i=0 ;i<Pregunta.length;i++){
                preparedStatementCatalogoSoporte.setString(1, Pregunta[i]);
                preparedStatementCatalogoSoporte.setString(2, Estado);
                preparedStatementCatalogoSoporte.executeUpdate();
            }
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
        //Respuestas de cada usuario al catalogo de preguntas
        String[] RespuestasUsuario = {"Azul", "Rojo", "Verde", "Azul", "Celeste"};
        insertQuery = "INSERT INTO RECORDATORIO (RespuestaUsuario, FechaIngreso, FechaModificacion, Estado, id_CursoCatalogos, id_Credenciales) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatementRecordatorio = conn.prepareStatement(insertQuery);
            for (int i=0 ;i<RespuestasUsuario.length;i++){
                preparedStatementRecordatorio.setString(1, RespuestasUsuario[i]);
                preparedStatementRecordatorio.setString(2, Fecha_ingreso[i]);
                preparedStatementRecordatorio.setString(3, Fecha_modificacion[i]);
                preparedStatementRecordatorio.setString(4, Estado);
                preparedStatementRecordatorio.setInt(5, i+1);
                preparedStatementRecordatorio.setInt(6, i+1);
                preparedStatementRecordatorio.executeUpdate();
            }

        String createTableRegistroIngreso = "CREATE TABLE IF NOT EXISTS REGISTRO_INGRESO (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT,\n"
                + "id_Credenciales INTEGER,\n"
                + "FOREIGN KEY (id_Credenciales) REFERENCES CREDENCIALES(id)\n"
                + ");";
        statement.execute(createTableRegistroIngreso);
        insertQuery = "INSERT INTO REGISTRO_INGRESO (FechaIngreso, FechaModificacion, Estado, id_Credenciales) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatementRegistroIngreso = conn.prepareStatement(insertQuery);
            for (int i=0 ;i<RespuestasUsuario.length;i++){
                preparedStatementRegistroIngreso.setString(1, Fecha_ingreso[i]);
                preparedStatementRegistroIngreso.setString(2, Fecha_modificacion[i]);
                preparedStatementRegistroIngreso.setString(3, Estado);
                preparedStatementRegistroIngreso.setInt(4, i+1);
                preparedStatementRegistroIngreso.executeUpdate();
            }
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
        //Valor total a pagar de cada usuario por lo comprado 
        String[] Total = {"$340", "$334", "$753", "$868", "$492"};
        //Enumeración de las factiras
        String[] NumeroFactura ={"F-000","F-001","F-002","F-003", "F-004"};
        insertQuery = "INSERT INTO FACTURA (Total, NumeroFactura, FechaIngreso, FechaModificacion, Estado, id_FormaPago, id_Curso) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatementFactura = conn.prepareStatement(insertQuery);
            for (int i=0 ;i<RespuestasUsuario.length;i++){
                preparedStatementFactura.setString(1, Total[i]);
                preparedStatementFactura.setString(2, NumeroFactura[i]);
                preparedStatementFactura.setString(3, Fecha_ingreso[i]);
                preparedStatementFactura.setString(4, Fecha_modificacion[i]);
                preparedStatementFactura.setString(5, Estado);
                preparedStatementFactura.setInt(6, i+1);
                preparedStatementFactura.setInt(7, i+1);
                preparedStatementFactura.executeUpdate();
            }
        String createTableSoporteCatalogo = "CREATE TABLE IF NOT EXISTS SOPORTE_CATALOGO (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "Problema TEXT,\n"
                + "FechaIngreso DATE,\n"
                + "FechaModificacion DATE,\n"
                + "Estado TEXT\n"
                + ");";
        statement.execute(createTableSoporteCatalogo);
        //Catalogo de problemas en el sistema
        String[] Problema = {"No hay sistema", "No carga la página", "Fechas incorrectas", "Cursos inexistentes", "Problemas Pago"};
        insertQuery = "INSERT INTO SOPORTE_CATALOGO (Problema, FechaIngreso, FechaModificacion, Estado) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatementSoporteCatalogo = conn.prepareStatement(insertQuery);
            for (int i=0 ;i<Problema.length;i++){
                preparedStatementSoporteCatalogo.setString(1, Problema[i]);
                preparedStatementSoporteCatalogo.setString(2, Fecha_ingreso[i]);
                preparedStatementSoporteCatalogo.setString(3, Fecha_modificacion[i]);
                preparedStatementSoporteCatalogo.setString(4, Estado);
                preparedStatementSoporteCatalogo.executeUpdate();
            }
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
        //Soluciones de los técnicos a los problemas proporcionados en el catalogo
        String[] Solucion = {"Verficar Conexión", "Recargar la página", "Revisar fecha dispositivo", "Comprobar Conexión", "Verficar datos"};
        insertQuery = "INSERT INTO SOPORTE_TECNICO (Solucion, FechaIngreso, FechaModificacion, Estado, id_SoporteCatalogo, id_Usuario) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatementSoporteTecnico = conn.prepareStatement(insertQuery);
            for (int i=0 ;i<Solucion.length;i++){
                preparedStatementSoporteTecnico.setString(1, Solucion[i]);
                preparedStatementSoporteTecnico.setString(2, Fecha_ingreso[i]);
                preparedStatementSoporteTecnico.setString(3, Fecha_modificacion[i]);
                preparedStatementSoporteTecnico.setString(4, Estado);
                preparedStatementSoporteTecnico.setInt(5, i+1);
                preparedStatementSoporteTecnico.setInt(6, i+1);
                preparedStatementSoporteTecnico.executeUpdate();
            }
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
        //Detalle de las facturas por si algún dato cambia se conserva en esta columna
        String[] CostoDetalle = {"$352", "$345", "$753", "$489", "$573"};
        //Nombre del alumno que va a pagar
        String[] NombreAlumno ={"Juan","Pedro","Maria","Ana","Luis"};
        insertQuery = "INSERT INTO FACTURA_DETALLE (Costo, NombreAlumno, FechaIngreso, FechaModificacion, Estado, id_Factura, id_Curso) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatementFacturaDetalle = conn.prepareStatement(insertQuery);
            for (int i=0 ;i<CostoDetalle.length;i++){
                preparedStatementFacturaDetalle.setString(1, CostoDetalle[i]);
                preparedStatementFacturaDetalle.setString(2, NombreAlumno[i]);
                preparedStatementFacturaDetalle.setString(3, Fecha_ingreso[i]);
                preparedStatementFacturaDetalle.setString(4, Fecha_modificacion[i]);
                preparedStatementFacturaDetalle.setString(5, Estado);
                preparedStatementFacturaDetalle.setInt(6, i+1);
                preparedStatementFacturaDetalle.setInt(7, i+1);
                preparedStatementFacturaDetalle.executeUpdate();
            }
        conn.close();

        System.out.println("Tablas creadas exitosamente.");
    } catch (Exception e) {
        e.printStackTrace();
    }
            
    }
}
